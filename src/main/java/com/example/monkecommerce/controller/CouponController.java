package com.example.monkecommerce.controller;

import com.example.monkecommerce.entity.Coupon;
import com.example.monkecommerce.exception.ResourceNotFoundException;
import com.example.monkecommerce.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coupons")
public class CouponController {

        @Autowired
        private CouponService couponService;

        @PostMapping
        public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
            Coupon createdCoupon = couponService.createCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
        }

        @GetMapping
        public ResponseEntity<List<Coupon>> getAllCoupons() {
            return ResponseEntity.ok(couponService.getAllCoupons());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Coupon> getCouponById(@PathVariable UUID id) {
            Coupon coupon = couponService.getCouponById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
            return ResponseEntity.ok(coupon);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Coupon> updateCoupon(@PathVariable UUID id, @RequestBody Coupon couponDetails) {
            Coupon updatedCoupon = couponService.updateCoupon(id, couponDetails);
            return ResponseEntity.ok(updatedCoupon);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCoupon(@PathVariable UUID id) {
            couponService.deleteCoupon(id);
            return ResponseEntity.noContent().build();
        }
    }
