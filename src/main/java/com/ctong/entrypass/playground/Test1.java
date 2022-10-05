package com.ctong.entrypass.playground;

public class Test1 {
    public void permutationOfIfBlocks(int n) {
        if (n <= 0) {
            return;
        }
        StringBuilder prefix = new StringBuilder();
        printIfBlocks(n, 0, 0, prefix);
    }

    private void printIfBlocks(int n, int left, int right, StringBuilder prefix) {
        // base case: not bracket left
        if (left == n && right == n) {
            formatAndPrint(prefix.toString().toCharArray());
            return;
        }

        // print "if {"
        if (left < n) {
            prefix.append("{");
            printIfBlocks(n, left + 1, right, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
        if (left > right) {
            prefix.append("}");
            printIfBlocks(n, left, right + 1, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public void formatAndPrint(char[] input) {
        int count = 0;
        for (char c : input) {
            if (c == '{') {
                printIndent(count);
                System.out.println("if {");
                count++;
            } else {
                count--;
                printIndent(count);
                System.out.println("}");
            }
        }
        System.out.println();
    }

    public void printIndent(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("  ");
        }
    }

    public static void main(String[] args) {
        Test1 t = new Test1();
        t.permutationOfIfBlocks(3);
    }
}
