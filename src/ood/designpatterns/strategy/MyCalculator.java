package ood.designpatterns.strategy;

import ood.designpatterns.strategy.strategies.CalculatorOperation;

public class MyCalculator {

    private CalculatorOperation operation;

    public void setOperation(CalculatorOperation operation) {
        this.operation = operation;
    }

    public int operate(final int n1, final int n2) {
        if (operation == null) {
            throw new IllegalStateException("operation unset!");
        }

        return operation.operate(n1, n2);
    }
}
