package algorithms;

import java.util.*;

public class DepthFirstSearch {

    /**
     * All Subsets I
     * Given a set of characters represented by a String, return a list containing all
     * subsets of the characters.
     * Assumptions: there is no duplicate characters in the original set
     * e.g. set = "abc", output = [“”, “a”, “ab”, “abc”, “ac”, “b”, “bc”, “c”]
     *
     * Time = O(2^n) // branch^depth - approximate the total nodes in last level
     * Space = O(n) // each letter has a level
     */
    public List<String> findAllSubSets(String set) {
        List<String> result = new ArrayList<>();
        if (set == null) {
            return result;
        }
        StringBuilder prefix = new StringBuilder();
        findSubSets(set, 0, prefix, result);
        return result;
    }

    private void findSubSets(String s, int index, StringBuilder prefix, List<String> result) {
        if (index == s.length()) {
            result.add(prefix.toString());
            return; // MUST HAVE 触底反弹
        }

        /* 多少个字母多少层，每一层每个node两个杈，考虑加不加当前层字母，左杈加，右杈不加 */

        // case 1: add current character to the prefix
        prefix.append(s.charAt(index));
        findSubSets(s, index + 1, prefix, result); // 吃
        /* 在try同层不同的node时，需要回到母节点层，再下到下一层的右边node.
         * e.g. 从add 'c' 到add '', 需要回到"For b"层 (=把c从set里删掉)
         * 再下到"For c"层, add '' */
        prefix.deleteCharAt(prefix.length() - 1); // 吐 remove the char just added, reset states

        // case 2: not add current character to the prefix
        findSubSets(s, index + 1, prefix, result);
    }

    /**
     * All Valid Permutations Of Parentheses I
     * Given n pairs of parentheses “()”, return a list with all the valid permutations.
     * e.g. n = 3, all valid permutations are ["((()))", "(()())", "(())()", "()(())", "()()()"]
     *
     * Time = O(2 ^ (2n)) // branch 2, depth 2n (2n levels)
     * Space = O(2 * n) // 2n levels, but need another n to form the StringBuilder
     */
    public List<String> findValidPermutationsOfParentheses(int n) {
        if (n < 1) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        StringBuilder prefix = new StringBuilder();
        generateParenthesis(n, n, prefix, result);
        return result;
    }

    /**
     * n stores total number of "pair of ()" need to add
     * So total levels == 2 * n  (2n positions)
     * leftRemain stores the number of left parenthesis '(' need to be added
     * rightRemain stores the number of right parenthesis ')' need to be added
     * prefix stores the solution so far
     */
    private void generateParenthesis(int leftRemain, int rightRemain, StringBuilder prefix, List<String> result) {
        if (leftRemain == 0 && rightRemain == 0) {
            result.add(prefix.toString());
            // formatAndPrint(prefix.toString().toCharArray()); this is for print indent
            return; // MUST HAVE 触底反弹
        }

        /* n个括号, 2n个position, 所以2n层.
         * 每层每个node两个杈，考虑当前位置加什么括号, 左杈加左括号, 右杈加右括号. */

        // case 1: add '(' on this level
        if (leftRemain > 0) {
            prefix.append('(');
            generateParenthesis(leftRemain - 1, rightRemain, prefix, result);
            prefix.deleteCharAt(prefix.length() - 1); // reset
        }

        // case 2: add ')' on this level
        /* Restriction: whenever we want to insert a new ')', we need to make sure the
         * number of '(' added so far is larger than the number of ')' added so far. */
        if (rightRemain > leftRemain) {
            prefix.append(')');
            generateParenthesis(leftRemain, rightRemain - 1, prefix, result);
            prefix.deleteCharAt(prefix.length() - 1); // reset
        }
    }

    /**
     * Given n if blocks, print them with right indent.
     * Used by findPermutationsOfParentheses().
     *
     * Time = O(n^2 * 2^(2n))
     * Space = O(n^2) // 2n levels, but need another n to form the StringBuilder
     */
    private void formatAndPrint(char[] input) {
        int count = 0;
        for (char c : input) {
            if (c == '{') {
                printIndent(count);
                System.out.println("if {");
                count++;
            } else {
                count--;
                printIndent(count);
                System.out.println("}");
            }
        }
        System.out.println();
    }

    private void printIndent(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("  ");
        }
    }

    /**
     * Given a number of different denominations of coins
     * (e.g., 1 cent, 5 cents, 10 cents, 25 cents),
     * get all the possible ways to pay a target number of cents.
     * Assumption: target >= 0, coins not null and no coin value < 1
     *
     * If target = 99 cents and coins are 25, 10, 5, 1
     * Time = O(99 ^ 4) // 4 + (10+8+5+3) + ...  but think it Asymptotically
     *      ~ (target/smallestCoin) ^ numberOfValuesOfCoins
     * Space = O(n)
     */
     public List<List<Integer>> combinationsOfCoins(int target, int[] coins) {
         List<List<Integer>> result = new ArrayList<>(); // store the result
         if (target == 0) {
             return result;
         }
         List<Integer> cur = new ArrayList<>(); // store each formed combination
         getComboOfCoins(target, coins, 0, cur, result);
         return result;
    }

    private void getComboOfCoins(int moneyLeft, int[] coins, int level,
                                 List<Integer> cur, List<List<Integer>> result) {
        /* level 0 : how many biggest cent of coins will be used
         * ...
         * level coins.length - 1 : how many smallest cent
         *
         * termination condition:
         * NOTICE this could also be done at level == coins.length, but we end at the
         * previous level to reduce the number of branches in DFS */
        if (level == coins.length - 1) {
            /* since it's still in the process of DFS
             * we still need to follow the rule in this condition block (吃了吐) */
            if (moneyLeft % coins[coins.length - 1] == 0) { // smallest coin might not be 1
                cur.add(moneyLeft / coins[coins.length - 1]);
                // need a new copy of list since the original list will still be edited during DFS
                result.add(new ArrayList<>(cur));
                cur.remove(cur.size() - 1);
            }
            return;
        }
        // i is number of coins in one level
        for (int i = 0; i <= moneyLeft/coins[level]; i++) { // notice <=
            cur.add(i);
            getComboOfCoins(moneyLeft - i * coins[level], coins, level + 1, cur, result);
            cur.remove(cur.size() - 1);
        }
    }

    /**
     * 99 cents problem
     */
    public void getCombinationOf99Cents(int moneyLeft, int[] coins, int level, int[] curSol) {
         if (level == 3) {
             curSol[level] = moneyLeft;
             System.out.println(Arrays.toString(curSol));
             // curSol[level] = 0;
             return;
         }
         
         // money value on this level == coin[index]
         for (int i = 0; i <= moneyLeft / coins[level]; i++) {   // notice <=
             curSol[level] = i;
             getCombinationOf99Cents(moneyLeft - i * coins[level], coins, level + 1, curSol);
             // curSol[level] = 0; // not necessary because we are overwriting it
         }
    }

    /**
     * All Permutations I
     * Given a string with no duplicate characters, return a list with all permutations of the characters.
     * input = “abc”, all permutations are [“abc”, “acb”, “bac”, “bca”, “cab”, “cba”]
     *
     * Time = O(n!)  // 3 * 2 * 1 for "abc"
     * Space = O(n)
     */
    public List<String> getAllPermutations(String set) {
        List<String> result = new ArrayList<>();
        if (set == null) {
            return result;
        }
        char[] array = set.toCharArray();
        getAllPermutations(array, 0, result);
        return result;
    }

    private void getAllPermutations(char[] array, int index, List<String> result) {
        /* index is the current level that we are trying (第index个position放哪个字母)
         * all position filled with one char
         * Notice that can't be index == array.length - 1, "" -> [] not [""] */
        if (index == array.length) {
            result.add(new String(array));
            // System.out.println(array);
            return;
        }

        // put each letter in the index-th position of the input string
        for (int i = index; i < array.length; i++) { // index从0开始, 但是每次从index开始!
            /* e.g. 第0层, 所有字母跟第0个换一下
             * all the position(0, index - 1) are already chosen
             * all possible character could be place at index are from (index, array.length - 1) */
            swap(array, index, i); // swap remaining chars with index
            getAllPermutations(array, index + 1, result);
            swap(array, index, i); // swap it back for reset (吃了吐)
        }
    }

    /**
     * Two Subsets With Min Difference
     * Given a set of n integers, divide the set in two subsets such that the
     * difference of the sum of two subsets is as minimum as possible. Return the
     * minimum difference(absolute value).
     *
     * Time = (2 ^ n)
     * Space = O(n)
     */
    public int minDifference(int[] array) {
        int totalSum = 0;
        for (int i : array) {
            totalSum += i;
        }
        int[] preSum = new int[1];
        int[] minDiff = new int[]{Integer.MAX_VALUE};
        findMinDiff(0, array, preSum, minDiff, totalSum, 0);
        return minDiff[0];
    }

    private void findMinDiff(int index, int[] array, int[] preSum, int[] minDiff, int totalSum, int size) {
        if (index == array.length) {
            if (size > 0 && size < array.length - 1) {
                minDiff[0] = Math.min(minDiff[0], Math.abs(totalSum - preSum[0] * 2));
            }
            return;
        }
        preSum[0] += array[index];
        findMinDiff(index + 1, array, preSum, minDiff, totalSum, size + 1);
        preSum[0] -= array[index];
        findMinDiff(index + 1, array, preSum, minDiff, totalSum, size);
    }

    /**
     * All Subsets II
     * Given a set of characters represented by a String, return a list containing all subsets of the characters.
     * Assumptions: There could be duplicate characters in the original set.
     * Time = O(2 ^ n)
     * Space = O(n)
     */
    public List<String> findAllSubSetsII(String set) {
        List<String> result = new ArrayList<>();
        if (set == null) {
            return result;
        }
        StringBuilder prefix = new StringBuilder(); // store each subsets
        char[] input = set.toCharArray();
        Arrays.sort(input); // set might not be sorted!
        findSubsetsII(input, 0, prefix, result);
        return result;
    }

    private void findSubsetsII(char[] input, int index, StringBuilder prefix, List<String> result) {
        if (index == input.length) {
            result.add(prefix.toString());
            return;
        }

        // case 1: add input[index]
        prefix.append(input[index]);
        findSubsetsII(input, index + 1, prefix, result);
        prefix.deleteCharAt(prefix.length() - 1);

        // since it‘s already sorted, skip all subsequent duplicate letters
        while (index < input.length - 1 && input[index] == input[index + 1]) {
            index++;
        }

        // case 2: do not add input[index]
        findSubsetsII(input, index + 1, prefix, result);
    }

    /**
     * All Valid Permutations Of Parentheses II
     * Get all valid permutations of l pairs of (), m pairs of <> and n pairs of {}.
     * e.g l = 1, m = 1, n = 0, all the valid permutations are ["()<>", "(<>)", "<()>", "<>()"]
     *
     * Time = O(m ^ n) // m is the branches, n is the number of brackets
     * Space = O(n)
     */
    public List<String> findAllValidPermutationsOfParentheses2(int l, int m, int n) {
        if (l == 0 && m == 0 && n == 0) {
            return new ArrayList<>();
        }
        char[] brackets = new char[]{'(', ')', '<', '>', '{', '}'};
        int[] remain = new int[]{l, l, m, m, n, n}; // {(, ), <, >, {, }}
        int targetLength = (l + m + n) * 2;
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder prefix = new StringBuilder();
        List<String> result = new ArrayList<>();
        findValidParenthesesCombo(targetLength, remain, brackets, stack, prefix, result);
        return result;
    }

    private void findValidParenthesesCombo(int targetLength, int[] remain, char[] brackets,
                                           Deque<Character> stack, StringBuilder prefix, List<String> result) {
        if (prefix.length() == targetLength) {
            result.add(prefix.toString());
            return;
        }

         /* case 1: whenever we add a new left bracket:
          *         check leftRemain, push it to the Stack and add it to the solution prefix
          * case 2: whenever we add a new right bracket:
          *         check whether it matches the top element of the stack
          *         case 2.1: if matches, stack.pop(), add it to the solution prefix
          *         case 2.2: if not match, prune this branch (do not call the recursion function) */
        for (int i = 0; i < remain.length; i++) {
            if (i % 2 == 0) { // a left bracket
                if (remain[i] > 0) {
                    stack.offerFirst(brackets[i]);
                    prefix.append(brackets[i]);
                    remain[i]--;
                    findValidParenthesesCombo(targetLength, remain, brackets, stack, prefix, result);
                    stack.pollFirst();
                    prefix.deleteCharAt(prefix.length() - 1);
                    remain[i]++;
                }
            } else { // a right brackets
                if(!stack.isEmpty() && stack.peekFirst() == brackets[i - 1]) {
                    stack.pollFirst();
                    prefix.append(brackets[i]);
                    remain[i]--;
                    findValidParenthesesCombo(targetLength, remain, brackets, stack, prefix, result);
                    stack.offerFirst(brackets[i - 1]); // NOTICE, which character we just polled?
                    prefix.deleteCharAt(prefix.length() - 1);
                    remain[i]++;
                }
            }
        }
    }

    /**
     * All Valid Permutations Of Parentheses III
     * Get all valid permutations of l pairs of (), m pairs of <> and n pairs of {},
     * subject to the priority restriction: {} higher than [] than ().
     *
     * Time = O(m ^ n) // m is the branches, n is the number of brackets
     * Space = O(n)
     */
    public List<String> findAllValidPermutationsOfParentheses3(int l, int m, int n) {
        if (l == 0 && m == 0 && n == 0) {
            return new ArrayList<>();
        }
        char[] brackets = new char[]{'(', ')', '<', '>', '{', '}'};
        int[] remain = new int[]{l, l, m, m, n, n}; // {(, ), <, >, {, }}
        int targetLength = (l + m + n) * 2;
        Deque<Integer> stack = new ArrayDeque<>();
        StringBuilder prefix = new StringBuilder();
        List<String> result = new ArrayList<>();
        findParenthesesComboWithPriority(targetLength, remain, brackets, stack, prefix, result);
        return result;
    }

    private void findParenthesesComboWithPriority(int targetLength, int[] remain, char[] brackets,
                                                  Deque<Integer> stack, StringBuilder prefix, List<String> result) {
        if (prefix.length() == targetLength) {
            result.add(prefix.toString());
            return;
        }

        /* case 1: whenever we add a new left bracket:
         *         check leftRemain and whether the priority of current bracket ≥ the top of the Stack,
         *         then push it to the Stack and add it to the solution prefix
         * case 2: whenever we add a new right bracket:
         *         check whether it matches the top element of the stack
         *         case 2.1: if matches, stack.pop(), add it to the solution prefix
         *         case 2.2: if not match, prune this branch (do not call the recursion function) */
        for (int i = 0; i < remain.length; i++) {
            if (i % 2 == 0) { // a left bracket
                if (remain[i] > 0 && (stack.isEmpty() || stack.peekFirst() > i)) {
                    stack.offerFirst(i);
                    prefix.append(brackets[i]);
                    remain[i]--;
                    findParenthesesComboWithPriority(targetLength, remain, brackets, stack, prefix, result);
                    stack.pollFirst();
                    prefix.deleteCharAt(prefix.length() - 1);
                    remain[i]++;
                }
            } else { // a right brackets
                if(!stack.isEmpty() && stack.peekFirst() == i - 1) {
                    stack.pollFirst();
                    prefix.append(brackets[i]);
                    remain[i]--;
                    findParenthesesComboWithPriority(targetLength, remain, brackets, stack, prefix, result);
                    stack.offerFirst(i - 1); // NOTICE, which character we just polled?
                    prefix.deleteCharAt(prefix.length() - 1);
                    remain[i]++;
                }
            }
        }
    }

    /**
     * Factor Combinations
     * Given an integer number, return all possible combinations of the factors that
     * can multiply to the target number.
     *
     * Time = n^m // O(log(n) ^ (number of factors)) , m is # of factors
     * Space = O(m)
     */
    public List<List<Integer>> factorCombinations(int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (target < 3) {
            return result;
        }

        List<Integer> factors = getFactors(target);
        List<Integer> cur = new ArrayList<>();
        getValidFactorCombinations(target, factors, 0, cur, result);
        return result;
    }

    private void getValidFactorCombinations(int target, List<Integer> factors, int index,
                                            List<Integer> cur, List<List<Integer>> result) {
        if (target == 1) {
            result.add(new ArrayList<>(cur));
            return;
        }
        if (index == factors.size()) {
            return;
        }

        int factor = factors.get(index);
        // not using current factor
        getValidFactorCombinations(target, factors, index + 1, cur, result);
        int size = cur.size();
        while (target % factor == 0) {
            cur.add(factor);
            target /= factor;
            getValidFactorCombinations(target, factors, index + 1, cur, result);
        }
        cur.subList(size, cur.size()).clear();
    }

    private void getValidFactorCombinations2(int n,
                                             List<Integer> factors, List<Integer> prefix, List<List<Integer>> result) {
        if (n == 1) {
            result.add(new ArrayList<>(prefix));
            return;
        }

        for (int factor : factors) {
            if (n % factor == 0
                    && (prefix.isEmpty() || factor <= prefix.get(prefix.size() - 1))) {
                prefix.add(factor);
                getValidFactorCombinations2(n / factor, factors, prefix, result);
                prefix.remove(prefix.size() - 1);
            }
        }
    }

    private List<Integer> getFactors(int target) {
        List<Integer> factors = new ArrayList<>();
        for (int i = target / 2; i > 1; i--) {
            if (target % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }

    /**
     * 4^99 version, not recommended
     */
    private void getValidFactorCombinations2(int cur, int index, List<Integer> factors, int target,
                                             List<Integer> prefix, List<List<Integer>> result) {
        if (cur == target) {
            result.add(new ArrayList<>(prefix));
            return;
        }

        if (cur > target) {
            return;
        }

        for (int i = index; i < factors.size(); i++) {
            prefix.add(factors.get(i));
            getValidFactorCombinations2(cur * factors.get(i), i, factors, target, prefix, result);
            prefix.remove(prefix.size()-1);
        }
    }

    /**
     * All Permutations II
     * Given a string with possible duplicate characters, return a list with all
     * permutations of the characters.
     * e.g. Set = "aba", all permutations are ["aab", "aba", "baa"]
     *
     * Time = O(n!)
     * Space = O(n ^ 2)
     */
    public List<String> getAllPermutationsII(String set) {
        List<String> result = new ArrayList<>();
        if (set == null) {
            return result;
        }
        if (set.length() == 0) {
            result.add("");
            return result;
        }
        char[] chars = set.toCharArray();
        getAllPermutationsII(chars, 0, result);
        return result;
    }

    private void getAllPermutationsII(char[] chars, int index, List<String> result) {
        if (index == chars.length - 1) {
            result.add(new String(chars));
            return;
        }
        Set<Character> set = new HashSet<>();
        for (int i = index; i < chars.length; i++) {
            if (set.add(chars[i])) {
                swap(chars, index, i);
                getAllPermutationsII(chars, index + 1, result);
                swap(chars, index, i);
            }
        }
    }

    /**
     * Get all valid ways of putting N Queens on an N * N chessboard so that no two
     * Queens threaten each other.
     *
     * Time = O(n ^ n)  // O(n * n!)
     * Space = O(n)
     */
    public List<List<Integer>> nQueens(int n) {
        // the result of how to put n queens on a board
        List<List<Integer>> res = new ArrayList<>();
        if (n <= 0) {
            return res;
        }

        /* cur will be a list of size n, and cur[i] is the column number where the
         * queen on row i positioned
         * index =  0  1  2  3  ...   // row number
         * cur   = {1, 3, 5, 7, ...}  // column number */

        List<Integer> cur = new ArrayList<Integer>(); // store the cur result
        // actually we don't need to pass a variable curRow
        // since cur.size() will be the current row number
        putQueens(n, 0, cur, res); // n = num of rows
        return res;
    }

    private void putQueens(int n, int level, List<Integer> cur, List<List<Integer>> res) {
        // base case: all queens has been put in row 0 to n - 1.
        if (level == n) {
            res.add(new ArrayList<Integer>(cur));
            return; // 可以不写
        }

        for (int i = 0; i < n; i++) {
            // check if putting a queen on current row at column i is valid
            if(isValid(cur, i)) {
                cur.add(i);
                putQueens(n, level + 1, cur, res);
                cur.remove(cur.size() - 1); // recovery
            }
        }
    }

    private boolean isValid(List<Integer> cur, int curCol) {
        /* Row: no need since we put queue per row A[N] = {1, 3, 5, 7}
         * Column: curCol != preCol
         * Diagonal: abs(行差) == abs(列差) */
        for (int i = 0; i < cur.size(); i++) {
            int row = i;
            int col = cur.get(i);
            if (curCol == col
                    || Math.abs(curCol - col) == Math.abs(cur.size() - row)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        DepthFirstSearch dfs = new DepthFirstSearch();
        String s = "abc";
        System.out.println(dfs.findAllSubSets(s));
        dfs.findValidPermutationsOfParentheses(3);

        dfs.getCombinationOf99Cents(99,new int[]{25, 10, 5, 1},0,new int[4]);
        dfs.getAllPermutations("abc");
    }

    private void swap(char[] array, int left, int right) {
        char temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }
}
