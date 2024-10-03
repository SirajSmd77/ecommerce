package com.example.monkecommerce.dto;

import com.example.monkecommerce.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CartRequestDTO {


       private UUID cartId;
       private UUID userId;

       private List<CartItemDTO> items;

        public List<CartItemDTO> getItems() {
            return items;
        }

        public void setItems(List<CartItemDTO> items) {
            this.items = items;
        }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public static class CartItemDTO {
            private UUID productId;
            private Integer quantity;
            private BigDecimal price;
            private BigDecimal discount = BigDecimal.ZERO;  // Discount for the item, initialized to zero


            public BigDecimal getDiscount() {
                return discount;
            }

            public void setDiscount(BigDecimal discount) {
                this.discount = discount;
            }

            public UUID getProductId() {
                return productId;
            }

            public void setProductId(UUID productId) {
                this.productId = productId;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }
        }
    }
