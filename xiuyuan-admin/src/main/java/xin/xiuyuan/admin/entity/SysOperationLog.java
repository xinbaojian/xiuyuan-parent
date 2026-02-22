package xin.xiuyuan.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xin.xiuyuan.common.types.OperationStatus;
import xin.xiuyuan.domain.entity.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志实体
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@Data
@NoArgsConstructor
@Document
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysOperationLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    /**
     * 操作模块
     */
    @Indexed
    private String module;

    /**
     * 操作类型
     */
    @Indexed
    private String operationType;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作人ID
     */
    @Indexed
    private String operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 操作部门ID
     */
    @Indexed
    private String deptId;

    /**
     * 操作部门名称
     */
    private String deptName;

    /**
     * 请求方法
     * 例如: xin.xiuyuan.admin.controller.SysUserController.save
     */
    private String method;

    /**
     * 请求方式
     * 例如: GET, POST, PUT, DELETE
     */
    private String requestMethod;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求IP
     */
    @Indexed
    private String operIp;

    /**
     * 请求地点(IP解析)
     */
    private String operLocation;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回结果
     */
    private String jsonResult;

    /**
     * 操作状态(成功/失败)
     */
    @Indexed
    private OperationStatus status;

    /**
     * 错误消息(失败时记录)
     */
    private String errorMsg;

    /**
     * 执行时长(毫秒)
     */
    private Integer costTime;

    /**
     * 操作时间(复用BaseEntity.createTime,增加索引加速查询)
     */
    @Indexed
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operTime;
}
