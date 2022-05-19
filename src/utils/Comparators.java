package utils;

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

class ComparableCell implements Comparable<ComparableCell> {

    int row;
    int column;
    int value;

    public ComparableCell(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    @Override
    public int compareTo(ComparableCell c2) {
        if (this.value == c2.value) {
            return 0;
        }

        return this.value > c2.value ? -1 : 1;
    }
}

class Student {

    private final String name;
    private final int age;
    public final String gender;

    public Student(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }
}

@SuppressWarnings("all")
public class Comparators {

    static class StudentComparator implements Comparator<Student> {

        @Override
        public int compare(Student s1, Student s2) {
            if (s1.getAge() == s2.getAge()) {
                return 0;
            }
            return s1.getAge() < s1.getAge() ? -1 : 1;
        }
    }

    /**
     * Possible ways to provide a comparator class
     * 1. Top-level class
     * 2. Static nested class
     * 3. Anonymous class (not recommended)
     * 4. Lambda expressions (recommended)
     * 5. Comparator Interface
     */
    public static void fiveRegularWays() {
        // 1. Top-level class
        PriorityQueue<Cell> minHeapCells = new PriorityQueue<>(new CellComparator());
        List<Cell> cells = new ArrayList<>();
        cells.sort(new CellComparator());

        // 2. Static nested class
        PriorityQueue<Student> minHeapStudents = new PriorityQueue<>(new StudentComparator());
        List<Student> students = new ArrayList<>();
        students.sort(new StudentComparator());

        // 3. Anonymous class (not recommended)
        PriorityQueue<Cell> maxHeap = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                if (c1.value == c2.value) {
                    return 0;
                }
                return c1.value > c2.value ? -1 : 1;
            }
        });
        List<Student> students2 = new ArrayList<>();
        students2.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });

        // 4. Lambda expressions
        PriorityQueue<ComparableCell> minHeapComparableCells = new PriorityQueue<>((c1, c2) -> c2.compareTo(c1));
        List<Student> students3 = new ArrayList<>();
        students3.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));

        // 5. Comparator Interface
        PriorityQueue<ComparableCell> minHeap2new2 = new PriorityQueue<>(Comparator.comparingInt((ComparableCell c) -> c.value));
        List<Student> students4 = new ArrayList<>();
        students4.sort(Comparator.comparing(Student::getName).thenComparing(Student::getAge).thenComparing(Student -> Student.gender));
        // this is equivalent to
        students4.sort((s1, s2) -> {
            if(s1.getName().equals(s2.getName())) {
                if (s1.getAge() == s2.getAge()) {
                    return s1.gender.compareTo(s2.gender);
                } else {
                    return s1.getAge() < s2.getAge()? -1 : 1;
                }
            } else {
                return s1.getName().compareTo(s2.getName());
            }
        });
    }

    public static void main(String[] args) {
        // cast (Student s) is important for chained comparators on field members (appearing first) without getter
        PriorityQueue<Student> minHeapStudents = new PriorityQueue<>(Comparator.comparing((Student s) -> s.gender).thenComparingInt(Student::getAge));
        PriorityQueue<Student> minHeapStudents2 = new PriorityQueue<>(Comparator.comparingInt(Student::getAge).thenComparing(Student -> Student.gender));
        List<Student> students = new ArrayList<>();
        students.sort(Comparator.comparing(Student::getName).thenComparing(Student -> Student.gender));
        students.sort(Comparator.comparing((Student s) -> s.gender).thenComparing(Student::getName));

        // students.sort((s1, s2) -> s1.getAge().compareTo(s2.getAge())); 这个不行因为int没有compareTo
        students.sort(Comparator.comparingInt(Student::getAge));

        // Collections.sort() are Java 8 onwards
        List<Cell> cells = new ArrayList<>();
        Collections.sort(cells, new CellComparator());

        // reverse: Collections.reverseOrder() vs Comparator.comparing().reverse()
        PriorityQueue<ComparableCell> minHeap = new PriorityQueue<>();
        PriorityQueue<ComparableCell> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Cell> maxHeap2 = new PriorityQueue<>(Collections.reverseOrder(new CellComparator()));
        PriorityQueue<Cell> maxHeap3 = new PriorityQueue<>(Comparator.comparing((Cell c) -> c.row).thenComparing(c -> c.column).reversed());

        int[] nums = {1, 2, 3};
        Map<Integer, Integer> map = new HashMap<>(); // stores <nums, freq>
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        // 1,2,3,4 are equivalent
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap1 = new PriorityQueue<>(3, Map.Entry.comparingByValue());
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap2 = new PriorityQueue<>(3, Comparator.comparing(Map.Entry::getValue));
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap3 = new PriorityQueue<>(3,
                Comparator.comparing((Map.Entry<Integer, Integer> ele) -> ele.getValue()));
        PriorityQueue<Integer> minHeap4 = new PriorityQueue<>(3, Comparator.comparingInt(map::get));

        // 1,2,3 are equivalent
        PriorityQueue<List<Integer>> pq1 = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                double dis1 = Math.sqrt(o1.get(0) * o1.get(0) + o1.get(1) * o1.get(1) + o1.get(2) * o1.get(2));
                double dis2 = Math.sqrt(o2.get(0) * o2.get(0) + o2.get(1) * o2.get(1) + o2.get(2) * o2.get(2));
                if (dis1 == dis2) {
                    return 0;
                }
                return dis1 < dis2 ? -1 : 1;
            }
        });
        PriorityQueue<List<Integer>> pq2 = new PriorityQueue<>((o1, o2) -> {
            double dis1 = Math.sqrt(o1.get(0) * o1.get(0) + o1.get(1) * o1.get(1) + o1.get(2) * o1.get(2));
            double dis2 = Math.sqrt(o2.get(0) * o2.get(0) + o2.get(1) * o2.get(1) + o2.get(2) * o2.get(2));
            if (dis1 == dis2) {
                return 0;
            }
            return dis1 < dis2 ? -1 : 1;
        });
        PriorityQueue<List<Integer>> pq3 = new PriorityQueue<>(
                Comparator.comparing(
                        (List<Integer> list) ->
                                Math.sqrt(list.get(0)*list.get(0)
                                        + list.get(0)*list.get(0)
                                        + list.get(0)*list.get(0))));

        int[][] intervals = new int[0][0];
        Arrays.sort(intervals, Comparator.comparingInt((int[] a) -> a[0]));
        List<int[]> a = Arrays.asList(intervals);

        String[] strs = {"c", "bb", "aaa"};
        Arrays.sort(strs, Comparator.comparingInt(String::length));

        // Heapify
        List<ComparableCell> cellList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cellList.add(new ComparableCell(0, i,i + 1));
        }
        PriorityQueue<ComparableCell> maxHeapForHeapify = new PriorityQueue<>(cellList);
        System.out.println(maxHeapForHeapify.peek().value);
    }
}
