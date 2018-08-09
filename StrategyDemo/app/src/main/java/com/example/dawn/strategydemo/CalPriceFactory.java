package com.example.dawn.strategydemo;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单工厂模式，缺点：对修改开放
 * 根据累计消费的额度，给与不同的折扣
 */
public class CalPriceFactory {
    private static final String CAL_PRICE_PACKAGE = "com.example.dawn.strategydemo";//这里是一个常量，表示我们扫描策略的包
    private ClassLoader classLoader = getClass().getClassLoader();
    private List<Class<? extends CalPrice>> calPriceList;



    private CalPriceFactory() {
        initial();
    }

    private void initial() {
        calPriceList=new ArrayList<>();
        File[] resources=getResources();
        Class<CalPrice>  calPriceClass=null;
        try {
            //使用相同的加载器加载策略接口
            calPriceClass=(Class<CalPrice>) classLoader.loadClass(CalPrice.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("未找到策略接口");
        }

        for (int i = 0; i <resources.length; i++) {
            try {
                Class<?>  clazz=classLoader.loadClass(CAL_PRICE_PACKAGE+"."+resources[i].getName().replace(".class", ""));
                //判断是否是CalPrice的实现类并且不是CalPrice它本身，满足的话加入到策略列表
                if (CalPrice.class.isAssignableFrom(clazz) && clazz != calPriceClass) {
                    calPriceList.add((Class<? extends CalPrice>) clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    //获取扫描的包下面所有的class文件
    private File[] getResources() {
        try {
            File file = new File(classLoader.getResource(CAL_PRICE_PACKAGE.replace(".", "/")).toURI());
            return file.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (pathname.getName().endsWith(".class")) {//我们只扫描class文件
                        return true;
                    }
                    return false;
                }
            });
        } catch (URISyntaxException e) {
            throw new RuntimeException("未找到策略资源");
        }
    }

    public static CalPriceFactory getInstance() {
        return CalPriceFactoryInstance.instance;
    }

    private static class CalPriceFactoryInstance {

        private static CalPriceFactory instance = new CalPriceFactory();
    }

    public  CalPrice createCalPrice(Player player) {

        for (Class<? extends  CalPrice> clazz :calPriceList){
            //获取该策略的注解
           PriceRegion  validRegion=handleAnnotation(clazz);
            //判断金额是否在注解的区间
            if(player.getTotalAmount()>=validRegion.min() && player.getTotalAmount()<validRegion.max()){
                try {
                    //返回一个当前策略所在的实例,例如VipPlayer
                    return clazz.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("策略获得失败");
                }
            }
        }
        throw new RuntimeException("策略获得失败");
    }

    //处理注解，我们传入一个策略类，返回它的注解
    private PriceRegion handleAnnotation(Class<? extends CalPrice> clazz) {
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i] instanceof PriceRegion) {
                return (PriceRegion) annotations[i];
            }
        }
        return null;
    }
}

