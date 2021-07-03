package playground;

/**
 * playground.ListArrayTest.java - Lab 4
 * @author
 * @author
 * CIS 36B
 */

public class ListArrayTest {
    public static void main(String[] args) {
        System.out.println("***Testing Default Constructor and One Parameter Constructor***\n");
        ListArray test1 = new ListArray();

        System.out.println("After default constructor called:");
        System.out.println("Should print size of 0: " 
                + test1.size());
        System.out.println("Should print the list array is empty - true: " 
                + test1.isEmpty());
        System.out.println("Should print []: " + test1);
        
        ListArray test2 = new ListArray(15);
        System.out.println("\nAfter one parameter constructor called:");
        System.out.println("\nShould print size of 0: " 
                + test2.size());
        System.out.println("Should print the list array is empty - true: " 
                + test2.isEmpty());
        System.out.println("Should print []: " + test2);
        
        System.out.println("\n***Testing Add Methods***\n");
        test1.add("B");
        System.out.println("Adding B: ");
        System.out.println("Should print size of 1: " 
                + test1.size());
        System.out.println("Should print the list array is empty - false: " 
                + test1.isEmpty());
        System.out.println("Should print B at index 0: " 
                + test1.get(0));
        System.out.println("Should print [B]: " + test1);
        
        test1.add("D");
        System.out.println("\nAdding D: ");
        System.out.println("Should print size of 2: " 
                + test1.size());
        System.out.println("Should print the list array is empty - false: " 
                + test1.isEmpty());
        System.out.println("Should print B at index 0: " 
                + test1.get(0));
        System.out.println("Should print D at index 1: " 
                + test1.get(1));
        System.out.println("Should print [B, D]: " 
                + test1);
        
        System.out.println("\nShould print error message. Cannot add at index 10:");
        test1.add(10, "A");
    
        test1.add(0, "A");
        System.out.println("\nAdding A at index 0: ");
        System.out.println("Should print size of 3: " 
                + test1.size());
        System.out.println("Should print the list array is empty - false: " 
                + test1.isEmpty());
        System.out.println("Should print A at index 0: " 
                + test1.get(0));
        System.out.println("Should print B at index 1: " 
                + test1.get(1));
        System.out.println("Should print [A, B, D]: " + test1);


        test1.add(2, "C");
        System.out.println("\nAdding C at index 2: ");
        System.out.println("Should print size of 4: " 
                + test1.size());
        System.out.println("Should print the list array is empty - false: " 
                + test1.isEmpty());
        System.out.println("Should print A at index 0: " 
                + test1.get(0));
        System.out.println("Should print C at index 2: " 
                + test1.get(2));
        System.out.println("Should print [A, B, C, D]: " + test1);

        System.out.println("\nAdding 1-20 in playground.ListArray of size 15. Should resize to 20:");
        for(int i = 1; i <= 20; i++) {
            test2.add("" + i);
        }
        System.out.println("Should print size of 20: " 
                + test2.size());
        System.out.println("Should print the list array is empty - false: " 
                + test2.isEmpty());
        System.out.println("Should print 1 at index 0: " 
                + test2.get(0));
        System.out.println("Should print 20 at index 19: " 
                + test2.get(19));

        System.out.println("\n***Testing Copy Constructor***\n");
        ListArray test3 = new ListArray(test1);
        System.out.println("Should print size of 4: " 
                + test1.size());
        System.out.println("Should print the list array is empty - false: " 
                + test3.isEmpty());
        System.out.println("Should print A at index 0: " 
                + test3.get(0));
        System.out.println("Should print C at index 2: " 
                + test3.get(2));
        System.out.println("Should print [A, B, C, D]: " + test3);
        
        System.out.println("\nTesting for deep copy: ");
        test3.add("A");
        test3.add("B");
        test3.add("C");
        System.out.println("Original list array size should be 4: " 
                + test1.size());
        System.out.println("New list array size should be 7: " 
                + test3.size());
        System.out.println("Printing original [A, B, C, D]: " + test1);
        System.out.println("Printing new [A, B, C, D, A, B, C]: " + test3);

        System.out.println("\n***Testing Set Method***\n");
        System.out.println("Should print error message when setting index 6: ");
        test1.set(6, "Z");

        test1.set(2, "Z");
        System.out.println("\nShould print size of 4: " 
                + test1.size());
        System.out.println("Should print the list array is empty - false: " 
                + test1.isEmpty());
        System.out.println("Should print A at index 0: " 
                + test1.get(0));
        System.out.println("Should print Z at index 2: " 
                + test1.get(2));
        System.out.println("Should print [A, B, Z, D]: " + test1);

        test1.set(0, "Z");
        System.out.println("\nShould print size of 4: " 
                + test1.size());
        System.out.println("Should print the list array is empty - false: " 
                + test1.isEmpty());
        System.out.println("Should print Z at index 0: " 
                + test1.get(0));
        System.out.println("Should print Z at index 2: " 
                + test1.get(2));
        System.out.println("Should print [Z, B, Z, D]: " + test1);

        System.out.println("\n***Testing Remove Methods***\n");
        
        System.out.println("Remove index 3, element D: " 
                + test3.remove(3));
        System.out.println("Should print size of 6: " 
                + test3.size());
        System.out.println("Should print the list array is empty - false: " 
                + test3.isEmpty());
        System.out.println("Should print A at index 0: " 
                + test3.get(0));
        System.out.println("Should print A at index 3: " 
                + test3.get(3));
        System.out.println("Should print [A, B, C, A, B, C]: " + test3);

        System.out.println("\nRemove index 0, element A: " 
                + test3.remove(0));
        System.out.println("Should print size of 5: " 
                + test3.size());
        System.out.println("Should print the list array is empty - false: " 
                + test3.isEmpty());
        System.out.println("Should print B at index 0: " 
                + test3.get(0));
        System.out.println("Should print B at index 3: " 
                + test3.get(3));
        System.out.println("Should print [B, C, A, B, C]: " + test3);

        System.out.println("\nRemove index 10, should print error message: ");
        test3.remove(10);
        System.out.println("Should print size of 5: " 
                + test3.size());
        System.out.println("Should print the list array is empty - false: " 
                + test3.isEmpty());
        System.out.println("Should print B at index 0: " 
                + test3.get(0));
        System.out.println("Should print B at index 3: " 
                + test3.get(3));
        System.out.println("Should print [B, C, A, B, C]: " + test3);

        System.out.println("\nRemove index of size - 1 (last element) C: " 
                + test3.remove(test3.size() - 1));
        System.out.println("Should print size of 4: " 
                + test3.size());
        System.out.println("Should print the list array is empty - false: " 
                + test3.isEmpty());
        System.out.println("Should print B at index 0: " 
                + test3.get(0));
        System.out.println("Should print B at index size - 1: " 
                + test3.get(test3.size() - 1));
        System.out.println("Should print [B, C, A, B]: " + test3);

        System.out.println("\nCan remove B (first instance): " 
                + test3.remove("B"));
        System.out.println("Should print size of 3: " 
                + test3.size());
        System.out.println("Should print the list array is empty - false: " 
                + test3.isEmpty());
        System.out.println("Should print C at index 0: " 
                + test3.get(0));
        System.out.println("Should print B at index size - 1: " 
                + test3.get(test3.size() - 1));
        System.out.println("Should print [C, A, B]: " + test3);

        System.out.println("\nCan remove B (only instance): " 
                + test3.remove("B"));
        System.out.println("Should print size of 2: " 
                + test3.size());
        System.out.println("Should print the list array is empty - false: " 
                + test3.isEmpty());
        System.out.println("Should print C at index 0: " 
                + test3.get(0));
        System.out.println("Should print A at index size - 1: " 
                + test3.get(test3.size() - 1));
        System.out.println("Should print [C, A]: " + test3);

        System.out.println("\nShould print error message: ");
        System.out.println("Cannot remove Z (not stored): " 
                + test3.remove("Z"));
        System.out.println("Should print size of 2: " 
                + test3.size());
        System.out.println("Should print the list array is empty - false: " 
                + test3.isEmpty());
        System.out.println("Should print C at index 0: " 
                + test3.get(0));
        System.out.println("Should print A at index size - 1: " 
                + test3.get(test3.size() - 1));
        System.out.println("Should print [C, A]: " + test3);

        test3.remove("C");
        System.out.println("\nRemove C:");
        System.out.println("Should print size of 1: " 
                + test3.size());
        System.out.println("Should print the list array is empty - false: " 
                    + test3.isEmpty());
        System.out.println("Should print A at index 1: " 
                        + test3.get(0));
        System.out.println("Should print A at index size - 1: " 
                        + test3.get(test3.size() - 1));
        System.out.println("Should print [A]: " + test3);

        test3.remove("A");
        System.out.println("\nRemove A:");
        System.out.println("Should print size of 0: " 
                + test3.size());
        System.out.println("Should print the list array is empty - true: " 
                + test3.isEmpty());
        System.out.println("Should print []: " + test3);
        
        System.out.println("\n***Testing Size Method***\n");
        System.out.println("Should print size of 0: " 
                + test3.size());
        System.out.println("Should print size of 20: "
                + test2.size());
        System.out.println("Should print size of 4: " 
                + test1.size());

        System.out.println("\n***Testing IsEmpty Method***\n");
        System.out.println("Should print true: " 
                + test3.isEmpty());
        System.out.println("Should print false: " 
                + test2.isEmpty());
        System.out.println("Should print false: " 
                + test1.isEmpty());

        System.out.println("\n***Testing Contains Method***\n");
        System.out.println("Contains Z, should print true: " 
                + test1.contains("Z"));
        System.out.println("Contains D, should print true: " 
                + test1.contains("D"));
        System.out.println("Contains A, should print false: " 
                + test1.contains("A"));
        
        System.out.println("\n***Testing IndexOf Method***\n");
        System.out.println("Index of Z, should print 0: " 
                + test1.indexOf("Z"));
        System.out.println("Index of D, should print 3: " 
                + test1.indexOf("D"));
        System.out.println("Index of A, should print -1: " 
                + test1.indexOf("A"));
        
        System.out.println("\n***Testing Get Method***\n");
        System.out.println("Element at 0, should print Z: " 
                + test1.get(0));
        System.out.println("Element at 3, should print D: " 
                + test1.get(3));
        System.out.println("Element at 6, should print error message: ");
        test1.get(6);

    }
}