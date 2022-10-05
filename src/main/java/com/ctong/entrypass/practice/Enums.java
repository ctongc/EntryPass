package com.ctong.entrypass.practice;

public class Enums {
    public enum RainbowColor { RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE }

    public static void main(String[] args) {
        RainbowColor color = RainbowColor.BLUE;
        for (RainbowColor c : RainbowColor.values()) {
            System.out.println(c);
            System.out.println(c.ordinal());
        }
        System.out.println(RainbowColor.valueOf("RED"));
        System.out.println(RainbowColor.valueOf("red"));
    }
}
