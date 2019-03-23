package algorithms;

import java.util.HashSet;

public class BitOperations {
    private BitOperations() {}

    public static void printBinary(int num) {
        System.out.print(num + " : ");
        StringBuilder sb = new StringBuilder();
        for (int shift = 31; shift >= 0; shift--) {
            sb.append((num >>> shift) & 1);
        }
        System.out.println(sb.toString());
    }

    /**
     * Given a number x, test whether x's k-th bit is 1 (bit tester)
     *
     * k = 0 -> least significant bit
     */
    public int bitTester(int x, int k) { // bitGetter

        return (x >> k) & 1; // == x[k], return that bit
        // better than x & (1 << k) since the result could be 0 or 2^n
        // but (x >> k) & 1 could only be 00..00 or 00..01
    }

    /**
     * Given a number x, set x's k-th bit to 1 (bit setter)
     *
     * k = 0 -> least significant bit
     */
    public int bitSetter(int x, int k) {
//        int bitSetter = 1;
//        bitSetter = bitSetter << k;
//        x |= bitSetter;
        return x | (1 << k);
    }

    /**
     * Given a number x, set x's k-th bit to 0 (bit reSetter)
     *
     * k = 0 -> least significant bit
     */
    public int bitReSetter(int x, int k) {
//        int bitReSetter = 1;
//        bitReSetter = bitReSetter << k;
//        bitReSetter = ~bitReSetter;
//        x &= bitReSetter;
        return x & (~(1 << k));
    }

    /**
     * determine whether a number x is a power of 2
     *
     * 任何数 & 0 = 0, & 自己 = 自己
     */
    public boolean isPowerOfTwo(int number) {
        // corner case
        if (number <= 0) {
            return false;
        }

        // byte xByte = (byte) number;
        int count = 0;

//        for (int i = 0; i < 31; i++) {
//            if (((number >> i) & 1) == 1) count++;
//        }
        while (number > 0) {
            // check n's least significant bit is 1 or not
            count += (number & 1); // getter
            number >>= 1; // left move 左补0
        }

        return count == 1;
    }

    /**
     * Determine the number of bits that are different for two given integers.
     *
     * ^ XOR (判断一样不一样)
     * 0 ^ 0 -> 0
     * 1 ^ 1 -> 0
     * 0 ^ 1 -> 1
     * 0 ^ 任何数 -> 任何数
     */
    public int numOfDifferentBits(int a, int b) {

        int c = a ^ b;
        // after XOR, only the bits are different will be 1
        int count = 0;

        // using >>> 补0 而不是>> 补符号位 and c != 0 as terminate condition
        // else can't deal with a or b or c < 0
        while (c != 0) {
            if ((c & 1) == 1) {
                count++;
            }
            c = c >>> 1;
        }

        return count;
    }

    /**
     * Determine if the characters of a given string are all unique.
     *
     * the value of valid characters are from 0 to 255
     */
    public boolean containsAllUniqueChar(String word) {

        if (word == null || word.length() == 0) {
            return true;
        }

        HashSet<Character> occurred = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {

            // if occurred.contains(word.charAt(i)) return false;
            // else occurred.add(word.charAt(i));
            if (!occurred.add(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine if the characters of a given string are all unique.
     *
     * the value of valid characters are from 0 to 255
     */
    public boolean containsAllUniqueChar2(String word) {

        if (word == null || word.length() == 0) {
            return true;
        }

        boolean[] occurred = new boolean[256];
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (occurred[c]) {
                return false; // c will convert to the ascii
            }
            else occurred[c] = true;
        }
        return true;
    }

    /**
     * Determine if the characters of a given string are all unique.
     *
     * the value of valid characters are from 0 to 255
     */
    public boolean containsAllUniqueChar3(String word) {

        if (word == null || word.length() == 0) {
            return true;
        }

        int[] occurred = new int[8]; //bit vector

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            /*
             * Occurred[0] = xxxx xxxx …. xxxx // 31 ~ 0  注意是从31 ~ 0: 31 30 29 28 xxxx … xxxx 3 2 1 0
             * Occurred[1] = xxxx xxxx …. xxxx // 63 ~ 32 比如34是这行第2位
             * Occurred[2] = xxxx xxxx …. xxxx // 95 ~ 64
             * …
             * Occurred[7] = xxxx xxxx …. xxxx // 255 ~ 224
             */
            int row = c / 32;
            int col = c % 32;

            if ((occurred[row] & (1 << col)) != 0) {
                return false;
            } else {
                occurred[row] |= (1 << col);
            }
        }
        return true;
    }

    /**
     * Reverse all bits of a number
     */
    public int reverseBits(int num) {
        /* i is the left index
         * j is the right index */
        for (int i = 0, j = 31; i < j; i++, j--) {
            num = swapBitPair(num, i, j);
        }
        return num;
    }

    private int swapBitPair(int num, int i, int j) {
        int rightBit = (num >> i) & 1; // get the bit, 0 or 1
        int leftBit = (num >> j) & 1;

        if ((leftBit ^ rightBit) == 1) { // if they are different
            /*        0b b7 b6 b5 b4 b3 b2 b1 b0
             * bitMask = 1  0  0  0  0  0  0  1
             * x^bMask = ~b7 b6 b5 b4 b3 b2 b1 ~b0 */
            num ^= ((1 << i) | (1 << j)); // 100... | ...001 = 100...001 create the mask
        }
        return num;
    }

    public String integerToHex(int number) {
        char[] base = {'0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        boolean isLeading = true;
        StringBuilder sb = new StringBuilder("0x");
        for (int i = 28; i >= 0; i -= 4) { // i is the mask end
            // since we compare 4 bits by 4 bits
            // use 0xF, which is 0000..1111 in stead of 1
            char digit = base[(number >> i) & 0xF];
            if (digit != '0' || !isLeading) {
                sb.append(digit);
                isLeading = false;
            }
        }
        if (isLeading) {
            sb.append("0");
        }
        return sb.toString();
    }

    private void swap(int[] nums, int i, int j) {
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

    public static void main(String[] args) {

        byte a = (byte) 0b11001011;
        System.out.println(a);
        byte b;
        b = (byte) (a << 1);
        System.out.println(b);
        b = (byte) (a << 2);
        System.out.println(b);

        BitOperations solution = new BitOperations();
        System.out.println(solution.containsAllUniqueChar2("acsdfw"));
        System.out.println(solution.bitReSetter(3,1));

        System.out.println('9' - '3' == 6);

        System.out.println("~~~~~~~~~~~~~~");
        int numA = a;
        printBinary(numA);
        printBinary(solution.reverseBits(numA));

        System.out.println(solution.integerToHex(137894));

        //System.out.println();
    }
}
