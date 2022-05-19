package ood.designpatterns.strategy.strategies;

public class OperationAdd implements CalculatorOperation {

    @Override
    public int operate(final int n1, final int n2) {
        return n1 + n2;
    }
}
