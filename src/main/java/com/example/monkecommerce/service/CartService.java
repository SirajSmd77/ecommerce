package com.example.monkecommerce.service;

import com.example.monkecommerce.dto.ApplicableCouponDTO;
import com.example.monkecommerce.dto.CartRequestDTO;
import com.example.monkecommerce.entity.Cart;
import com.example.monkecommerce.entity.CartItem;
import com.example.monkecommerce.entity.Coupon;
import com.example.monkecommerce.entity.Product;
import com.example.monkecommerce.exception.InvalidOperationException;
import com.example.monkecommerce.exception.ResourceNotFoundException;
import com.example.monkecommerce.repository.CartRepository;
import com.example.monkecommerce.repository.CouponRepository;
import com.example.monkecommerce.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {
    private static final Logger log = LoggerFactory.getLogger(CartService.class);


    @Autowired
        private CouponRepository couponRepository;

        @Autowired
        CartRepository cartRepository;

        @Autowired
        private ProductRepository productRepository;

        public List<ApplicableCouponDTO> getApplicableCoupons(CartRequestDTO cartRequestDTO) {
            List<Coupon> coupons = couponRepository.findAll();
            log.info("coupons are : {} ",coupons);
            List<ApplicableCouponDTO> applicableCoupons = new ArrayList<>();

            for (Coupon coupon : coupons) {
                BigDecimal discount = calculateDiscount(cartRequestDTO, coupon);
                if (discount.compareTo(BigDecimal.ZERO) > 0) {
                    ApplicableCouponDTO dto = new ApplicableCouponDTO();
                    dto.setCouponId(coupon.getId());
                    dto.setType(coupon.getType());
                    dto.setDiscount(discount);
                    applicableCoupons.add(dto);
                }
            }

            return applicableCoupons;
        }

        private BigDecimal calculateDiscount(CartRequestDTO cartRequestDTO, Coupon coupon) {
            switch (coupon.getType()) {
                case CART_WISE:
                    return calculateCartWiseDiscount(cartRequestDTO, coupon);
                case PRODUCT_WISE:
                    return calculateProductWiseDiscount(cartRequestDTO, coupon);
                case BXGY:
                    return calculateBxGyDiscount(cartRequestDTO, coupon);
                default:
                    return BigDecimal.ZERO;
            }
        }

    private BigDecimal calculateCartWiseDiscount(CartRequestDTO cartRequestDTO, Coupon coupon) {
        ObjectMapper mapper = new ObjectMapper();
//        String detailsJson = coupon.getDetails();
//        log.info("detailJson is : {} ",detailsJson);
        try {
            JsonNode detailsNode = mapper.readTree(coupon.getDetails());

            JsonNode thresholdNode = detailsNode.get("threshold");
            JsonNode discountNode = detailsNode.get("discount");


            if (thresholdNode == null || discountNode == null) {
                throw new IllegalArgumentException("Invalid coupon details: missing threshold or discount.");
            }

            // Convert the JSON values to BigDecimal
            BigDecimal threshold = new BigDecimal(thresholdNode.asText());
            BigDecimal discountPercentage = new BigDecimal(discountNode.asText());

            log.info("threshold & discountPercentage  is : {},{} ",threshold,discountPercentage);


            // Calculate cart total
            BigDecimal cartTotal = cartRequestDTO.getItems().stream()
                    .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))) // Use BigDecimal
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            log.info("cartTotal is : {} ",cartTotal);

            // Check if cart total exceeds the threshold
            if (cartTotal.compareTo(threshold) > 0) {
                // Apply the discount percentage
                return cartTotal.multiply(discountPercentage).divide(BigDecimal.valueOf(100));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
          return BigDecimal.ZERO;
    }


    private BigDecimal calculateProductWiseDiscount(CartRequestDTO cartRequestDTO, Coupon coupon) {
        ObjectMapper mapper = new ObjectMapper();
        String detailsJson = coupon.getDetails();
        log.info("detailJson is : {} ",detailsJson);
        try {
            JsonNode detailsNode = mapper.readTree(detailsJson);
            BigDecimal threshold = new BigDecimal(detailsNode.get("threshold").asText());
            BigDecimal discountPercentage = new BigDecimal(detailsNode.get("discount").asText());

            log.info("threshold & discountPercentage  is : {},{} ",threshold,discountPercentage);


            // Calculate cart total
            BigDecimal cartTotal = cartRequestDTO.getItems().stream()
                    .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))) // Use BigDecimal
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            log.info("cartTotal is : {} ",cartTotal);

            // Check if cart total exceeds the threshold
            if (cartTotal.compareTo(threshold) > 0) {
                // Apply the discount percentage
                return cartTotal.multiply(discountPercentage).divide(BigDecimal.valueOf(100));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
          return BigDecimal.ZERO;

        }

        private BigDecimal calculateBxGyDiscount(CartRequestDTO cartRequestDTO, Coupon coupon) {
            // Implement BxGy discount logic
            ObjectMapper mapper = new ObjectMapper();
            String detailsJson = coupon.getDetails();
            log.info("detailJson is : {} ",detailsJson);
            try {
                JsonNode detailsNode = mapper.readTree(detailsJson);
                BigDecimal threshold = new BigDecimal(detailsNode.get("threshold").asText());
                BigDecimal discountPercentage = new BigDecimal(detailsNode.get("discount").asText());

                log.info("threshold & discountPercentage  is : {},{} ",threshold,discountPercentage);


                // Calculate cart total
                BigDecimal cartTotal = cartRequestDTO.getItems().stream()
                        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))) // Use BigDecimal
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                log.info("cartTotal is : {} ",cartTotal);

                // Check if cart total exceeds the threshold
                if (cartTotal.compareTo(threshold) > 0) {
                    // Apply the discount percentage
                    return cartTotal.multiply(discountPercentage).divide(BigDecimal.valueOf(100));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return BigDecimal.ZERO;
        }

        public Cart applyCouponToCart(UUID couponId,  CartRequestDTO cartRequestDTO) {
            log.info("before fetching couponId from coupons tables");
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
            log.info("before calculating discount for couponId from coupons tables",coupon);

            BigDecimal discount = calculateDiscount(cartRequestDTO, coupon);

            log.info("after calculating discount for couponId from coupons tables",discount);

            if (discount.compareTo(BigDecimal.ZERO) == 0) {
                throw new InvalidOperationException("Coupon is not applicable to this cart");
            }

            // Apply discount to cart items accordingly
            // For simplicity, apply cart-wise discount proportionally to items
            if (coupon.getType() == Coupon.CouponType.CART_WISE) {
                log.info("before calculating cartTotal for couponId",coupon.getType());

                BigDecimal cartTotal = cartRequestDTO.getItems().stream()
                        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                log.info("after calculating discount for couponId from coupons tables",cartTotal);


                for (CartRequestDTO.CartItemDTO item : cartRequestDTO.getItems()) {
                    log.info("before calculating itemTotal for couponId : ",item);

                    BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    BigDecimal itemDiscount = itemTotal.divide(cartTotal, 4, RoundingMode.HALF_UP)
                            .multiply(discount);
                    item.setDiscount(itemDiscount);
                    log.info("before calculating itemTotal & itemDiscount for couponId : {},{} ",itemTotal,itemDiscount);


                }
            }

            Cart cartEntity = convertToCartEntity(cartRequestDTO);
            log.info("after convertToCartEntity : {}",cartEntity);

            cartRepository.save(cartEntity);

            return cartEntity;
        }


    private Cart convertToCartEntity(CartRequestDTO cartRequestDTO) {
        Cart cart = new Cart();
        cart.setId(cartRequestDTO.getCartId());
        cart.setUserId(cartRequestDTO.getUserId());

        List<CartItem> cartItems = cartRequestDTO.getItems().stream().map(itemDTO -> {
            CartItem item = new CartItem();

            // Fetch the product using the productId
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemDTO.getPrice());
            item.setDiscount(itemDTO.getDiscount());  // Set the calculated discount
            item.setCart(cart);  // Set the parent cart for item
            return item;
        }).collect(Collectors.toList());

        cart.setItems(cartItems);
        return cart;
    }

}
