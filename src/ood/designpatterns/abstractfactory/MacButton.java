package ood.designpatterns.abstractfactory;

public class MacButton implements Button {
    @Override
    public void paint() {
        System.out.println("Inside MacButton :: paint() method");
    }
}
