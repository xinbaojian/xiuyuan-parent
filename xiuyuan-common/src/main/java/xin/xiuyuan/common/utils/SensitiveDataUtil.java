package xin.xiuyuan.common.utils;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            .registerModule(new JavaTimeModule());
    private static final String MASK = "******";

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
            String json = OBJECT_MAPPER.writeValueAsString(args);
            return filterSensitiveFields(json, sensitiveFields);
        } catch (Exception e) {
            log.warn("参数序列化失败", e);
            return "参数序列化失败";
        }
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
