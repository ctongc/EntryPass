package com.ctong.entrypass.interviews;

public class CiticSecurities {

    /**
     * 实现四则运算算法
     */

    /**
     * java编写encode方法和decode方法, 分别实现对字符串的变换和复原。
     * encode()按以下规则逐组生成新字符串:
     * (1)若已知字符串的当前字符不是大于0的数字字符, 则复制该字符于新字符串中;
     * (2)若以已知字符串的当前字符是一个数字字符, 且它之后没有后继字符, 则简单地将它复制到新字符串中;
     * (3)若以已知字符串的当前字符是一个大于0的数字字符, 并且还有后继字符, 设该数字字符的面值为n,
     *    则将它的后继字符(包括后继字符是一个数字字符)重复复制n+1次到新字符串中;
     * (4)以上述一次变换为一组, 在不同组之间另插入一个下划线'_'用于分隔;
     * (5)若已知知字符串中包含有下划线'_', 则变换为用"/UL".
     *
     * e.g. encode()函数对字符串"24ab_2t2"的变换结果为"444_aaaaa_a_b_/UL_ttt_t_2"
     */
    public String encode(String rawText) {
        if (rawText == null || rawText.length() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rawText.length(); i++) {
            char cur = rawText.charAt(i);
            if (Character.isDigit(cur)) {
                if (cur <= '0') { // 1
                    sb.append(cur);
                } else if (i + 1 == rawText.length()) {
                    sb.append(cur); // 2
                } else { // 3
                    int n = Integer.parseInt(String.valueOf(cur)) + 1;
                    for (int j = 0; j < n; j++) {
                        sb.append(rawText.charAt(i + 1));
                    }
                }
            } else if (cur == '_') { // 5
                sb.append("/UL");
            } else {
                sb.append(cur); // 1
            }

            sb.append("_"); // 4
        }

        sb.deleteCharAt(sb.length() - 1); // remove last _

        return sb.toString();
    }

    public static void main(String[] args) {
        CiticSecurities a = new CiticSecurities();
        System.out.println(a.encode("24ab_2t2"));
    }
}