package ood.designpatterns;

import ood.designpatterns.strategy.MyCalculator;
import ood.designpatterns.strategy.OperationAdd;
import ood.designpatterns.strategy.OperationSubtract;

/**
 * Strategy Pattern
 * 策略模式
 */
public class StrategyPatternDemo {

    public static void main(String[] args) {
        final MyCalculator calculator = new MyCalculator();

        calculator.setOperation(new OperationAdd());
        System.out.println("Addition operation: " + calculator.operate(5, 4));

        calculator.setOperation(new OperationSubtract());
        System.out.println("Subtraction operation: " + calculator.operate(5, 4));
    }
}
