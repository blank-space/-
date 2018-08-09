package com.example.dawn.strategydemo;

/**
 * 普通玩家
 */
@PriceRegion(max = 1000)
public class PlainPlayer implements CalPrice {
    @Override
    public float calPrice(float originalPrice) {
        return originalPrice;
    }
}
