package utils;

import java.io.*;

class MyException extends Exception {
    public MyException() {
        super();
    }

    public MyException(String msg) {
        super(msg + " of type my exception");
    }
}

public class Exceptions {
    // 2. leave Exception to JVM
    public static void executeWithJVMExceptionHandling() throws Exception {
        final FileInputStream fs = new FileInputStream("D:/temp/a.txt");
    }

    // 3. throw Exception by ourselves
    public static void executeWithManuallyExceptionHandling() throws Exception {
        System.out.println("execute...");
        throw new Exception();
    }

    // 4. customize Exception
    public static void execute(final String a) throws MyException {
        System.out.println("execute...");
        if ("true".equals(a)) {
            throw new MyException("it can be true");
        }
    }

    public static void main(String[] args) throws Exception, MyException {
        // 1. regular exception blocks
        try {
            final FileInputStream fs = new FileInputStream("D:/temp/a.txt");
        } catch (FileNotFoundException e) {
            System.out.println("catch FileNotFoundException");
            e.printStackTrace();
        } finally {
            // no matter you get in try or catch, you must run finally after that
            System.out.println("finally...");
        }

        // 2. leave Exception to JVM
        executeWithJVMExceptionHandling();

        // 3. throw Exception by ourselves
        try {
            executeWithManuallyExceptionHandling();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // MyException
        execute("true");
    }

    /**
     * Try with resources - New improvements in Java 7
     */
    static String readFirstLineFromFile(final String path) throws IOException {
        try (final BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

    // normally you need to:
    static String readFirstLineFromFileWithFinallyBlock(String path) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            return br.readLine();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    /**
     * Handling multiple types of Exceptions - New improvements in Java 7
     */
    static void doSomething() {
        try {
            final FileInputStream fs = new FileInputStream("D:/temp/a.txt");
        } catch (SecurityException | FileNotFoundException e) { // Note it is "|" not "||", syntax rule
            System.out.println("Caught SecurityException or FileNotFoundException.");
        } catch (Exception e) {
            System.out.println("Unhandled Exceptions.");
        }
    }
}