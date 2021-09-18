package exams;

import java.util.*;

public class FinalExam {

    /**
     * Recruiting Event Schedules
     * Our company is organizing a series of university recruiting events. Each day,
     * we host an event at one university, but sometimes we want to take a break for
     * one day before moving onto the next university.
     * Given a sequence of universities, print all possible schedules of the recruiting events.
     * Input: a String of universities. Each university is represented as a single capital letter.
     * Output: all possible schedules. An underscore '_' means we take a break
     * e.g.
     * Input: str = "ABC"
     * Output: ABC, AB_C, A_BC, A_B_C
     *
     * Time = O(2^n) // n = s.length() - 2
     * Space = O(n)
     */
    public void printUniversitySchedule(final String s) {
        if (s == null || s.length() <= 1) {
            System.out.println(s);
            return;
        }
        final StringBuilder prefix = new StringBuilder(); // store each formed String
        printStringWithSpace(s, 0, prefix);
    }

    private void printStringWithSpace(final String s, final int index, final StringBuilder prefix) {
        // 一共 array.length() - 1 层, 因为末尾不能再加空格了
        if (index == s.length() - 1) {
            prefix.append(s.charAt(index));
            System.out.println(prefix);
            prefix.deleteCharAt(prefix.length() - 1);
            return; // MUST HAVE, 触底反弹
        }

        /* 每一层每个node两个叉，左叉加字母和下划线，右杈只加字母不加下划线 */

        prefix.append(s.charAt(index)); // 加字母

        // 加下划线走左杈
        prefix.append("_");
        printStringWithSpace(s, index + 1, prefix);
        prefix.deleteCharAt(prefix.length() - 1); // 删下划线

        // 不加下划线走右叉
        printStringWithSpace(s, index + 1, prefix);

        prefix.deleteCharAt(prefix.length() - 1); // 删字母
        /* 为啥删了2次? 想想冯诺依曼体系
         * 要装作这层没发生过! A_的所有substring走完了, 才会开始走A的 */
    }

    /**
     * Cousins in a Binary Tree
     * In a binary tree, two nodes are cousins of each other if they are at the same
     * level and have different parents.
     * Given a Binary Tree and two nodes, determine whether the two nodes are cousins
     * of each other or not.
     *
     * Assumption: Both TreeNodes are not null
     *
     * Time = O(n) // 遍历每个节点
     * Space = O(n) // Queue
     */
    public boolean isCousin(final TreeNode root, final TreeNode a, final TreeNode b) {
        if (root == null) {
            return false;
        }

        final Queue<TreeNode> queue = new ArrayDeque<>();
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
                // if a and b are siblings not cousins
                if (cur.left == a && cur.right == b
                        || cur.left == b && cur.right == a ) {
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
     * Time = O(n)
     * Space = O(height)
     */
    public boolean isCousin2(final TreeNode root, final TreeNode a, final TreeNode b) {
        if (root == null) {
            return false;
        }
        boolean[] result = new boolean[1];
        isCousinDFS(root, a, b, 0, result);
        return result[0];
    }

    /**
     * 1. ask left and right, did you find a or b? - semantic of the return value
     * 2. if found both && their level + 1 != current level, they are cousin
     * 3. return the level to the parents
     *
     * Time = O(n)
     * Space = O(height)
     */
    private int isCousinDFS(final TreeNode root, final TreeNode a, final TreeNode b, int level, boolean[] result) {
        if (root == null) {
            return 0;
        }
        if (root == a || root == b) {
            return level;
        }

        int left = isCousinDFS(root.left, a, b, level + 1, result);
        int right = isCousinDFS(root.right, a, b, level + 1, result);

        if (left == right && left > level + 1) {
            result[0] = true;
        }

        return left == 0 ? right : left;
    }

    /**
     * Packing Up the Swags
     * Our company is going to distribute swags at the recruiting event. We will put
     * the swags into square-shaped boxes. All boxes have to be completely filled so
     * that the swags wouldn't break during transportation.
     * e.g. a box can contain 1 swag, 4 swags, 9 swags, etc. (can be sufficiently large)
     * However, if there are 10 swags, we have to put them into multiple boxes.
     * e.g. split them into four boxes: 4, 4, 1, 1; or just two boxes: 9, 1
     * Given the number of swags, what is the minimum number of boxes to pack them up
     *
     * Time = O(num^maxRoot) // 实际上要比这个小
     * Space = O(maxRoot)
     */
    public int splitNum(final int num) {
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

    private void getLeastSplit(final int valueLeft, final int level,
                               final int[] roots, final int[] cuts, final int[] min) {

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
     * Time = O(n^1.5) // O(n)*O(sqrt(n))
     * Space =
     */
    int splitNum2(final int num) {
        if (num <= 1) {
            return num;
        }

        // M[i]的物理意义是第i个数可以被分成的最少个数的平方数之和
        int[] M = new int[num + 1];
        M[0] = 0;
        M[1] = 1;

        for (int i = 2; i <= num; i++) {
            M[i] = i;
            // 左大段 + 右小段(不可分割)
            for (int j = 1; j * j <= i; j++) {
                M[i] = Math.min(M[i], M[i - j * j]);
            }
        }

        return M[num];
    }

    /**
     * Q4 Infinite Loop Around the Dinner Table
     * After the event, our company will take the students out for dinner. The restaurant
     * has a large round table that can fit any whole party. We want to know if we can
     * arrange the students so that the names of all students around the table form an
     * "infinite loop". For each pair of neighboring students s1 and s2, the last letter
     * of s1's name must be identical to the first letter of s2's name.
     * e.g. "Alice" and "Eric" can sit together, but "Alice" and "Bob" cannot.
     * Given an array of names, determine if it is possible to arrange the students at
     * the round table in this way.
     *
     * Assumption: each String in names is not null and length > 0
     *
     * Time = O(n!)
     * Space = O(n)
     */
    public boolean canChain(final String[] names) {
        if (names == null || names.length == 0) {
            return false;
        }
        // start from the second string
        return chainCheck(names, 1);
    }

    private boolean chainCheck(final String[] names, final int index) {
        if (index == names.length) {
            // check if the last String can chain the first String
            return isValid(names[index - 1], names[0]);
        }

        /* all the elements should be used, the relative order can be changed
         * all permutation -> swap-swap */
        for (int i = index; i < names.length; i++) {
            // swap only when s1's last char == s2's first char
            if (isValid(names[index - 1], names[i])) {
                swap(names, index, i);
                if (chainCheck(names, index + 1)) {
                    return true;
                }
                swap(names, index, i);
            }
        }

        return false;
    }

    private boolean isValid(final String one, final String two) {
        return one.charAt(one.length() - 1) == two.charAt(0);
    }

    private void swap(final String[] array, final int a, final int b) {
        String temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void main(String[] args) {
        final FinalExam solution = new FinalExam();
        solution.printUniversitySchedule("ABC");
        //      6
        //    /   \
        //   3     5
        //  / \   / \
        // 7   8 1   2
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
        System.out.println(solution.isCousin(t1, t4, t6));
        System.out.println(solution.isCousin2(t1, t4, t6));

        System.out.println(solution.splitNum(13));
        System.out.println(solution.splitNum2(13));

        System.out.println(solution.canChain(new String[]{"ab", "ba", "acca"}));
        System.out.println(solution.canChain(new String[]{"aba"}));
    }
}
