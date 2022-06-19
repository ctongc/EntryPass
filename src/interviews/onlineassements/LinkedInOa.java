package interviews.onlineassements;

import java.util.*;

public class LinkedInOa {
    /**
     * 把给定的string左移i位再右移j位
     */
    public String getShiftedString(String s, int leftShifts, int rightShifts) {
        if (s == null || s.length() == 0 || leftShifts - rightShifts == 0) {
            return s;
        }
        char[] str = s.toCharArray();
        char[] result = new char[s.length()];
        int shifts = (leftShifts - rightShifts) % s.length();
        shifts = shifts > 0 ? shifts : s.length() + shifts;
        for (int i = 0; i < s.length(); i++) {
            if (shifts == s.length()) {
                shifts = 0;
            }
            result[i] = str[shifts++];
        }
        return Arrays.toString(result);
    }

    /**
     * 问能否用给定的雨伞种类恰好cover所有的人
     */
    public int getUmbrellas(int n, List<Integer> p) {
        // Write your code here
        // dp[i] represents the min number of umbrellas that need to be bought when i person
        Collections.sort(p);
        int[] min = new int[]{ Integer.MAX_VALUE };
        validUmbrellas(n, p, 0, 0, min);
        return min[0] == Integer.MAX_VALUE ? -1 : min[0];
    }

    private void validUmbrellas(int remain, List<Integer> p, int level, int amount, int[] min) {
        if (remain == 0) {
            min[0] = Math.min(amount, min[0]);
            return;
        }
        if (level == p.size()) {
            return;
        }
        int umbrellaSize = p.get(level);
        validUmbrellas(remain, p, level + 1, amount, min);
        // i is number of umbrellas in current size
        for (int i = 1; i <= remain/umbrellaSize; i++) {
            remain -= i * umbrellaSize;
            amount += i;
            validUmbrellas(remain, p, level + 1, amount, min);
            remain += i * umbrellaSize;
            amount -= i;
        }
    }

    /**
     * 求所有由按顺序元素组成的subarray sum
     */
    public int getSubarraySum(int[] arr) {
        int[] sum = new int[1];
        List<Integer> prefix = new ArrayList<>();
        getSum(arr, 0, prefix, sum);
        return sum[0];
    }

    private void getSum(int[] arr, int level, List<Integer> prefix, int[] sum) {
        if (level == arr.length) {
            for (int i : prefix) {
                sum[0] += i;
            }
            return;
        }
        getSum(arr, level + 1, prefix, sum);
        prefix.add(arr[level]);
        if (prefix.size() == 1 || prefix.get(prefix.size() - 2) == arr[level - 1]) {
            getSum(arr, level + 1, prefix, sum);
        }
        prefix.remove(prefix.size() - 1);
    }

    /**
     * 按二进制有多少个1排序
     */
    public void ascBinarySort(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : arr) {
            map.put(n, countBinaryOne(n));
        }
        Integer[] arr2 = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i];
        }
        Arrays.sort(arr2, (i1, i2) -> {
            if (map.get(i1).equals(map.get(i2))) {
                return i1 - i2;
            } else {
                return map.get(i1) - map.get(i2);
            }
        });
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr2[i];
        }

        Arrays.sort(arr2, new IntComparator(map));
    }

    static class IntComparator implements Comparator<Integer> {

        Map<Integer, Integer> map;

        public IntComparator(Map<Integer, Integer> map) {
            this.map = map;
        }

        @Override
        public int compare(Integer e1, Integer e2) {
            if (!e1.equals(e2)) {
                return e1 - e2;
            } else {
                Integer v1 = map.get(e1);
                Integer v2 = map.get(e2);
                return v1 - v2;
            }
        }
    }

    private int countBinaryOne(int n) {
        int count = 0;
        while(n > 0)
        {
            if (n % 2 == 1) {
                count++;
            }
            n = n / 2;
        }
        return count;
    }

    public static void main(String[] args) {
        LinkedInOa ins = new LinkedInOa();
        List<Integer> p = new ArrayList<>();
        p.add(2);
        p.add(4);
        System.out.println(ins.getUmbrellas(10, p));
        System.out.println(ins.getShiftedString("abcde", 10000,6));
        System.out.println(ins.getSubarraySum(new int[]{4,5,6}));
        int[] r9 = new int[]{7,8,6,5};
        ins.ascBinarySort(r9);
        for (int i : r9) {
            System.out.print(i + ", ");
        }

        String s1 = "nn";
        String s2 = "nann";
        System.out.println("~~~~~~~~~~");
        System.out.println(s1.compareTo(s2));
    }
}
