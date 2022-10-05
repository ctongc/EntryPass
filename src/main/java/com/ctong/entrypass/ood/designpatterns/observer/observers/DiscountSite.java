package com.ctong.entrypass.ood.designpatterns.observer.observers;

public class DiscountSite implements Subscriber {

    private final String name;

    public DiscountSite(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println("DiscountSite " + name + " received update.");
    }
}
