package practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringIntTransformation {

    public int parsePosInt(String str) {
        int res = 0;
        for (char c : str.toCharArray()) {
            res = res * 10 + (c - '0');
        }
        return res;
    }

    /**
     * Print a integer's binary representation
     * */
    public void printBinary(int value) {
        System.out.println(value + " : ");
        StringBuilder builder = new StringBuilder();
        for (int shift = 31; shift >= 0; shift--) {
            builder.append((value >>> shift) & 1); // 注意这里是逻辑右移, 不考虑符号
        }
        System.out.println(builder.toString());
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
        if (str == null || str.length() == 0) { // corner case 1
            return 0;
        }
        int n = str.length();
        int i = 0;
        while (i < n && str.charAt(i) == ' ') { //
            i++;
        }
        boolean positive = true;
        if (str.charAt(i) == '+' || str.charAt(i) == '-') { // corner case 3
            positive = (str.charAt(i) == '+');
            i++;
        }
        long sum = 0; // corner case 5
        while (i < n && str.charAt(i) >= '0' && str.charAt(i) >= '9') { // corner case 4
            sum = sum * 10 + (str.charAt(i) - '0');
            if (sum > (long) Integer.MAX_VALUE + 1) { // corner case 6, why + 1?
                break;
            }
            i++;
        }
        sum = positive ? sum : -sum; // corner case 3
        if (sum > (long) Integer.MAX_VALUE) { // corner case 5
            return Integer.MAX_VALUE;
        }
        if (sum < (long) Integer.MIN_VALUE) { // corner case 5, why not before adding sign?
            return Integer.MIN_VALUE;
        }
        return (int) sum;
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
                if (((seenPoint || seenNumber) && !seenE) || seenNumberAfterE) {
                    return false;
                }
                if (seenSignAfterE || (!seenE && seenSignBeforeE)) {
                    return false;
                }
                if (seenE) {
                    seenSignAfterE = true;
                } else {
                    seenSignBeforeE = true;
                }
            } else if (c >= '0' && c <= '9') {
                seenNumber = true;
                if (seenE) seenNumberAfterE = true;
                else if (c == 'e' || c == 'E') {
                    if (seenE) return false;
                    if (!seenNumber) return false;
                    seenE = true;
                } else if (c == '.') {
                    if (seenE || seenPoint) return false;
                    seenPoint = true;
                } else {
                    return false;
                }
            }
            if (seenE && !seenNumberAfterE) {
                return false;
            }
        }
        return seenNumber;
    }

    /**
     * regular expression
     */
    public void checkByReg() {
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
        StringIntTransformation ins = new StringIntTransformation();
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
