package interview;

import java.util.HashSet;
import java.util.Set;

class ListNode {
    int value;
    ListNode next;

    public ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }
}

public class MicrosoftCn {

    public ListNode reverseLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = null;
        while (head != null) { // if using while (head.next != null) then it won't reverse the last element
            ListNode nextNode = head.next; // always store the new head first
            head.next = prev; // reserve happens here
            prev = head; // move prev by one
            head = nextNode; // move current by one
        }
        // now head == null
        return prev;
    }

    public ListNode reverseLinkedListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseLinkedListRecursive(head.next); // newHead is the last node of current linked list
        head.next.next = head; // head.next is the next node
        head.next = null; // reset
        return newHead;
    }

    /**
     * Return the maximum possible value obtained by deleting one '5' digit from the decimal representation
     * It's guaranteed that num has at least one '5'
     *
     * Time = O(n)
     * Space = O(n)
     */
    public int removeFive(int num) {
        int sign = num >= 0 ? 1 : -1;
        String numString = Integer.toString(num); // this will keep the sign
        boolean foundFivePos = false;
        StringBuilder sb = new StringBuilder();
        if (sign == -1) {
            for (int i = 0; i < numString.length(); i++) {
                if (numString.charAt(i) == '5' && !foundFivePos) {
                    foundFivePos = true;
                    continue;
                }
                sb.append(numString.charAt(i));
            }
        } else {
            for (int i = 0; i < numString.length(); i++) {
                if (numString.charAt(i) == '5' && !foundFivePos) {
                    foundFivePos = true;
                    continue;
                }
                sb.append(numString.charAt(i));
            }
        }
        return Integer.parseInt(sb.toString());
    }

    /**
     * Min Deletions To Obtain String in Right Format
     * Given a string with only characters X and Y. Find the minimum number of characters
     * to remove from the string such that there is no interleaving of character X and Y
     * and all the Xs appear before any Y.
     *
     * e.g. Input: BAAABAB
     * Output: 2
     * We can obtain AAABB by:
     * Delete first B -> AAABAB
     * Delete last occurrence of A -> AAABB
     *
     * e.g. Input:BBABAA
     * Output: 3
     * We can remove all occurrence of A or B
     */
    public int minStep(String str) {
        int numB = 0;
        int minDel = 0;

        //      BAAABAB
        //            i
        // numB 1111223
        // minD 0111122
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'A') {
                minDel = Math.min(numB, minDel + 1);
            } else {
                numB++;
            }
        }
        return minDel;
    }

    /**
     * This is a demo task.
     *
     * Write a function:
     *
     * class Solution { public int solution(int[] A); }
     *
     * that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
     *
     * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
     *
     * Given A = [1, 2, 3], the function should return 4.
     *
     * Given A = [−1, −3], the function should return 1.
     *
     * Write an efficient algorithm for the following assumptions:
     *
     * N is an integer within the range [1..100,000];
     * each element of array A is an integer within the range [−1,000,000..1,000,000].
     * */
    public int solution(int[] A) {
        // sanity check
        if (A == null || A.length == 0) {
            return 1;
        }
        boolean negCheck = false;
        boolean[] lookup = new boolean[A.length + 1];
        for (int i : A) {
            if (i > 0) {
                negCheck = true;
            }
            if (i > 0 && i <= A.length) {
                lookup[i] = true;
            }
        }

        if (!negCheck) {
            return 1;
        }

        for (int i = 1; i < lookup.length; i++) {
            if (!lookup[i]) {
                return i;
            }
        }

        return A.length + 1;
    }

   /**
    * Max Concatenated String Length with unique Characters
    */
    public int maxConcatenatedLength(String[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int[] result = new int[]{0};
        allPerm(A, 0, "", result);
        return result[0];
    }

    private void allPerm(String[] A, int index, String prefix, int[] result) {
        if (index == A.length) {
            return;
        }

        String cur = prefix + A[index];
        if (!containsDup(cur)) {
            result[0] = Math.max(cur.length(), result[0]);
            allPerm(A, index + 1, cur, result);
        }

        allPerm(A, index + 1, prefix, result);
    }

    private boolean containsDup(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if(!set.add(c)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        MicrosoftCn ins = new MicrosoftCn();
        System.out.println(ins.removeFive(15958));
        System.out.println(ins.removeFive(-9995));
        System.out.println(ins.removeFive(-5859));
        System.out.println(ins.removeFive(-5000));

        System.out.println(ins.minStep("AAABBB"));
        System.out.println(ins.minStep("BBAABBBB"));
        System.out.println(ins.minStep("BAAABAB"));

        ins.solution(new int[]{1,3,6,4,1,2});
        ins.maxConcatenatedLength(new String[]{"abc", "yyy", "def", "csv"});
    }
}
