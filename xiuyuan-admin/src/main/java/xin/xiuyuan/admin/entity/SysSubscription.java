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
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.SubscriptionType;
import xin.xiuyuan.domain.entity.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订阅管理实体
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Data
@NoArgsConstructor
@Document
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysSubscription extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    /**
     * 订阅名称
     */
    @Indexed
    private String subscriptionName;

    /**
     * 订阅类型
     */
    @Indexed
    private SubscriptionType subscriptionType;

    /**
     * 提前提醒天数
     */
    private Integer advanceReminderDays;

    /**
     * 开始时间
     */
    @Indexed
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    @Indexed
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    /**
     * 状态
     */
    @Indexed
    private CommonStatus status = CommonStatus.NORMAL;
}
