package com.example.monkecommerce.dto;

import com.example.monkecommerce.entity.Coupon;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ApplicableCouponDTO {


        private UUID couponId;
        private Coupon.CouponType type;
        private BigDecimal discount;



    public UUID getCouponId() {
        return couponId;
    }

    public void setCouponId(UUID couponId) {
        this.couponId = couponId;
    }

    public Coupon.CouponType getType() {
        return type;
    }

    public void setType(Coupon.CouponType type) {
        this.type = type;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}

