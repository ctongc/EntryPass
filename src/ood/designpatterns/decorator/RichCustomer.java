package ood.designpatterns.decorator;

public class RichCustomer implements Customer {

    private final String name;

    public RichCustomer(String name) {
        this.name = name;
    }

    @Override
    public Double bill() {
        return 0.0d;
    }

    @Override
    public void show() {
        System.out.println("RichCustomer " + name + " spent " + this.bill());
    }
}
