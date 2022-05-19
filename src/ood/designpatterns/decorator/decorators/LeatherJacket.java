package ood.designpatterns.decorator.decorators;

import ood.designpatterns.decorator.Customer;

public class LeatherJacket extends GoodsDecorator {

    public LeatherJacket(final Customer customer) {
        super(customer);
    }

    @Override
    public Double bill() {
        return customer.bill() + 500;
    }

    @Override
    public void show() {
        customer.show();
        System.out.println("RichCustomer bought a leather jacket that costs 500, total bill: " + this.bill());
    }
}
