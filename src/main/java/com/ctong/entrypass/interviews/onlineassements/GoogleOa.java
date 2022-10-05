package com.ctong.entrypass.interviews.onlineassements;

import java.util.*;

public class GoogleOa {

    /**
     * Email处理
     * email地址分为local@domain:
     * (1) local里dots('.') between some characters 要去除  -- Note @后面的.不受影响
     * (2) local里如果有'+'，'+'和后面的全去除  -- Note @后面没有+
     * eg, 'a.b@example.com' -> 'ab@example.com', 'dupli......cate@example.com' -> 'duplicate@example.com',
     * 'd.u.p.l.i.c.a.t.e@example.com' -> 'duplicate@example.com',  'ab+work@example.com' -> 'ab@example.com'
     * 处理完后的邮件地址一样的放在一组, 返回所有组里面不止一个邮件地址的组的个数
     * Time = O(n * k) // n is the number of email addresses and k is the average length of the email addresses
     * Space = O(n)
     */
    public int groupEmail(String[] L) {
        Map<String, List<String>> emailGroup = new HashMap<>(); // group emails that are same after simplify
        StringBuilder emailBuilder = new StringBuilder(); // stores current result after simplify
        for (String s : L) { // s is original email
            emailBuilder.setLength(0); // reset the StringBuilder
            String simpleEmail = simplifyEmail(s, emailBuilder); // email being simplified
            System.out.println(s);
            System.out.println(simpleEmail);
            List<String> tempList = emailGroup.getOrDefault(simpleEmail, new ArrayList<>());
            tempList.add(s);
            emailGroup.put(simpleEmail, tempList); // add email to its corresponding group
        }
        int count = 0;
        for (Map.Entry<String, List<String>> e : emailGroup.entrySet()) {
            if (e.getValue().size() > 1) {
                count++;
            }
        }
        return count;
    }

    private String simplifyEmail(String s, StringBuilder emailBuilder) {
        int tempDotCount = 0; // stores dot count that might be needed
        boolean isLeadingDot = true;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                tempDotCount++;
            } else if (s.charAt(i) == '+') {
                // ignore all following chars until see the '@'
                while (i < s.length() && s.charAt(i) != '@') {
                    i++;
                }
                // append domain
                addDomain(i, s, emailBuilder);
                break;
            } else {
                // keep leading dots
                if (isLeadingDot) {
                    isLeadingDot = false;
                    while (tempDotCount > 0) {
                        emailBuilder.append('.');
                        tempDotCount--;
                    }
                }
                if (s.charAt(i) == '@') {
                    // keep tailing dots
                    while (tempDotCount > 0) {
                        emailBuilder.append('.');
                        tempDotCount--;
                    }
                    // append domain
                    addDomain(i, s, emailBuilder);
                    break;
                }
                tempDotCount = 0;
                emailBuilder.append(s.charAt(i));
            }
        }
        return emailBuilder.toString();
    }

    private void addDomain(int i, String s, StringBuilder emailBuilder) {
        // append domain
        while (i < s.length()) {
            emailBuilder.append(s.charAt(i));
            i++;
        }
    }

    /**
     * 采水果
     * 小红去果园采水果, 有2个篮子, 可以装无数个水果, 但是每个篮子只能装一种水果
     * 从任意位置的树开始, 往右采, 遇到2种情况退出:
     * (1) 遇到第三种水果, 没有篮子可以放了
     * (2) 到头了
     * 返回可以采摘的最多的水果个数
     * eg. [1,2,1,3,4,3,5,1,2] return 3, [1,2,1,2,1,2,1] return 7
     * Time = O(n) // n is the number of fruits among all the trees
     * Space = O(k) // k is the number of types of fruit could be kept in the basket, in this case k = 2
     */
    public int totalFruit(int[] A) {
        if (A.length < 2) {
            return A.length;
        }
        int max = 0; // result
        int count = 0; // stores number of fruits in the basket
        Set<Integer> basket = new HashSet<>();
        for(int i = 0; i < A.length; i++) {
            if (basket.size() < 2 && !basket.contains(A[i])) { // base case
                basket.add(A[i]);
                count++;
            } else if (basket.contains(A[i])) {
                count++;
            } else { // if we meet a 3rd type fruit
                int temp = i - 1; // the fruits will be kept in the basket are A[i] and A[i - 1]
                // reset
                basket.clear();
                count = 0;
                // count how many A[i - 1] will be kept in the basket
                while (temp >= 0 && A[temp--] == A[i - 1]) {
                    count++;
                }
                basket.add(A[i - 1]);
                basket.add(A[i]);
                count++; // add the new fruit in
            }
            max = Math.max(max, count);
        }
        return max;
    }

    public static void main(String[] args) {
        GoogleOa ins = new GoogleOa();
        int i = ins.groupEmail(new String[]{"chaotong@google.com", "chao.tong@google.com",
                "chaotong1@google.com", "chao.to.ng..1@google.com",
                "...chaotong@google.com", "chao.tong...@google.com",
                "chao.tong@goo.gle.com", "ch..ao.t.o.n.g@goo.gle.com",
                "chao.tong@g.o.o.g.l.e.com", "chao.tong@g.o.o.g.l.e.c.o.m"});
        int j = ins.groupEmail(new String[]{"a.b@example.com", "x@example.com", "x@exa.mple.com", "ab+1@example.com",
                "y@example.com", "y@example.com", "y@example.com"});
        //System.out.println(i);
        System.out.println(j);
    }
}
