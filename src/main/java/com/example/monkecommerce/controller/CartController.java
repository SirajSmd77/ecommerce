package com.example.monkecommerce.controller;

import com.example.monkecommerce.dto.ApplicableCouponDTO;
import com.example.monkecommerce.dto.CartRequestDTO;
import com.example.monkecommerce.entity.Cart;
import com.example.monkecommerce.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    @Autowired
        private CartService cartService;

        @PostMapping("/applicable-coupons")
        public ResponseEntity<List<ApplicableCouponDTO>> getApplicableCoupons(@RequestBody CartRequestDTO cartRequestDTO) {
            List<ApplicableCouponDTO> coupons = cartService.getApplicableCoupons(cartRequestDTO);
            return ResponseEntity.ok(coupons);
        }

        @PostMapping("/apply-coupon/{couponId}")
        public ResponseEntity<Cart> applyCoupon(@PathVariable UUID couponId, @RequestBody CartRequestDTO cartRequestDTO) {
            log.info("couponId is : {} ",couponId);
            Cart updatedCart = cartService.applyCouponToCart(couponId, cartRequestDTO);
            return ResponseEntity.ok(updatedCart);
        }
    }

