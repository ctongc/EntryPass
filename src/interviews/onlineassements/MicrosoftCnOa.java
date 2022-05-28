package interviews.onlineassements;

import java.util.HashSet;
import java.util.Set;

public class MicrosoftCnOa {

    /**
     * You are given an integer array nums, you need to divide it into n pairs such that:
     * 1. Each element belongs to exactly one pair.
     * 2. The elements present in a pair are equal.
     * Return true if nums can be divided into n pairs, otherwise return false.
     */
    public boolean canDivideArray(int[] nums) {
        if (nums == null || nums.length % 2 != 0) {
            return false;
        }

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                set.remove(num);
            }
        }

        return set.isEmpty();
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
        MicrosoftCnOa ins = new MicrosoftCnOa();
        System.out.println(ins.maxConcatenatedLength(new String[]{"abc", "yyy", "def", "csv"}));
    }
}
