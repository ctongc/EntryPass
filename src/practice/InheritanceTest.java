package practice;

import java.io.IOException;

class Parent {
    public static void staticMethod() {
        System.out.println("Parent staticMethod run");
    }
}

class Son extends Parent {
    public static void staticMethod() {
        System.out.println("Son staticMethod run");
    }
}

class Person {
    private String name;
    public Person(String name) { this.name = name; }
    public String getName() { return name; }
    public void setName(String nane) { this.name  = name; }

    public void setName(Person other) {
        this.name  = other.name;
    }
    public void changeName() { this.name = "new name"; }
}

class Employee extends Person {
    private String company;

    public Employee(String name, String company) {
        super(name);
        this.company = company;
    }

    public void setCompany(String company) { this.company = company; }
    public String getCompany() { return company; }
}

class A {
    public String show(D obj) {
        return ("A and D");
    }
    public String show(A obj) {
        return ("A and A");
    }
}
class B extends A {
    public String show(B obj) {
        return ("B and B");
    }
    public String show(A obj) {
        return ("B and A");
    }
}
class C extends B {}
class D extends B {}

public class InheritanceTest {

    public static void main(String[] args) {
        Parent child = new Son();
        child.staticMethod(); // 输出 "Parent staticMethod run"

        Son child2 = new Son();
        child2.staticMethod();


        A a1 = new A();
        A a2 = new B();
        B b = new B();
        C c = new C();
        D d = new D();

        System.out.println("1--" + a1.show(b)); // A and A
        System.out.println("2--" + a1.show(c)); // A and A
        System.out.println("3--" + a1.show(d)); // A and D
        System.out.println("4--" + a2.show(b)); // B and A, reference A 决定了调用A里的方法, Instance B 决定了使用B里的版本
        System.out.println("5--" + a2.show(c)); // B and A
        System.out.println("6--" + a2.show(d)); // A and D
        System.out.println("7--" + b.show(b)); // B and B
        System.out.println("8--" + b.show(c)); // B and B
        System.out.println("9--" + b.show(d)); // A and D, 父类A里有直接调用D的方法

        Person p1 = new Person("John");
        Person p2 = new Person("Steven");
        p2.setName(p1); // p1's private members are open to person 2
        System.out.println(p2.getName());

        int digits = (int) (Math.log(1000) / Math.log(5));
        System.out.println(digits);

    }
}
