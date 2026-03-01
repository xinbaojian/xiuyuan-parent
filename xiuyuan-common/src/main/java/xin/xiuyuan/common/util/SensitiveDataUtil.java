package xin.xiuyuan.common.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * 敏感数据脱敏工具类
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@Slf4j
public class SensitiveDataUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    private static final String MASK = "******";

    // 需要跳过的包名（框架相关的类）
    private static final Set<String> SKIP_PACKAGES = Set.of(
            "org.springframework",
            "org.hibernate",
            "org.apache.catalina",
            "jakarta.servlet",
            "javax.servlet",
            "java.lang"
    );

    /**
     * 过滤敏感字段
     *
     * @param json            JSON字符串
     * @param sensitiveFields 敏感字段名数组
     * @return 脱敏后的JSON字符串
     */
    public static String filterSensitiveFields(String json, String[] sensitiveFields) {
        if (StrUtil.isBlank(json) || sensitiveFields == null || sensitiveFields.length == 0) {
            return json;
        }

        try {
            JsonNode rootNode = OBJECT_MAPPER.readTree(json);
            Set<String> sensitiveSet = new HashSet<>(Set.of(sensitiveFields));

            filterNode(rootNode, sensitiveSet);
            return OBJECT_MAPPER.writeValueAsString(rootNode);
        } catch (Exception e) {
            log.warn("敏感数据脱敏失败", e);
            return json;
        }
    }

    /**
     * 过滤敏感字段
     *
     * @param args            参数数组
     * @param sensitiveFields 敏感字段名数组
     * @return 脱敏后的JSON字符串
     */
    public static String filterSensitiveParams(Object[] args, String[] sensitiveFields) {
        if (args == null || args.length == 0) {
            return null;
        }

        try {
            // 过滤掉框架相关的对象，只保留业务对象
            Object[] filteredArgs = filterFrameworkObjects(args);
            String json = OBJECT_MAPPER.writeValueAsString(filteredArgs);
            return filterSensitiveFields(json, sensitiveFields);
        } catch (Exception e) {
            log.warn("参数序列化失败", e);
            return "参数序列化失败";
        }
    }

    /**
     * 过滤掉框架相关的对象
     *
     * @param args 原始参数数组
     * @return 过滤后的参数数组
     */
    private static Object[] filterFrameworkObjects(Object[] args) {
        if (args == null || args.length == 0) {
            return args;
        }

        Object[] filtered = new Object[args.length];
        int index = 0;

        for (Object arg : args) {
            if (arg == null) {
                filtered[index++] = null;
            } else if (shouldSkip(arg.getClass())) {
                // 跳过框架对象，用字符串代替
                filtered[index++] = "[Framework Object: " + arg.getClass().getSimpleName() + "]";
            } else {
                filtered[index++] = arg;
            }
        }

        return filtered;
    }

    /**
     * 判断是否应该跳过该对象
     *
     * @param clazz 对象类型
     * @return true-跳过，false-不跳过
     */
    private static boolean shouldSkip(Class<?> clazz) {
        // 检查包名
        String packageName = clazz.getPackageName();
        if (SKIP_PACKAGES.stream().anyMatch(packageName::startsWith)) {
            return true;
        }

        // 检查常见的不可序列化接口
        // 检查类本身实现的接口
        if (hasUnserializableInterface(clazz)) {
            return true;
        }

        return false;
    }

    /**
     * 检查类是否实现了不可序列化的接口
     *
     * @param clazz 类
     * @return true-包含不可序列化接口，false-不包含
     */
    private static boolean hasUnserializableInterface(Class<?> clazz) {
        // 检查所有接口
        for (Class<?> iface : clazz.getInterfaces()) {
            String interfaceName = iface.getName();
            // Servlet 相关接口
            if (interfaceName.contains("ServletRequest") ||
                    interfaceName.contains("ServletResponse") ||
                    interfaceName.contains("HttpSession")) {
                return true;
            }
        }

        // 递归检查父类
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            return hasUnserializableInterface(superClass);
        }

        return false;
    }

    /**
     * 递归过滤JSON节点
     */
    private static void filterNode(JsonNode node, Set<String> sensitiveFields) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            // 使用 properties() 替代已弃用的 fields()
            objectNode.properties().forEach(entry -> {
                String fieldName = entry.getKey();
                JsonNode valueNode = entry.getValue();

                if (sensitiveFields.contains(fieldName) && valueNode.isTextual()) {
                    objectNode.put(fieldName, MASK);
                } else if (valueNode.isObject() || valueNode.isArray()) {
                    filterNode(valueNode, sensitiveFields);
                }
            });
        } else if (node.isArray()) {
            for (JsonNode item : node) {
                filterNode(item, sensitiveFields);
            }
        }
    }
}
