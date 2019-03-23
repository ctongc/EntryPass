package interview;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Amazon {
    /**
     * Word Search
     * Given a 2D board and a word, find if the word exists in the grid.
     *
     * Time = O(n * m * 4 ^ k) (n m is the row/col numbers, k is the length of the word)
     * Space = O(n * m)
     */
    public boolean exist(char[][] board, String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        if (board == null || board.length == 0
                || board[0] == null || board[0].length == 0) {
            return false;
        }
        int row = board.length;
        int col = board[0].length;
        boolean[][] visited = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == word.charAt(0) && findWord(i, j, board, visited, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean findWord(int r, int c, char[][] board, boolean[][] visited, String word, int index) {
        if (index == word.length()) { // base case 1
            return true;
        }
        if(r < 0 || r >= board.length || c < 0 || c >= board[0].length || visited[r][c]
                || board[r][c] != word.charAt(index)) { // base case 2
            return false;
        }
        visited[r][c] = true;
        if (findWord(r + 1, c, board, visited, word, index + 1) ||
                findWord(r - 1, c, board, visited, word, index + 1) ||
                findWord(r, c + 1, board, visited, word, index + 1) ||
                findWord(r, c - 1, board, visited, word, index + 1)) {
            return true;
        }
        visited[r][c] = false; // restore
        return false;
    }

    /**
     * Longest subarray without repeating characters
     * Given a string, find the length of the longest substring without repeating characters.
     *
     * */
    public int lengthOfLongestSubstring(String s) {
        // slow - start of current substring
        // fast - end of current substring
        // globalMax - res
        // set, current substring chars
        // initial
        // slow 0, fast 0, globalMax -inf, set {}
        // for each step
        //   add fast to set
        //     can add, fast++
        //        update globalMax
        //     can't add, set.remove(s[slow++]), till can add
        // sanity check
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int slow = 0;
        int globalMax = Integer.MIN_VALUE;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (set.add(s.charAt(i))) {
                globalMax = Math.max(globalMax, i + 1 - slow);
            } else {
                while (!set.add(s.charAt(i))) {
                    set.remove(s.charAt(slow++));
                }
            }
        }
        return globalMax;
    }
    public int gcd(int a, int b) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }
        List<Integer> list = new ArrayList<>(); // store divisors from biggest to smallest
        for (int i = a/2; i > 1; i--) {
            if (a % i == 0) {
                list.add(i);
            }
        }
        for (int i : list) {
            if(b % i == 0) {
                return i;
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        Amazon amazon = new Amazon();
        amazon.lengthOfLongestSubstring("bbbbbbbbbb");
        // slow = 0, i = 1
        // max = 1
        // set = b
        int i = amazon.gcd(54, 24);
        System.out.println(i);
    }
}
