package ood.designpatterns.strategy;

public class OperationSubtract implements CalculatorOperation {

    @Override
    public int operate(final int n1, final int n2) {
        return n1 - n2;
    }
}
