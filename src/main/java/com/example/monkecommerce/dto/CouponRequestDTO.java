package com.example.monkecommerce.dto;

import java.util.List;

public class CouponRequestDTO {

        private String type;  // "cart-wise", "product-wise", "bxgy"
        private CouponDetails details;

        // Getters and Setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public CouponDetails getDetails() {
            return details;
        }

        public void setDetails(CouponDetails details) {
            this.details = details;
        }

        // Inner classes for different coupon details
        public static class CouponDetails {
            private Double threshold;  // For cart-wise coupons
            private Double discount;   // Discount percentage or amount
            private Long productId;    // For product-wise coupons
            private List<BuyProduct> buyProducts;  // For bxgy coupons
            private List<GetProduct> getProducts;
            private Integer repetitionLimit; // For bxgy coupons

            // Getters and Setters
            public Double getThreshold() {
                return threshold;
            }

            public void setThreshold(Double threshold) {
                this.threshold = threshold;
            }

            public Double getDiscount() {
                return discount;
            }

            public void setDiscount(Double discount) {
                this.discount = discount;
            }

            public Long getProductId() {
                return productId;
            }

            public void setProductId(Long productId) {
                this.productId = productId;
            }

            public List<BuyProduct> getBuyProducts() {
                return buyProducts;
            }

            public void setBuyProducts(List<BuyProduct> buyProducts) {
                this.buyProducts = buyProducts;
            }

            public List<GetProduct> getGetProducts() {
                return getProducts;
            }

            public void setGetProducts(List<GetProduct> getProducts) {
                this.getProducts = getProducts;
            }

            public Integer getRepetitionLimit() {
                return repetitionLimit;
            }

            public void setRepetitionLimit(Integer repetitionLimit) {
                this.repetitionLimit = repetitionLimit;
            }
        }

        public static class BuyProduct {
            private Long productId;
            private Integer quantity;

            // Getters and Setters
            public Long getProductId() {
                return productId;
            }

            public void setProductId(Long productId) {
                this.productId = productId;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }
        }

        public static class GetProduct {
            private Long productId;
            private Integer quantity;

            // Getters and Setters
            public Long getProductId() {
                return productId;
            }

            public void setProductId(Long productId) {
                this.productId = productId;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }
        }
    }
