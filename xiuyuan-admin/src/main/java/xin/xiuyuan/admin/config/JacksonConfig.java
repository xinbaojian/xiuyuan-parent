package xin.xiuyuan.admin.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Jackson 配置类
 * 解决 Java 8 新日期 API 序列化/反序列化问题
 *
 * @author xinbaojian
 * @create 2025-12-24
 **/
@Configuration
public class JacksonConfig {

    /**
     * 宽松的日期时间格式解析器
     * 支持格式: yyyy-MM-dd HH:mm:ss, yyyy-M-d HH:mm:ss (不补零)
     */
    private static final DateTimeFormatter LENIENT_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter();

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            // 自定义 LocalDateTime 序列化器
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            // 自定义 LocalDateTime 反序列化器,支持宽松格式解析
            javaTimeModule.addDeserializer(LocalDateTime.class, new LenientLocalDateTimeDeserializer());

            builder.modules(javaTimeModule);
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            builder.deserializerByType(LocalDateTime.class, new LenientLocalDateTimeDeserializer());
            // 启用默认类型信息，用于反序列化时保留类型信息
            builder.featuresToEnable(MapperFeature.DEFAULT_VIEW_INCLUSION);
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        };
    }

    /**
     * 自定义 LocalDateTime 反序列化器
     * 支持不规范的日期格式(如 2025-3-1 而不是 2025-03-01)
     */
    public static class LenientLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

        public LenientLocalDateTimeDeserializer() {
            super(LocalDateTime.class);
        }

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String dateStr = p.getText().trim();

            // 尝试多种格式解析
            try {
                // 标准格式: yyyy-MM-dd HH:mm:ss
                return LocalDateTime.parse(dateStr, LENIENT_DATE_TIME_FORMATTER);
            } catch (Exception e1) {
                try {
                    // 尝试使用 ISO 格式
                    return LocalDateTime.parse(dateStr);
                } catch (Exception e2) {
                    // 尝试自动格式化(处理不补零的情况,如 2025-3-1)
                    return parseLenientDateTime(dateStr);
                }
            }
        }

        private LocalDateTime parseLenientDateTime(String dateStr) {
            // 将 "2025-3-1 22:41:48" 转换为 "2025-03-01 22:41:48"
            String normalized = dateStr.replaceAll("(?<=\\D)(\\d)(?=\\D)", "0$1")
                    .replaceAll("(?<=^|\\s)(\\d)(?=\\d)", "0$1");
            return LocalDateTime.parse(normalized, LENIENT_DATE_TIME_FORMATTER);
        }
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        jackson2ObjectMapperBuilderCustomizer().customize(builder);
        ObjectMapper mapper = builder.build();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // 禁用时间戳格式
        // 不使用全局类型信息，避免HTTP请求解析问题
        // 仅在Redis序列化时单独处理类型信息
        return mapper;
    }
}