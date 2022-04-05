package practice;

import java.util.ArrayList;
import java.util.List;

class GenericNode<T> {
    private T value;

    public GenericNode(T v) {
        this.value = v;
    }

    public String toString() {
        return value.toString();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T v) {
        this.value = v;
    }
}

class MyPair<K, V> {
    private K key;
    private V value;

    public MyPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        return "MyPair [key = " + key + ", value = " + value + "]";
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

class MyPairUtil {
    public static <K, V> boolean isEqual (MyPair<K, V> p1, MyPair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) && p1.getValue().equals(p2.getValue());
    }
}

public class Generic {
    // generic method printArray
    public static <E> void printArray(E[] inputArray) {
        // Display array elements
        for (E element: inputArray) {
            System.out.printf("%s ", element.toString());
        }
    }

    public static <E extends Comparable<E>> E getMin(E[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        E min = array[0];
        for (E e : array) {
            // min = e < min ? e : min;
            min = e.compareTo(min) < 0 ? e : min;
        }
        return min;
    }

    public static void main(String[] args) {
        // arguments can't be primitive types
        Integer[] integerArray = { 1, 2, 3, 4, 5 };
        Double[] doubleArray = { 1.1, 2.2, 3.3, 4.4, 5.5 };
        Character[] characterArray = { 'A', 'B', 'C', 'D', 'E' };

        System.out.println("Array integerArray contains:");
        printArray(integerArray);
        System.out.println("\nArray doubleArray contains:");
        printArray(doubleArray);
        System.out.println("\nArray characterArray contains:");
        printArray(characterArray);
        System.out.println();

        GenericNode<Integer> node = new GenericNode<>(1);
        GenericNode<Integer> node2 = new GenericNode(1); // not good(warning), just like ArrayList
        System.out.println(node.toString());
        node.setValue(2);
        System.out.println(node.getValue());

        MyPair<String, Integer> p1 = new MyPair<>("A", 1);
        MyPair<String, Integer> p2 = new MyPair<>("B", 2);
        MyPair<String, Integer> p3 = new MyPair<>("A", 1);
        // note that there is no need to call p1.toString()
        System.out.println(p1 + " compares with " + p2 + " : " + MyPairUtil.isEqual(p1, p2));
        System.out.println(p1 + " compares with " + p3 + " : " + MyPairUtil.<String, Integer>isEqual(p1, p3));

        // generic type erase
        ArrayList<Integer> a1 = new ArrayList<>();
        ArrayList<String> a2 = new ArrayList<>();
        Class c1 = a1.getClass();
        Class c2 = a2.getClass();
        System.out.println("c1 == c2 ? " + (c1 == c2)); // 判断两个类型相同
        System.out.println(c1); // class java.util.ArrayList
        try {
            // 成功将字符串插入整数数组
            a1.getClass().getMethod("add", Object.class).invoke(a1, "a");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        // ? extends
        List<Apple> apples = new ArrayList<>();
        List<? extends Fruit> fruits = apples;
        fruits.add(null);
        Fruit fruit = fruits.get(0); // read time is fine
        Apple apple = (Apple) fruits.get(0);
        // fruits.add(new Apple()); // compile error
        // fruits.add(new Fruit()); // compile error

        // ? super
        List<Fruit> fruits2 = new ArrayList<>();
        List<? super Apple> apples2 = fruits2;
        apples2.add(new Apple());
        apples2.add(new GreenApple());
        // apples2.add(new Fruit()); // compile time error
        // apples2.add((Apple) new Fruit()); // compile time ok, runtime error
        // apples2.add(new Orange()); // compile time error
        // Fruit fruit3 = apples2.get(0); // compile time error, apples.get(0) is Object
        Fruit fruit4 = (Fruit) apples2.get(0); // ok
    }
}

class Apple extends Fruit {

}

class Fruit {

}

class GreenApple extends Apple {

}

class Orange extends Fruit {

}
