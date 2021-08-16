package practice;

import java.util.LinkedList;
import java.util.ListIterator;

public class IteratorPractice {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        for(ListIterator<Integer> it = list.listIterator(); it.hasNext();) {
            System.out.println("One step forward: " + it.next());
            if (it.hasNext()) {
                System.out.println("Another step forward: " + it.next());
                System.out.println("One step backward: " + it.previous());
            }
        }
    }
}
