package com.example.storage.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_storage")
public class Storage {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;

    /**
     * 总库存
     */
    private Integer totalStock;

    /**
     * 已用库存
     */
    private Integer usedStock;

    /**
     * 剩余库存
     */
    private Integer residueStock;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
