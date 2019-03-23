package practice;

import java.util.*;

public class Practice {
    public static void main(String[] args) {
        Queue<String> queue1 = new ArrayDeque<>();  // 只有offer()
        Deque<String> queue2 = new ArrayDeque<>();  // 还有offerFirst() offerLast()

        queue1.offer("");
        ((ArrayDeque<String>) queue1).offerLast("");
        queue2.offerFirst("");

        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list);
        list.sort((Integer n1, Integer n2) -> Integer.valueOf(n2).compareTo(Integer.valueOf(n1)));
        System.out.println(list);
        list.remove(3);
        list.remove(Integer.valueOf(3));
        System.out.println(list);

        int a = 3;
        int b = 7;

        double c = 7 / 3.0;
        System.out.println(c);
        char cc = 'a' - 32;
        System.out.println(cc);
        System.out.println('9'-'3' == 6);
    }
}
