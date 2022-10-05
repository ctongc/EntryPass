package com.ctong.entrypass.ood.designpatterns.decorator.decorators;

import com.ctong.entrypass.ood.designpatterns.decorator.Customer;

public abstract class GoodsDecorator implements Customer {

    protected final Customer customer;

    public GoodsDecorator(Customer customer) {
        this.customer = customer;
    }
}
