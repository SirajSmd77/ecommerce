package com.example.monkecommerce.service;

import com.example.monkecommerce.entity.Coupon;
import com.example.monkecommerce.exception.ResourceNotFoundException;
import com.example.monkecommerce.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CouponService {

        @Autowired
        private CouponRepository couponRepository;

        public Coupon createCoupon(Coupon coupon) {
            coupon.setCreatedAt(LocalDateTime.now());
            return couponRepository.save(coupon);
        }

        public List<Coupon> getAllCoupons() {
            return couponRepository.findAll();
        }

        public Optional<Coupon> getCouponById(UUID id) {
            return couponRepository.findById(id);
        }

        public Coupon updateCoupon(UUID id, Coupon couponDetails) {
            Coupon coupon = couponRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
            coupon.setDetails(couponDetails.getDetails());
            coupon.setType(couponDetails.getType());
            coupon.setUpdatedAt(LocalDateTime.now());
            return couponRepository.save(coupon);
        }

        public void deleteCoupon(UUID id) {
            couponRepository.deleteById(id);
        }
    }
