package com.example.dawn.strategydemo;

public class Player {

    private float totalAmount = 0.0f;//总消费
    private float amount = 0.0f;//单次消费
    private CalPrice calPrice ;

    public void buy(float amount) {
        this.amount = amount;
        totalAmount += amount;
//        if(totalAmount>3000){
//            calPrice=new SuperVipPlayer();
//        }else if(totalAmount >2000){
//            calPrice=new VipPlayer();
//        }
        /* 变化点，我们将策略的制定转移给了策略工厂，将这部分责任分离出去 */
        calPrice = CalPriceFactory.getInstance().createCalPrice(this);
    }

    ///计算客户每次要付的钱
    public float calLastAmount() {
        return calPrice.calPrice(amount);
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
