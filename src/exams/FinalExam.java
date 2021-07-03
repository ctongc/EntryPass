package exams;

import java.util.*;

public class FinalExam {

    /**
     * Given a string, we can insert at most one empty space between each pair of adjacent letters.
     * Print all permutations of strings after insertions of empty spaces.
     * Input: str = "ABC"
     * Output:
     * ABC
     * AB_C
     * A_BC
     * A_B_C
     *
     * Time = O(2^n) // n = s.length() - 2
     * Space = O(n)
     */
    void printStringWithSpace(String s) {
        if (s == null || s.length() <= 1) {
            System.out.println(s);
            return;
        }
        StringBuilder prefix = new StringBuilder(); // store each formed string
        addSpaceToString(s, 0, prefix);
    }

    private void addSpaceToString(String s, int index, StringBuilder prefix) {
        /* 一共 array.length() - 1 层, 因为末尾不能再加空格了 */

        // base case
        if (index == s.length() - 1) {
            // 手动处理最后一层可以减少最后一层一半的叉
            prefix.append(s.charAt(index));
            System.out.println(prefix);
            prefix.deleteCharAt(prefix.length() - 1);
            return; // 如果不加会出界
        }

        /* 每一层每个node两个叉，左叉加空格，右杈不加 */

        prefix.append(s.charAt(index)); // 加字母

        // 加空格走左杈
        prefix.append(" ");
        addSpaceToString(s, index + 1, prefix);
        // 删空格走右叉
        prefix.deleteCharAt(prefix.length() - 1);
        addSpaceToString(s, index + 1, prefix);

        // 删掉当前字母，回到上一层
        prefix.deleteCharAt(prefix.length() - 1);

        /* 想想冯诺依曼体系 现在知道为啥要删2次了吗
         * 要装作这层没发生过！A_的substring走完了 才会开始走A的 */
    }

    /**
     * Given the binary Tree and the two nodes say ‘a’ and ‘b’
     * determine whether the two nodes are cousins of each other or not.
     * Two nodes are cousins of each other if they are at same level and have different parents.
     *
     * Assumption: Both TreeNodeC a and TreeNodeC b are not null
     *
     * Time = O(n) // 遍历每个节点
     * Space = O(n) // queue
     */
    boolean isCousin(TreeNode root, TreeNode a, TreeNode b) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            int count = 0; // when count == 2, find both a and b
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
                // a and b are siblings not cousins
                if (cur.left == a && cur.right == b
                        || cur.left == a && cur.right == b ) {
                    return false;
                }
                if (cur == a || cur == b) {
                    count++;
                }
                if (count == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * How to cut/split the number into a minimum number of items
     * such that each item is equal to an integer's square value.
     *
     * Time = O(num^maxRoot) // 实际上要比这个小
     * Space = O(maxRoot)
     */
    int splitNum(int num) {
        if (num <= 1) {
            return num;
        }
        int maxRoot = (int) Math.sqrt(num);
        int[] roots = new int[maxRoot];
        for (int i = 0; i < roots.length; i++) {
            roots[i] = maxRoot*maxRoot;
            maxRoot--;
        }
        int[] min = {num};
        int[] cuts = new int[1]; // cuts is the number of cuts
        getLeastSplit(num, 0, roots, cuts, min);
        return min[0];
    }

    private void getLeastSplit(int valueLeft, int level, int[] roots, int[] cuts, int[] min) {

        if (level == roots.length - 1) {
            // 手动处理最后一层
            cuts[0] += valueLeft;
            if (cuts[0] < min[0]) {
                min[0] = cuts[0];
            }
            cuts[0] -= valueLeft;
            return;
        }

        for (int i = 0; i <= valueLeft / roots[level]; i++) {
            cuts[0] += i;
            getLeastSplit(valueLeft - roots[level] * i, level + 1, roots, cuts, min);
            cuts[0] -= i;
        }
    }

    /**
     * How to cut/split the number into a minimum number of items
     * such that each item is equal to an integer's square value.
     *
     * Time = O(n^1.5) // O(n)*O(sqrt(n))
     * Space =
     */
    int splitNum2(int num) {
        if (num <= 1) {
            return num;
        }
        // M[i]的物理意义是第i个数可以被分成的最少个数的平方数之和
        int[] M = new int[num + 1];
        M[0] = 0;
        M[1] = 1; // base case
        for (int i = 2; i <= num; i++) {
            M[i] = num;
            // 左大段 + 右小段
            for (int j = 1; j * j <= i; j++) {
                M[i] = Math.min(M[i], M[i - j*j] + 1);
            }
        }
        return M[num];
    }

    /**
     * Given an array of strings, find if all the strings can be chained to form a circle.
     * Two string s1 and s2 can be chained, iff the last letter of s1is identical to the first letter of s2.
     *
     * Assumption: each string in arr is not null and length > 0
     * Time = O(n!)
     * Space = O(n)
     */
    public boolean containCycle(String[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        if (array.length == 1) {
            String s = array[0];
            return s.charAt(0) == s.charAt(s.length() - 1);
        }
        // start from the second string
        return containCycleHelper(array, 1);
    }

    private boolean containCycleHelper(String[] input, int index){
        if (index == input.length) {
            // check if the last string can chain the first string
            return isValid(input[index - 1], input[0]);
        }

        for (int i = index; i < input.length; i++) {
            if (isValid(input[index - 1], input[i])) {
                swap(input, index, i);
                if (containCycleHelper(input, index + 1)) {
                    return true;
                }
                swap(input, index, i);
            }
        }
        return false;
    }

    private boolean isValid(String one, String two) {
        return one.charAt(one.length() - 1) == two.charAt(0);
    }

    private static void swap(String[] array, int a, int b) {
        String temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void main(String[] args) {
        FinalExam solution = new FinalExam();
        solution.printStringWithSpace("ABC");
        //     6
        //   /   \
        //  3     5
        // / \   / \
        //7   8 1   2
        TreeNode t1 = new TreeNode(6);
        TreeNode t2 = new TreeNode(3);
        TreeNode t3 = new TreeNode(5);
        TreeNode t4 = new TreeNode(7);
        TreeNode t5 = new TreeNode(8);
        TreeNode t6 = new TreeNode(1);
        TreeNode t7 = new TreeNode(2);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        System.out.println(solution.isCousin(t1,t4,t6));

        System.out.println(solution.splitNum(13));
        System.out.println(solution.splitNum2(13));

        System.out.println(solution.containCycle(new String[]{"ab", "ba", "acca"}));
    }
}
