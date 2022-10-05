package com.ctong.entrypass.playground;

import java.util.*;

public class PlayGround {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // sanity check
        if (wordList == null || wordList.isEmpty()) {
            return 0;
        }
        // put words into a set
        HashSet<String> words = new HashSet<>();
        for (String s : wordList) {
            words.add(s);
        }
        if (!words.contains(endWord)) {
            return 0;
        }
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);
        int level = 1; // result
        while(!queue.isEmpty()) {
            int size = queue.size();
            boolean findNext = false;
            for (int i = 0; i < size; i++) {

                String cur = queue.poll();
                if (cur.equals(endWord)) {
                    return level;
                }
                // when using for (String s : words), words can't be change!!!
                List<String> helper = new LinkedList<>();
                for (String s : words) {
                    helper.add(s);
                }
                for (String s : helper) {
                    if (isDifferByOne(cur, s)) {
                        queue.offer(s);
                        words.remove(s);
                        findNext = true;
                    }
                }
            }
            if(findNext) {
                level++;
            }
        }
        return 0;
    }

    private boolean isDifferByOne(String s1, String s2) {
        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            if(s1.charAt(i) != s2.charAt(i)) {
                diff++;
            }
        }
        return diff == 1;
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        // sanity check
        if (wordList == null || wordList.isEmpty()) {
            return result;
        }
        HashSet<String> visited = new HashSet<>();
        visited.add(beginWord);
        List<String> prefix = new ArrayList<>();
        prefix.add(beginWord);
        int[] minLength = new int[]{Integer.MAX_VALUE};
        ladderBuilder(endWord, wordList, visited, prefix, minLength, result);
        // post processing
        List<List<String>> finalResult = new ArrayList<>();
        for (List<String> list : result) {
            if (list.size() == minLength[0]) {
                finalResult.add(list);
                //System.out.println(list.toString());
            }
        }
        return finalResult;
    }

    private void ladderBuilder(String ew, List<String> wordList, HashSet<String> visited, List<String> prefix,
                               int[] minLength, List<List<String>> result) {
        String lastAdded = prefix.get(prefix.size() - 1);

        // base case
        if (prefix.size() > minLength[0]) {
            return;
        }
        if (lastAdded.equals(ew)) {
            result.add(new ArrayList<>(prefix));
            minLength[0] = prefix.size();
            return;
        }

        for (String s : wordList) {
            if (!visited.contains(s) && isDifferByOne(s, lastAdded)) {
                prefix.add(s);
                visited.add(s);
                ladderBuilder(ew, wordList, visited, prefix, minLength, result);
                visited.remove(s);
                prefix.remove(prefix.size() - 1);
            }
        }
    }

    public boolean existIJK(int[] array) {
        // sanity check
        if (array == null || array.length == 0) {
            return false;
        }

        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < first) {
                first = array[i];
            } else if (array[i] < second){
                second = array[i];
            } else if (array[i] > second){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // can't do this with Integer, there is no unbox for int[];
        String[] geeks = {"Rahul", "Utkarsh", "Shubham", "Neelam"};
        // array to array list
        List<String> list = new ArrayList<>(Arrays.asList(geeks));
        // array list to array
        String[] geeks2 = list.toArray(new String[list.size()]);
        for (String s: geeks2) {
            System.out.println(s);
        }

        char ch = 48; // 48 = '0', 65 = 'A', 97 = 'a'
        System.out.println(ch);
        char a = 'a';
        char b = 'b';
        System.out.println(a > b);

        PlayGround pg = new PlayGround();

        StringBuilder sb = new StringBuilder();
        sb.toString();
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {2, 3, 4};
        int[] arr3 = {1, 2, 3};
        System.out.println(arr1 == arr3);

        int aaa = 4375;
        int count = 0;
        for (int i = aaa / 2; i > 1; i--) {
            if (aaa % i == 0) {
                count++;
            }
        }
        System.out.println(count);
        if (count % 2 == 0) {
            System.out.println("zhuzhu");
        } else {
            System.out.println("niuniu");
        }
        System.out.println("------------");

        System.out.println(pg.existIJK(new int[]{5, 7, 2, 4}));

        String s = Arrays.deepToString(new int[][]{{1, 2}, {2, 3}, {3, 4}});
        System.out.println("s = " + s);
        List<Integer> testL = new ArrayList<>();
        testL.add(1);
        testL.add(2);
        testL.add(3);
        String[] sss = new String[]{"a", "abc", "ab"};
        Arrays.sort(sss, (s1, s2) -> s2.compareTo(s1));
        Arrays.sort(sss, Comparator.comparing(String :: toString));
        for (String sss1 : sss) {
            System.out.println(sss1);
        }
        System.out.println('a' - 'A');
        int[] whut = new int[]{1, 2, 3, 4, 5};
        for (int i = 0; i < whut.length; i++) {
            System.out.println(whut[i]);
        }

        LinkedList<Integer> s1 = new LinkedList<>();
        s1.offer(3);
        s1.offer(1);
        s1.offer(5);
        s1.offer(4);
        System.out.println(pg.minDifference(new int[]{5,-2,-10,3}));

        String sss999 = "aaa";
        System.out.println(sss999.charAt(0)+""+sss999.charAt(1));
    }

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
                System.out.println(minDiff[0]);
                minDiff[0] = Math.min(minDiff[0], Math.abs(totalSum - preSum[0] * 2));
            }
            return;
        }
        preSum[0] += array[index];
        findMinDiff(index + 1, array, preSum, minDiff, totalSum, size + 1);
        preSum[0] -= array[index];
        findMinDiff(index + 1, array, preSum, minDiff, totalSum, size);
    }
}
