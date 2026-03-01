package xin.xiuyuan.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Bark配置
 *
 * @author xinbaojian
 * @create 2026-03-01 22:05
 **/
@Data
@Component
@ConfigurationProperties(prefix = "bark")
public class BarkProperties {
    /**
     * Bark URL
     */
    private List<String> urlList;
}
