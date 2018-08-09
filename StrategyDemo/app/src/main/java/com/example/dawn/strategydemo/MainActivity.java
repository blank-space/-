package com.example.dawn.strategydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity{
    public static void main(String[] args) {

        Player player = new Player();
        player.buy(3000);
        System.out.println("玩家需要付钱：" + player.calLastAmount());
        player.buy(1200);
        System.out.println("玩家需要付钱：" + player.calLastAmount());
        player.buy(700);
        System.out.println("玩家需要付钱：" + player.calLastAmount());

    }
}
