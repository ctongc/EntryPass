package ood.designpatterns;

import ood.designpatterns.singleton.EagerSingleton;
import ood.designpatterns.singleton.LazySingleton;

public class SingletonDemo {
    public static void main(String[] args) {
        EagerSingleton instance = EagerSingleton.getInstance();
        LazySingleton instance1 = LazySingleton.getInstance();

        instance.print();
        instance1.print();
    }
}
