package algorithms;

import java.util.HashSet;
import java.util.Set;

public class BitOperations {
    /**
     * Print binary representation of an int value.
     */
    public static void printBinary(int num) {
        System.out.print(num + " : ");
        StringBuilder sb = new StringBuilder();
        // Integer.toBinaryString(num);
        for (int shift = 31; shift >= 0; shift--) {
            sb.append((num >>> shift) & 1); // 注意这里是逻辑右移, unsigned shift补0
        }
        System.out.println(sb);
    }

    /**
     * Given a number x, test whether x's k-th bit is 1 (bit tester).
     */
    public int bitTester(int x, int k) { // bitGetter
        /* better than x & (1 << k) since the result could be 0 or 2^n
         * (x >> k) & 1 could only be 00..00 or 00..01 */
        return (x >> k) & 1; // == x[k], return that bit
    }

    /**
     * Given a number x, set x's k-th bit to 1 (bit setter).
     */
    public int bitSetter(int x, int k) {
        // int bitSetter = 1;
        // bitSetter = bitSetter << k;
        // x |= bitSetter;
        return x | (1 << k);
    }

    /**
     * Given a number x, set x's k-th bit to 0 (bit reSetter).
     */
    public int bitReSetter(int x, int k) {
        // int bitReSetter = 1;
        // bitReSetter = bitReSetter << k;
        // bitReSetter = ~bitReSetter;
        // x &= bitReSetter;
        return x & (~(1 << k));
    }

    /**
     * Determine whether a num x is a power of 2.
     *
     * Time = O(# of bits)
     * Space = O(1)
     */
    public boolean isPowerOfTwo(int num) {
        if (num < 0) {
            return false;
        }
        // byte xByte = (byte) num;
        int count = 0;
        // for (int i = 0; i < 31; i++) {
        //     if (((num >> i) & 1) == 1) count++;
        // }
        while (num > 0) {
            // check num's least significant bit is 1 or not
            count += (num & 1); // getter
            num >>= 1; // right shift 左补0
        }
        return count == 1;
    }

    /**
     * x is power of 2:      0b 0 0 1 0 0 0 0 0
     * x - 1:                0b 0 0 0 1 1 1 1 1
     * x is not power of 2:  0b 0 0 1 0 1 0 0 0
     * x - 1:                0b 0 0 1 0 0 1 1 1
     */
    public boolean isPowerOfTwo2(int num) {
        return num > 0 && (num & (num - 1)) == 0;
    }

    /**
     * Determine the number of bits that are different for two given integers.
     * ^ XOR (判断一样不一样)
     * 0 ^ 0 -> 0
     * 1 ^ 1 -> 0
     * 0 ^ 1 -> 1
     * 0 ^ 任何数 -> 任何数
     */
    public int numOfDifferentBits(int a, int b) {
        // assume a >= 0, b >= 0
        int c = a ^ b; // after XOR, only the bits are different will be 1
        int count = 0;

        /* using >>> 补0 而不是 >> 补符号位 and c != 0 as terminate condition
         * else can't deal with a or b or c < 0 */
        while (c != 0) {
            if ((c & 1) == 1) {
                count++;
            }
            c = c >>> 1;
        }
        // initialization; termination condition; increment
        // for (int c = a ^ b; c != 0; c >>>= 1) {
        //     count += (c & 1);
        // }
        return count;
    }

    /**
     * Determine if the characters of a given string are all unique.
     * The value of valid characters are from 0 to 255.
     */
    public boolean containsAllUniqueChar(String word) {
        if (word == null || word.length() == 0) {
            return true;
        }

        Set<Character> occurred = new HashSet<>();
        for (char c : word.toCharArray()) {
            if (!occurred.add(c)) {
                return false;
            }
        }

        return true;
    }

    public boolean containsAllUniqueChar2(String word) {
        if (word == null || word.length() == 0) {
            return true;
        }

        boolean[] occurred = new boolean[256];
        for (char c : word.toCharArray()) {
            if (occurred[c]) { // c will convert to the ASCII
                return false;
            } else {
                occurred[c] = true;
            }
        }
        return true;
    }

    public boolean containsAllUniqueChar3(String word) {
        /* assume that the String is in ASCII representation */
        if (word == null || word.length() == 0) {
            return true;
        }

        int[] occurred = new int[8]; // bit vector, 8 integers to represents 256 bits
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
            num = reverseBitPair(num, i, j);
        }
        return num;
    }

    private int reverseBitPair(int num, int i, int j) {
        int rightBit = (num >> i) & 1; // get the bit, 0 or 1
        int leftBit = (num >> j) & 1;

        if (leftBit != rightBit) { // if they are different
            /*         0b b7 b6 b5 b4 b3 b2 b1 b0
             * bitMask =   1  0  0  0  0  0  0  1
             * x^bMask = ~b7 b6 b5 b4 b3 b2 b1 ~b0 */
            num ^= ((1 << i) | (1 << j)); // 100... | ...001 = 100...001 create the mask
        }

        return num;
    }

    /**
     * Given a non-negative number x, get the hexadecimal representation of x in String type.
     */
    public String integerToHex(int num) {
        if (num == 0) {
            return "0x0";
        }

        char[] base = {'0', '1', '2', '3', '4',
                       '5', '6', '7', '8', '9',
                       'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(base[num % 16]); // note the order is reversed
            num /= 16;
        }
        sb.append("x0");
        sb.reverse();

        return sb.toString();
    }

    public String integerToHex2(int num) {
        if (num == 0) {
            return "0x0";
        }

        char[] base = {'0', '1', '2', '3', '4',
                       '5', '6', '7', '8', '9',
                       'A', 'B', 'C', 'D', 'E', 'F'};
        boolean isLeading = true;
        StringBuilder sb = new StringBuilder("0x");
        for (int maskEnd = 28; maskEnd >= 0; maskEnd -= 4) { // i is the mask end
            // since we compare 4 bits by 4 bits
            // use 0xF, which is 0000..1111 instead of 1
            char digit = base[(num >> maskEnd) & 0xF];
            if (digit != '0' || !isLeading) { // deal with leading zero
                sb.append(digit);
                isLeading = false;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        // 0
        int a = 0;
        printBinary(a);

        // positive number
        a = 5;
        printBinary(a); // 00000000 00000000 00000000 00000101
        // negative number
        a = -5;
        printBinary(a); // 11111111 11111111 11111111 11111011

        a = Integer.MIN_VALUE;
        printBinary(a); // 10000000 00000000 00000000 00000000
        a = Integer.MAX_VALUE;
        printBinary(a); // 01111111 11111111 11111111 11111111
        // Integer.MAX_VALUE + 1 = Integer.MIN_VALUE;
        // Integer.MIN_VALUE - 1 = Integer.MAX_VALUE;

        // all "1"
        a = -1;
        printBinary(a); // 11111111 11111111 11111111 11111111

        // signed shift - leftmost bit depends on previous leftmost bit
        int b = -5 >> 2;
        printBinary(b); // 11111111 11111111 11111111 11111110

        // unsigned shift - leftmost bit '0'
        b = -5 >>> 2;
        printBinary(b); // 00111111 11111111 11111111 11111110

        System.out.println("~~~~~~~~~~~~~~");

        byte b1 = (byte) 0b11001011;
        System.out.println(b1);
        byte b2;
        b2 = (byte) (b1 << 1);
        System.out.println(b2);
        b2 = (byte) (b1 << 2);
        System.out.println(b2);

        BitOperations solution = new BitOperations();
        System.out.println(solution.containsAllUniqueChar2("acsdfw"));
        System.out.println(solution.bitReSetter(3, 1));

        System.out.println('9' - '3' == 6);
        System.out.println("~~~~~~~~~~~~~~");

        int num = b1;
        printBinary(num);
        printBinary(solution.reverseBits(num));

        System.out.println(solution.integerToHex(137894));

        printBinary(7);
        printBinary(7 >> 1 & 1);
        printBinary(7 >> 2 & 1);

        short aa = -1;
        char bb = (char) aa;
        System.out.println((int)bb);

        System.out.println(0 - 'a');
        System.out.println(1 - 'b');

        int[][] points = new int[10][10];
        int[][] queries = new int[10][10];
        int[] point = points[0];
        int[] query = queries[0];
    }
}