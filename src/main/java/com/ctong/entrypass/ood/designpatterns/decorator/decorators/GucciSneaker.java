package com.ctong.entrypass.ood.designpatterns.decorator.decorators;

import com.ctong.entrypass.ood.designpatterns.decorator.Customer;

public class GucciSneaker extends GoodsDecorator {

    public GucciSneaker(final Customer customer) {
        super(customer);
    }

    @Override
    public Double bill() {
        return customer.bill() + 1000;
    }

    @Override
    public void show() {
        customer.show();
        System.out.println("RichCustomer bought a pair of GucciSneaker that costs 1000, total bill: " + this.bill());
    }
}
