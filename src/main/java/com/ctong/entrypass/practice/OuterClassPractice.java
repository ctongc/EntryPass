package com.ctong.entrypass.practice;

class OuterClass {
    private static String msg = "Message";
    private int value = 0;

    public static class NestedStaticClass {
        // only static members of Outer class is directly accessible in nested static class
        public void printMessage() {
            // compile error if making msg a non-static variable
            System.out.println("Message from nested static class: " + msg);
        }
        private static void printOut() {}
    }

    class InnerClass {
        // both static and non-static members of outer class are accessible in this inner class
        public void display() {
            System.out.println("Message from non-static nested class: " + msg + " " + value);
        }
        // can we declare a static method here? NO
        // static method can only be declared in a static class or top-level class
        // public static void printOut() { }
        public OuterClass getOuterClass () {
            return OuterClass.this;
        }
    }
}
class AnonymousInnerClass {
    public void test() { // runnable is an interface
        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();
    }

    class NoneAnonymousClass implements Runnable {
        @Override
        public void run() {

        }
    }

    public void test2() {
        NoneAnonymousClass t = new NoneAnonymousClass();
        new Thread(t).start();
    }
}

public class OuterClassPractice {
    // how to create instance of static and non-static nested class
    public static void main(String[] args) {
        // create instance of nested static class
        OuterClass.NestedStaticClass printer = new OuterClass.NestedStaticClass(); // Map.Entry
        // call non-static method of nested static class
        printer.printMessage();

        // In order to create instance of inner class we need an Outer class instance
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.new InnerClass();

        // calling non-static method of Inner class
        inner.display();

        // we can also combine above steps in one step
        OuterClass.InnerClass inner2 = new OuterClass().new InnerClass();
        inner2.display();
    }
}