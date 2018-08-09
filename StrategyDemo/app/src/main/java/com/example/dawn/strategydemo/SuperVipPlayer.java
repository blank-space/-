package com.example.dawn.strategydemo;

/**
 * 超级VIP 6折
 */
@PriceRegion(min = 3000)
public class SuperVipPlayer implements CalPrice {
    @Override
    public float calPrice(float originalPrice) {
        return originalPrice * 0.6f;
    }
}
