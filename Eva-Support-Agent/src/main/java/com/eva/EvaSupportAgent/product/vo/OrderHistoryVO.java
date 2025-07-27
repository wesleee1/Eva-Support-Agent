package com.eva.EvaSupportAgent.product.vo;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryVO {
	@Schema(hidden = true)
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private String status;
    private String productName;
    private Date orderedOn;

    // Getters and Setters
}

