package playground;

import java.util.*;
import java.util.stream.Collectors;

public class Practice {
    public static void main(String[] args) {
        Queue<String> queue1 = new ArrayDeque<>();  // 只有offer()
        Deque<String> queue2 = new ArrayDeque<>();  // 还有offerFirst() offerLast()
        queue1.offer("");
        ((ArrayDeque<String>) queue1).offerLast("");
        queue2.offerFirst("");

        List<Integer> list = new LinkedList<>(Arrays.asList(1,2,3,4,4));
        System.out.println(list);
        list.sort(Comparator.reverseOrder());
        System.out.println(list);
        List<Integer> list1 = new ArrayList<>(list);
        List<Integer> list2 = new ArrayList<>(list);
        List<Integer> list3 = new ArrayList<>(list);
        list1.remove(3);
        System.out.println(list1);
        list2.remove(Integer.valueOf(3));
        System.out.println(list2);
        list3.removeAll(Arrays.asList(4));
        System.out.println(list3);

        List<Character> chars = new ArrayList<>(List.of('a','b'));
        chars.remove(Character.valueOf('a'));
        System.out.println(chars);

        List<String> strings = new ArrayList<>(List.of("aa", "bb", "cc"));
        String[] stringsArray = strings.toArray(new String[0]);
        System.out.println(strings);
        for (String s : stringsArray) {
            System.out.print(s + " ");
        }
        System.out.println();

        int a = 3;
        int b = 7;
        double c = 7 / 3.0;
        System.out.println(c);
        char cc = 'a' - 32;
        System.out.println(cc);
        System.out.println('9'-'3' == 6);

        Integer[] numArray = {1, 2, 3, 4, 5, 6, 7};
        List<Integer> integers = Arrays.stream(numArray).collect(Collectors.toList());
        integers.removeIf(filter -> filter % 2 == 0);
        System.out.println(integers);
    }
}
