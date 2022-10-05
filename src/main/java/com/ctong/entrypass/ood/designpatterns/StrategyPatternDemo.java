package com.ctong.entrypass.ood.designpatterns;

import com.ctong.entrypass.ood.designpatterns.strategy.MyCalculator;
import com.ctong.entrypass.ood.designpatterns.strategy.strategies.OperationAdd;
import com.ctong.entrypass.ood.designpatterns.strategy.strategies.OperationSubtract;

/**
 * Strategy Pattern
 * 策略模式
 * 定义一系列算法, 将每种算法分别放入独立的类中, 以使算法的对象能够相互替换
 * 优点: 遵循了开闭原则, 扩展性良好
 * 缺点: 随着策略增加, 策略类也会越来越多, 需要doc好已有策略
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
