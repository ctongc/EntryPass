package com.ctong.entrypass.ood.designpatterns.observer.observers;

public class Customer implements Subscriber {

    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println("Customer " + name + " received update.");
    }
}
