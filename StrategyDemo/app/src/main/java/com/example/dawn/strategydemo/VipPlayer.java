package com.example.dawn.strategydemo;

/**
 * vip  8折
 */
@PriceRegion(max = 2999,min = 1001)
public class VipPlayer implements CalPrice {
    @Override
    public float calPrice(float originalPrice) {
        return originalPrice * 0.8f;
    }
}
