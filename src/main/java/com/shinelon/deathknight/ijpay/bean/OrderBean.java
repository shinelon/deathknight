package com.shinelon.deathknight.ijpay.bean;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Shinelon
 */
@Data
@Builder
public class OrderBean implements Serializable {
    private String payChannel;
    private LocalDateTime createTime;
}
