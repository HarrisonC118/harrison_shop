package com.harrison.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 收货地址表
 * </p>
 *
 * @author harrison
 * @since 2023-06-23
 */
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键;雪花算法+UUID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 乐观锁
     */
    @TableField(fill = FieldFill.INSERT)
    @Version
    private Integer revision;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 是否为默认收货地址;0否1是
     */
    private Boolean defaultStatus;

    /**
     * 收件人姓名
     */
    private String receiveName;

    /**
     * 收件人电话
     */
    private String receivePhone;

    /**
     * 省、直辖市市
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String region;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 逻辑删除
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean isDeleted;
}
