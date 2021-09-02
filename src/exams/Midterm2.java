package exams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Midterm2 {
    /**
     * Find all valid ways of putting N Queens on an N*N chessboard
     * so that no two Queens can attack each other
     *
     * Time = O(n! * n)
     * Space = O(n)
     */
    public List<List<Integer>> nQueens(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        putQueens(result, new ArrayList<>(), n, 0);
        return result;
    }

    private void putQueens(List<List<Integer>> result, List<Integer> cur, int n, int level) {
        if (level == n) {
            result.add(new ArrayList<>(cur));
            return;
        }

        for (int i = 0; i < n; i++) {
            if(isValid(cur, i)) {
                cur.add(i);
                putQueens(result, cur, n, level + 1);
                cur.remove(cur.size() - 1);
            }
        }
    }

    private boolean isValid(List<Integer> cur, int curCol) {
        /* Row: no need since we put queue per row
         * Column: curCol != preCol
         * Diagonal: abs(行差) == abs(列差) */
        for (int row = 0; row < cur.size(); row++) {
            int col = cur.get(row);
            if (curCol == col
                    || Math.abs(curCol - col) == Math.abs(cur.size() - row)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Given a binary tree in which each node element contains a number
     * Find the maximum possible sum from any leaf node to another leaf node
     * The maximum sum path may or may not go through root
     *
     * Time = O(n)
     * Space = O(height)
     */
    public int maxSumLeafToLeaf(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE; // common mistake - return 0
        }
        int[] result = new int[]{Integer.MIN_VALUE}; // common mistake - initiate with 0
        maxSum(root, result);
        return result[0];
    }

    private int maxSum(TreeNode root, int[] globalMax) {
        if (root == null) {
            return 0;
        }
        int leftSum = maxSum(root.left, globalMax);
        int rightSum = maxSum(root.right, globalMax);
        if (root.left != null && root.right != null) { // corner case -> only has one leaf node
            globalMax[0] = Math.max(globalMax[0], leftSum + rightSum + root.key);
            return Math.max(leftSum, rightSum) + root.key;
        }
        return root.left == null ? root.key + rightSum : root.key + leftSum;
    }

    /**
     * Given a String, a partitioning of the String is a palindrome partitioning
     * if every partition is a palindrome. Determine the fewest cuts needed for
     * palindrome partitioning of a given String.
     */
    //TODO

    /**
     * Given an integer n, print all possible if blocks for it
     *
     * Time = O(2^(2n) * n ^ 2)
     * Space = O(2n)
     */
    public void printBlocks(int n) {
        StringBuilder sb = new StringBuilder();
        getComb(sb, n, n);
    }

    private void getComb(StringBuilder sb, int leftRemain, int rightRemain) {
        if (rightRemain == 0) {
            printIfBlocks(sb.toString());
            return;
        }
        if (leftRemain > 0) {
            sb.append('{');
            getComb(sb, leftRemain - 1, rightRemain);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (rightRemain > leftRemain) {
            sb.append('}');
            getComb(sb, leftRemain, rightRemain - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private void printIfBlocks(String bracketsCombo) {
        char[] chars = bracketsCombo.toCharArray();
        int dentCount = 0;
        for (char c : chars) {
            if (c == '{') {
                printDent(dentCount);
                System.out.println("if {");
                dentCount++;
            }
            if (c == '}') {
                dentCount--;
                printDent(dentCount);
                System.out.println("}");
            }
        }
    }

    private void printDent(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("  ");
        }
    }

    public static void main(String[] args) {
        Midterm2 ins = new Midterm2();
        ins.printBlocks(3);
    }
}
