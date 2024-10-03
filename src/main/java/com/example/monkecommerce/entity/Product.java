package com.example.monkecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(nullable = false, unique = true)
        private String name;

        @Column(length = 500)
        private String description;

        private BigDecimal price;



        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public void setPrice(BigDecimal price) {
                this.price = price;
        }


        @Override
        public String toString() {
                return "Product{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", price=" + price +
                        '}';
        }


}
