package xin.xiuyuan.admin.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 基础实体类，包含所有实体类的公共字段
 *
 * @author xinbaojian
 * @create 2025-12-15 17:30
 **/
@Data
@Accessors(chain = true)
@Document
public class BaseEntity {

    /**
     * 创建时间
     */
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 备注
     */
    private String remark;
}