package com.example.monkecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

        @Id
        @GeneratedValue
        private UUID id;

        @ManyToOne
        @JoinColumn(name = "cart_id")
        @JsonIgnore  // Ignore this field during serialization to avoid circular reference
        private Cart cart;

        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        private Integer quantity;

        private BigDecimal price;

        private BigDecimal discount = BigDecimal.ZERO;



        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public Product getProduct() {
                return product;
        }

        public void setProduct(Product product) {
                this.product = product;
        }

        public Cart getCart() {
                return cart;
        }

        public void setCart(Cart cart) {
                this.cart = cart;
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

        public BigDecimal getDiscount() {
                return discount;
        }

        public void setDiscount(BigDecimal discount) {
                this.discount = discount;
        }


        @Override
        public String toString() {
                return "CartItem{id=" + id + ", productId=" + (product != null ? product.getId() : null) +
                        ", quantity=" + quantity + ", price=" + price + ", discount=" + discount + "}";
        }

}
