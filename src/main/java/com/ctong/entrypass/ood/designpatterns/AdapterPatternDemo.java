package com.ctong.entrypass.ood.designpatterns;

import com.ctong.entrypass.ood.designpatterns.adapter.PlayerAdapter;
import com.ctong.entrypass.ood.designpatterns.adapter.VideoPlayer;

/**
 * Adapter Pattern
 * 适配器模式
 * 它能使接口不兼容的对象能够相互合作
 */
public class AdapterPatternDemo {

    public static void main(String[] args) {
        VideoPlayer player = new PlayerAdapter();

        player.play("avi", "xxx.avi");
        player.play("wmv", "xxx.wmv");
        player.play("mp4", "xxx.mp4");
    }
}
