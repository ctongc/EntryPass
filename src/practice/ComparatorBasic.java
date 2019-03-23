package practice;

import java.util.*;

class Cell {
    int row;
    int column;
    int value;

    public Cell(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }
}

class CellComparator implements Comparator<Cell> {
    @Override
    public int compare(Cell c1, Cell c2) {
        if (c1.value == c2.value) {
            return 0;
        }
        return c1.value < c2.value ? -1 : 1;
    }
}

class Cell2 implements Comparable<Cell2> {
    int row;
    int column;
    int value;

    public Cell2(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    @Override
    public int compareTo(Cell2 c2) {
        if (this.value == c2.value) {
            return 0;
        }
        return this.value > c2.value ? -1 : 1;
    }
}

class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }
}

public class ComparatorBasic {
    public static void main(String[] args) {
        // Top-level class (recommended, for project organization cleanness)
        PriorityQueue<Cell> minHeap = new PriorityQueue<>(new CellComparator());

        // Static nested class

        // Anonymous class (not recommended)
        PriorityQueue<Cell> maxHeap = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                if (c1.value == c2.value) {
                    return 0;
                }
                return c1.value > c2.value ? -1 : 1;
            }
        });

        // PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        // Lambda expressions
        PriorityQueue<Cell2> minHeap2 = new PriorityQueue<>((c1, c2) -> c2.compareTo(c1));
        // statement lambda replaced with expression lambda((c1, c2) -> {return c2.compareTo(c1)})

        PriorityQueue<Cell2> minHeap2new = new PriorityQueue<>(Comparator.comparingInt(Cell2 -> Cell2.value));
        PriorityQueue<Cell2> minHeap2new2 = new PriorityQueue<>(Comparator.comparingInt((Cell2 c) -> c.value));
        PriorityQueue<Student> maxHeap3 = new PriorityQueue<>(Comparator.comparing(Student :: getAge).reversed());
        // (Comparator.comparing(this::aSelfWrite Method));

        // Heapify
        List<Cell2> cellList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cellList.add(new Cell2(0,i,i+1));
        }
        PriorityQueue<Cell2> maxHeapForHeapify = new PriorityQueue<>(cellList);
        System.out.println(maxHeapForHeapify.peek().value);

        // For list
        List<Cell> cells = new ArrayList<>();
        cells.sort((c1, c2) -> Integer.compare(c1.value, c2.value));
        cells.sort(Comparator.comparingInt(Cell -> Cell.value));   // use this
        List<Student> students = new ArrayList<>();
        students.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
        students.sort(Comparator.comparing(Student :: getName)); // use this
        students.sort(Comparator.comparing(Student :: getName).reversed());
        students.sort(Comparator.comparing(Student :: getName).thenComparing(Student :: getAge));
        // students.sort((s1, s2) -> s1.getAge().compareTo(s2.getAge())); 这个不行因为int没有compareTo
        students.sort(Comparator.comparing(Student :: getAge)); // use this

        students.sort((s1, s2)-> {
            if(s1.getName().equals(s2.getName())) {
                if (s1.getAge() == s2.getAge()) {
                    return 0;
                } else {
                    return s1.getAge() < s2.getAge()? -1 : 1;
                }
            } else {
                return s1.getName().compareTo(s2.getName());
            }
        });

        int[] nums = {1,2,3};

        Map<Integer, Integer> map = new HashMap<>(); // stores <nums, freq>
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap123 = new PriorityQueue<>(5,
                Comparator.comparing((Map.Entry<Integer, Integer> ele) -> ele.getValue()));
        PriorityQueue<Integer> minHeap1234 = new PriorityQueue<>(5,
                Comparator.comparingInt((Integer i) -> map.get(i)));
    }
}
