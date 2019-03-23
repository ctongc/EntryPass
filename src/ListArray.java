/**
 * ListArray.java - Lab 4
 * @author
 * @author
 * CIS 36B
 */

public class ListArray {
    private String[] array;
    private int numElements;

    /**Constructors*/

    /**
     * Default constructor for ListArray
     * Creates an empty array of length 10
     * Sets numElements to 0
     */
    public ListArray() {
        array = new String[10];
        numElements = 0;
    }

    /**
     * Constructor for ListArray
     * Creates an empty array of length size
     * Sets numElements to 0
     * @param size the initial size of the
     * ListArray
     */
    public ListArray(int size) {
        array = new String[size];
        numElements = 0;
    }

    /**
     * Copy constructor for ListArray
     * Creates a new list array of the
     * same size as the ListArray passed
     * in as a parameter, and copies the 
     * parameter's data into the new 
     * list array using a for loop
     * Also sets the numElements to be
     * the same as the parameter's 
     * numElements
     * @param la the ListArray to copy
     */

    public ListArray(ListArray la) {
        array = new String[la.size()];
        for (int i = 0; i < la.size(); i++) {
            array[i] = la.get(i);
        }
        numElements = la.numElements;
    }

    /**Accessor Methods*/

    /**
     * Returns whether the list array is
     * currently empty
     * @return whether the list array is
     * empty
     */
    public boolean isEmpty() {
        return numElements == 0;
    }

    /**
     * Returns the current number of
     * elements stored in the list 
     * array
     * @return the number of elements
     */
    public int size() {
        return numElements;
    }

    /**
     * Returns the element at the specified
     * index. Prints an error message and
     * returns an empty String if index is
     * larger than or equal to numElements
     * @param index the index of the element
     * to access
     * @return the element at index
     */
    public String get(int index) {
        if (index >= numElements) {
            System.out.println("ArrayIndexOutOfBoundsException: " + index + " is larger than or equal to numElements.");
        }
        return array[index];
    }

    /**
     * Uses the linearSearch algorithm to 
     * locate an element in the list array
     * @param str the element to locate
     * @return whether or not the element
     * is in the list array
     */
    public boolean contains(String str) {
        for (String s : array) {
            if (str.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Uses the linearSearch algorithm to 
     * locate an element in the list array
     * @param str the element to locate
     * @return the location of the element
     * or -1 if the element is not in the
     * list array
     */
    public int indexOf(String str) {
        for (int i = 0; i < numElements; i++) {
            if (str.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Determines whether the list array
     * is currently full
     * @return whether the list array
     * is at maximum capacity
     */
    private boolean atCapacity() {
        return numElements == array.length;
    }

    /**Mutator Methods*/

    /**
     * Resizes the list array by making a new
     * array that has a length (capacity)
     * 10 larger than the current array's
     * length (capacity)
     */
    private void reSize() {
        String[] temp = new String[array.length + 10];
        for (int i = 0; i < array.length; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }

    /**
     * Inserts a new element to the end
     * of the list array.
     * Resizes the list array if it is
     * at capacity before inserting
     * @param str the element to insert
     */
    public void add(String str) {
        if (atCapacity()) {
            reSize();
        }
        array[numElements++] = str;
    }

    /**
     * Inserts a new element at the specified
     * index in the list array.
     * Resizes the list array if it is
     * at capacity before inserting
     * @param index the index at which to insert
     * @param str the element to insert
     */
    public void add(int index, String str) {
        if (index >= numElements && index != 0) {
            System.out.println("Cannot add at index " + index);
            return;
        }
        if (atCapacity()) {
            reSize();
        }
        for (int i = numElements; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = str;
        numElements++;
    }

    /**
     * Assigns a new value to the list array
     * at a specified index
     * @param index the index at which to update
     * @param str the new element
     */
    public void set(int index, String str) {
        if (index >= numElements) {
            System.out.println("ArrayIndexOutOfBoundsException: " + index + " is larger than or equal to numElements.");
            return;
        }
        array[index] = str;
    }

    /**
     * Removes an element at a specified index in
     * the list array
     * @param index the index at which to remove
     * @return the element that was removed
     */
    public String remove(int index) {
        if (index >= numElements) {
            System.out.println("ArrayIndexOutOfBoundsException: " + index + " is larger than or equal to numElements.");
            return null;
        }
        String temp = array[index];
        for (int i = index; i < numElements; i++) {
            array[i] = array[i + 1];
        }
        numElements--;
        return temp;
    }

    /**
     * Removes the first instance of the specified
     * element in the list array
     * @param str the element to remove
     * @return whether the element was successfully
     * removed
     */
    public boolean remove(String str) {
        int index = indexOf(str);
        if (index != - 1) {
            remove(index);
            return true;
        }
        System.out.println("Cannot remove " + str + " (not stored).");
        return false;
    }
    /**Additional Methods*/

    /**
     * Creates a String of all elements,
     * with [] around the elements,
     * each element separated from the next
     * with a comma
     */
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < numElements; i++) {
            sb.append(array[i]).append(", ");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }
}