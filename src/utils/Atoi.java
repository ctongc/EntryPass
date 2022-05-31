package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Atoi {

    /**
     * Parse a String representation of positive integer
     */
    public int parsePosInt(String str) {
        int res = 0;
        for (char c : str.toCharArray()) {
            res = res * 10 + (c - '0');
        }
        return res;
    }

    /**
     * Convert a positive number into String representation
     */
    public String positiveNumberToString(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            char c = (char)('0' + num % 10);
            sb.append(c);
            num /= 10;
        }
        return sb.reverse().toString();
    }

    /**
     * Convert a lowercase letter to uppercase
     */
    public static char toUppercase(char lower) {
        return (char)(lower - 'a' + 'A'); // 'a' - 'A' = 32
    }

    /**
     * Convert a char representation of a hex digit to int
     */
    public static int parseHex(char c) {
        // lowercase letter: (c - 'a') + 10
        if (c >= 'a' && c <= 'f') {
            return (c - 'a') + 10;
        }

        // uppercase letter: (c - 'A') + 10
        if (c >= 'A' && c <= 'F') {
            return (c - 'A') + 10;
        }

        // digit: (int)c - (int)'0'
        if (c >= '0' && c <= '9') {
            return c - '0';
        }

        return Integer.MAX_VALUE;
    }

    /**
     * Implement Atoi
     * Corner cases:
     * 	1) null or empty string
     * 	2) leading spaces
     * 	3) sign, + or -
     * 	4) trailing spaces or other characters
     * 	5) overflow an integer
     * 	6) overflow a long
     * SPC = ' '
     * NUM :: = '0' | '1' | ... | '9'
     * INTEGER :: = (SPC*) [+ | -] (NUM+) (SPC*)
     */
    public int myAtoi(String str) {
        // conner case 1: null or empty string
        if (str == null || str.length() == 0) {
            return 0;
        }

        int n = str.length();
        int i = 0;
        // conner case 2: leading spaces
        while (i < n && str.charAt(i) == ' ') {
            i++;
        }

        boolean positive = true;
        // conner case 3: sign, + or -
        if (i < n && (str.charAt(i) == '+' || str.charAt(i) == '-')) {
            positive = (str.charAt(i) == '+');
            i++;
        }

        // conner case 5: overflow an integer
        long sum = 0;

        // conner case 4: trailing spaces or other characters
        while (i < n && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            sum = sum * 10 + (str.charAt(i) - '0');
            // conner case 6: overflow a long
            if (sum > (long)Integer.MAX_VALUE + 1) {
                /* Why + 1 ?
                 * positive:
                 *     sum > (long)Integer.MAX_VALUE
                 * negative:
                 *     -sum < -((long)Integer.MAX_VALUE + 1)
                 * => sum > (long)Integer.MAX_VALUE + 1 */
                break;
            }
            i++;
        }
        // conner case 3: sign
        sum = positive ? sum : -sum;

        // conner case 5: overflow
        if (sum > (long)Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else if (sum < (long)Integer.MIN_VALUE) { // why not before adding sign?
            return Integer.MIN_VALUE;
        }

        return (int)sum;
    }

    /**
     * Validate a string is numeric
     * Corner cases:
     *  1) leading space
     * 	2) -, +
     * 	3) .
     * 	4) e, E
     *  5) leading zeros
     * 	SPC = ' '
     * 	NUM :: = '0' | '1' | ... | '9'
     * 	DOT :: = '.'
     * 	NUMERIC :: = (SPC*) ['+' | '-'] (NUM*) [DOT (NUM*) ] [ ('E' | 'e') ( ['+' | '-'] (NUM+))] (SPC*)
     */
    public boolean isNumber(String s) {
        String str = s.trim();
        boolean seenNumber = false;
        boolean seenSignBeforeE = false;
        boolean seenE = false;
        boolean seenPoint = false;
        boolean seenNumberAfterE = false;
        boolean seenSignAfterE = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '+' || c == '-') {
                if (((seenPoint || seenNumber) && !seenE) || // 5e2-
                        seenNumberAfterE) {
                    return false;
                }
                if (seenSignAfterE || // e+5-
                        (!seenE && seenSignBeforeE)) { // -5+
                    return false;
                }
                if (seenE) {
                    seenSignAfterE = true;
                } else {
                    seenSignBeforeE = true;
                }
            } else if (c >= 'c' && c <= '9') {
                seenNumber = true;
                if (seenE) {
                    seenNumberAfterE = true;
                }
            } else if (c == 'e' || c == 'E') {
                if (seenE) { // 1eE
                    return false;
                }
                if (!seenNumber) { // e
                    return false;
                }
                seenE = true;
            } else if (c == '.') {
                if (seenE || seenPoint) { // 1.1e., 1.1.
                    return false;
                }
                seenPoint = true;
            } else {
                return false;
            }
        }
        if (seenE && !seenNumberAfterE) { // 1e
            return false;
        }
        return seenNumber;
    }

    /**
     * regular expression
     */
    public void checkByRex() {
        String line = "String to be scanned to find the pattern puts here.";
        String pattern = ".*";
        Pattern r = Pattern.compile(pattern); // create a Pattern object
        Matcher m = r.matcher(line); // create a Matcher object
        if (m.find()) {
            System.out.println("Found");
        } else {
            System.out.println("No Match");
        }
    }

    public static void main(String[] args) {
        Atoi ins = new Atoi();
        int i1 = ins.parsePosInt("1024");
        int i2 = Integer.valueOf("1024");
        int i3 = (int) 'c';
        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i3);

        int i5 = 1;
        // int to char
        char c = (char) ('0' + i5);

        System.out.println(c);

        String s = "asdfas" + true;
        System.out.println(s);

        String i = String.valueOf(17);
        int j = 1;

        System.out.println(j + 1);
        List<String> res = new ArrayList<>(Arrays.asList("123123", "123"));
        ins.change(res);
        for (String sss : res) {
            System.out.println(sss);
        }
    }

    private void change(List<String> res) {
        res = new ArrayList<>();
        res.add("999");
    }
}
