package algorithms;

import java.util.*;

public class StringBasics {
    private StringBasics() {
    }

    /**
     * Remove Certain Characters
     * Remove given characters in input string, the relative order of other
     * characters should be remained. Return the new string after deletion. Do it in place.
     * Time = O(n)
     * Space = O(n)
     */
    public String removeCertainCharacters(String input, String t) {
        // assumption: neither input nor t is null
        if (input.length() == 0 || t.length() == 0) {
            return input;
        }
        // pro-processing: put all characters from t into a set
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < t.length(); i++) {
            set.add(t.charAt(i));
        }
        char[] result = new char[input.length()];
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            if (!set.contains(input.charAt(i))) {
                result[index++] = input.charAt(i);
            }
        }
        return new String(result, 0, index);
    }

    /**
     * Remove Spaces
     * Given a string, remove all leading/trailing/duplicated empty spaces. Do it in place.
     * e.g. "_ _I_ _Love_Google_ _ _" -> "I_Love_Google"
     * Time = O(n)
     * Space = O(1)
     */
    public String removeSpaces(String input) {
        // assumption: The given string is not null
        if (input.length() == 0) {
            return input;
        }
        // if (input == " ") return "";
        char[] sArray = input.toCharArray();
        int slow = 0; // left hand side of slow (excluding slow) should be retained
        for (int fast = 0; fast < sArray.length; fast++) {
            if (sArray[fast] != ' ') {
                sArray[slow++] = sArray[fast];
            } else {
                // Case 2.1: i == 0 || input[i - 1] == ' ', ignore
                // Case 2.2: otherwise, keep
                if (slow != 0 && sArray[slow - 1] != ' ') {
                    sArray[slow++] = sArray[fast];
                }
            }
        }
        // the last could be ' '
        // ANYTIME see a index - 1, consider if the index could be zero
        if (slow != 0 && sArray[slow - 1] == ' ') {
            slow--;
        }
        return new String(sArray, 0, slow);
    }

    /**
     * Remove Adjacent Repeated Characters I
     * Remove adjacent, repeated characters in a given string leaving only one
     * character for each group of such characters. Do it in place.
     * e.g. "aabbbbcccc" -> "abc"
     * Time = O(n)
     * Space = O(1)
     */
    public String deDuplication(String input) {
        if (input == null || input.length() <= 1) {
            return input;
        }
        char[] sArray = input.toCharArray();
        int slow = 0; // put all retained character to [0, slow), not including slow
        for (int fast = 0; fast < sArray.length; fast++) {
            // Case 1: i == 0 || a[j] == a[i - 1], ignore
            // Case 2: a[j] != a[i - 1], keep
            if (slow == 0 || sArray[fast] != sArray[slow - 1]) {
                sArray[slow++] = sArray[fast];
            }
        }
        return new String(sArray, 0, slow);
    }

    /**
     * Remove Adjacent Repeated Characters IV
     * Repeatedly remove all adjacent, repeated characters in a given string from left to right
     * No adjacent characters should be identified in the final string
     * e.g. "abbbaaccz" → "aaaccz" → "ccz" → "z"
     * using stack
     * Time = O(n)
     * Space = O(n)
     */
    public String repeatDeDuplication(String input) {
        if (input == null || input.length() <= 1) {
            return input;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < input.length(); i++) {
            // Case 1: stack.top() == null || a[fast] != stack.top()
            //         then stack.push(a[fast], fast++)
            // Case 2: a[fast] == stack.top()
            //         keep moving fast until a[fast] != stack.top(), stack.pop()
            if (stack.peekFirst() == null || input.charAt(i) != stack.peekFirst()) {
                stack.offerFirst(input.charAt(i));
            } else {
                while (i + 1 < input.length() && input.charAt(i + 1) == stack.peekFirst()) {
                    i++;
                }
                stack.pollFirst();
            }
        }
        char[] result = new char[stack.size()];
        for (int i = stack.size() - 1; i >= 0; i--) {
            result[i] = stack.pollFirst();
        }
        return new String(result);
    }

    /**
     * 优化 - 用slow pointer来代替stack, 记录栈顶元素 (in place)
     * Time = O(n)
     * Space = O(1)
     */
    public String repeatDeDuplication2(String input) {
        if (input == null || input.length() <= 1) {
            return input;
        }
        char[] sArray = input.toCharArray();
        int slow = -1; // sArray[slow] is the top element of stack (including slow)
        for (int i = 0; i < input.length(); i++) {
            // Case 1: stack.top() == null || a[fast] != stack.top()
            //         then stack.push(a[fast], fast++)
            // Case 2: a[fast] == stack.top()
            //         keep moving fast until a[fast] != stack.top(), stack.pop()
            if (slow == -1 || sArray[i] != sArray[slow]) {
                sArray[++slow] = sArray[i];
            } else {
                while (i + 1 < input.length() && sArray[i + 1] == sArray[slow]) {
                    i++;
                }
                slow--; // stack.pop()
            }
        }
        return new String(sArray, 0, slow + 1);
    }

    /**
     * Determine If One String Is Another's Substring
     * Determine if a small string is a substring of another large string.
     * Return the index of the first occurrence of the small string in the large string.
     * Return -1 if the small string is not a substring of the large string.
     *
     * Time = O(m + n)
     * Space = O(1)
     */
    public int ifExistPattern(String large, String small) {
        // assumptions: both large and small are not null
        // if small is empty string, return 0
        if (large.length() < small.length()) {
            return -1;
        }
        if (small.length() == 0) {
            return 0;
        }
        int prime = 101;
        int targetHash = 0; // initialHash(small);
        int windowHash = 0; // initialHash(large.substring(0, small.length()));
        // calculate initial hash for both the target and window
        for (int i = 0; i < small.length(); i++) {
            targetHash = (targetHash * 26 + small.charAt(i)) % prime;
            windowHash = (windowHash * 26 + large.charAt(i)) % prime;
        }
        // factor = pow(26,small.length() - 1) % prime
        int factor = 1;
        for (int i = 0; i < small.length() - 1; i++) {
            factor = (factor * 26) % prime;
        }
        // hash(txt[s + 1 .. s + m]) = (26 * (hash(txt[s .. s + m - 1]) – txt[s] * 26 ^ (m - 1) + txt[s + m]) mod prime
        for (int i = 0; i <= large.length() - small.length(); i++) {
            if (targetHash == windowHash && isMatch(i, large, small)) { // might have same hash but not the same String
                return i;
            }
            if (i < large.length() - small.length()) { // means you can still right shift the window by 1
                windowHash = (26 * (windowHash - large.charAt(i) * factor) + large.charAt(i + small.length())) % prime;
                // We might get negative value of windowHash, converting it to positive
                if (windowHash < 0) {
                    windowHash += prime;
                }
            }
        }
        return -1;
    }

    private boolean isMatch(int startPos, String large, String small) {
        for (int i = 0; i < small.length(); i++) {
            if (large.charAt(startPos + i) != small.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reverse String
     * Reverse a given string.
     * using two pointers, if char at left != char at right, swap left and right
     * Time = O(n)
     * Space = O(1)
     */
    public String reverseString(String input) {
        if (input == null || input.length() <= 1) {
            return input;
        }
        char[] sArray = input.toCharArray();
        int left = 0;
        int right = sArray.length - 1;
        while (left < right) {
            if (sArray[left] != sArray[right]) {
                swap(sArray, left, right);
            }
            left++;
            right--;
        }
        return new String(sArray);
    }

    /**
     * recursion
     * base case i >= j
     */
    public String reverseString2(String input) {
        if (input == null || input.length() <= 1) {
            return input;
        }
        char[] sArray = input.toCharArray();
        reverseHelper(sArray, 0, sArray.length - 1);
        return new String(sArray);
    }

    private void reverseHelper(char[] sArray, int left, int right) {
        // base case
        if (left >= right) {
            return;
        }
        if (sArray[left] != sArray[right]) {
            swap(sArray, left, right);
        }
        // left + 1 going to the next recursion not left
        // so left + 1 not left++
        reverseHelper(sArray, left + 1, right - 1);
    }

    /**
     * Reverse Words In A Sentence I
     * Reverse the words in a sentence.
     * e.g. I love yahoo -> yahoo love I
     * Time = O(n)
     * Space = O(n)
     */
    public String reverseWords(String input) {

        if (input == null || input.length() == 0) {
            return input;
        }
        char[] chars = input.toCharArray();
        int left = 0;
        int right = input.length() - 1;
        // step1: reverse the whole sentence
        reverse(chars, left, right);
        // step2: reverse each word
        int slow = 0;
        for(int fast = 0; fast < chars.length; fast++) {
            if(fast == chars.length - 1 || chars[fast + 1] == ' ') {
                reverse(chars, slow, fast);
                slow = fast + 2;
            }
        }
        return new String(chars);
    }

    private void reverse(char[] chars, int left, int right){
        while (left < right) {
            if (chars[left] != chars[right]) {
                swap(chars, left, right);
            }
            left++;
            right--;
        }
    }

    /**
     * Right Shift By N Characters
     * Right shift a given string by n characters.
     *
     * Time = O(n)
     * Space = O(n)
     * */
    public String rightShift(String input, int n) {
        // assumption: n >= 0
        if (input == null || input.length() == 0) {
            return input;
        }
        char[] chars = input.toCharArray();
        // step 1 reverse whole string
        reverse(chars, 0, input.length() - 1);
        // step 2 reverse two parts separately
        int shifts = n % input.length();
        if (shifts == 0) {
            return input;
        }
        reverse(chars, 0, shifts - 1);
        reverse(chars, shifts, input.length() - 1);
        return new String(chars);
    }

    /**
     * String Replace
     * Given an original string input, and two strings S and T, replace all occurrences of S in input with T.
     * Time = O(n * n * m)
     * Space = O(n)
     *
     * can optimized by rabin karp or kmp to O(n) by find all match then replace => O(n)
     * notice replace from right to left with extended space
     */
    public String replaceString(String input, String s, String t) {
        StringBuilder sb = new StringBuilder();
        int fromIndex = 0;
        int matchIndex = input.indexOf(s, fromIndex); // complexity O(m * n)
        while (matchIndex != -1) {
            sb.append(input, fromIndex, matchIndex).append(t);
            fromIndex = matchIndex + s.length();
            matchIndex = input.indexOf(s, fromIndex);
        }
        sb.append(input, fromIndex, input.length());
        return sb.toString();
    }

    /**
     * ReOrder Array
     * Given an array of elements, reorder it in place as follow:
     * { N1, N2, N3, …, N2k } → { N1, Nk+1, N2, Nk+2, N3, Nk+3, … , Nk, N2k }
     * { N1, N2, N3, …, N2k+1 } → { N1, Nk+1, N2, Nk+2, N3, Nk+3, … , Nk, N2k, N2k+1 }
     * reverse engineering: ABCD1234 -> A1B2C3D4
     * Time = O(nlogn)
     * Space = O(n)
     */
    public int[] reorder(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        if (array.length % 2 == 0) {
            convert(array, 0, array.length - 1);
        } else { // we ignore the last one when converting
            convert(array, 0, array.length - 2);
        }
        return array;
    }

    private void convert(int[] a, int left, int right) {
        int length = right - left + 1;
        if (length <= 2) { // base case
            return;
        }
        // calculate the mid points
        // 0 1 2 3 4 5 6 7
        // l   lm  m   rm
        // 0 1 2 3 4 5 6 7 8 9
        // l   lm    m   rm
        int mid = left + length / 2;
        int leftMid = left + length / 4;
        int rightMid = left + length * 3 / 4;
        reverse(a, leftMid, mid - 1);      // I love yahoo trick is here
        reverse(a, mid, rightMid - 1);
        reverse(a, leftMid, rightMid - 1); // DE123 -> 123DE
        // leftMid - left == half of the left partition's size
        convert(a, left, left + 2 * (leftMid - left) - 1);
        convert(a, left + 2 * (leftMid - left), right);
    }

    private void reverse(int[] array, int left, int right){
        while (left < right) {
            if (array[left] != array[right]) {
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;
            }
            left++;
            right--;
        }
    }

    /**
     * All Permutations II
     * Given a string with possible duplicate characters, return a list with all permutations of the characters.
     * e.g. Set = "aba", all permutations are ["aab", "aba", "baa"]
     * Time = O(n!)
     * Space = O(n ^ 2)
     */
    public List<String> permutations(String set) {
        List<String> result = new ArrayList<>();
        if (set == null) {
            return result;
        }
        if (set.length() == 0) {
            result.add("");
            return result;
        }
        char[] chars = set.toCharArray();
        getAllPermutations(chars, 0, result);
        return result;
    }

    private void getAllPermutations(char[] chars, int index, List<String> result) {
        if (index == chars.length - 1) {
            result.add(new String(chars));
            return;
        }
        Set<Character> set = new HashSet<>();
        for (int i = index; i < chars.length; i++) {
            if (set.add(chars[i])) {
                swap(chars, index, i);
                getAllPermutations(chars, index + 1, result);
                swap(chars, index, i);
            }
        }
    }

    /**
     * Decompress String II
     * Given a string in compressed form, decompress it to the original string.
     * The adjacent repeated characters in the original string are compressed to
     * have the character followed by the number of repeated occurrences.
     * e.g. “a1c0b2c4” → “abbcccc”
     * Time = O(n * m) // m is the average amount of each character
     * Space = O(n)
     */
    public String decompressString(String input) { // in-place
        // assumptions: 1 the string is not null
        // 2 the characters used in the original string are guaranteed to be 'a' = 'z'
        // 3 there are no adjacent repeated characters with length > 9
        if (input.isEmpty()) {
            return input;
        }
        char[] chars = input.toCharArray();
        int count = 0; // calculate for resize the array
        for (int i = chars.length - 1; i > 0; i -= 2) {
            if (chars[i] != '0') {
                count += (chars[i] - '0');
            }
        }
        char[] result = new char[count];
        int fast = chars.length - 1;
        int slow = result.length - 1;
        while (fast > 0) {
            if (chars[fast] != '0') {
                count = chars[fast] - '0';
                char c = chars[fast - 1];
                for (int i = 0; i < count; i++) {
                    result[slow--] = c;
                }
            }
            fast -= 2;
        }
        return new String(result);
    }

    public String decompressString2(String input) { // using string builder
        // assumptions: 1 the string is not null
        // 2 the characters used in the original string are guaranteed to be 'a' = 'z'
        // 3 there are no adjacent repeated characters with length > 9
        if (input.isEmpty()) {
            return input;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int count = input.charAt(i + 1) - '0';
            for (int j = 0; j < count; j++) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Longest Substring Without Repeating Characters
     * Given a string, find the longest substring without any repeating characters return the length of it.
     * e.g. the longest substring without repeating letters for "bcdfbd" is "bcdf", we should return 4 in this case.
     * Time = O(n) // 2n, slow和fast最多各走一次
     * Space = O(n)
     */
    public int longestSubStringWithoutRepeat(String input) {
        if (input == null || input.length() == 0) {
            return 0;
        }
        Set <Character> set = new HashSet<>();
        int slow = 0;
        int fast = 0; // [slow, fast - 1] is the sliding window
        int max = 0;
        while (fast < input.length()) {
            if (!set.add(input.charAt(fast))) {
                set.remove(input.charAt(slow++));
            } else {
                fast++;
                max = Math.max(max, fast - slow);
            }
        }
        return max;
    }

    /**
     * All Anagrams
     * Find all occurrence of anagrams of a given string s in a given string l.
     * Return the list of starting indices.
     * e.g, sh = "ab", lo = "abcbac"
     * Time = O(n) // was O(n * m) but optimized to O(n) by the typesToMatch counter
     * Space = O(n)
     */
    public List<Integer> allAnagrams(String sh, String lo) {
        // assumption: sh is not null or empty
        // lo is not null
        List<Integer> result = new ArrayList<>();
        if (lo.isEmpty() || sh.length() > lo.length()) {
            return result;
        }
        int slow = 0;
        int fast = sh.length() - 1; // [slow, fast] is the sliding window
        Map<Character, Integer> map = new HashMap<>(); // stores the difference between sh and window
        int typesMatch = 0; // when typesMatch == map.size() means the window covers all types of character
        // pre-processing
        for (int i = 0; i < sh.length(); i++) {
            map.put(sh.charAt(i), map.getOrDefault(sh.charAt(i), 0) + 1);
        }
        for (int i = slow; i <= fast; i++) {
            char c = lo.charAt(i);
            Integer left = map.get(c);
            if (left != null) {
                map.put(c, left - 1);
                if (left == 0) {
                    typesMatch--;
                }
                if (left - 1 == 0) {
                    typesMatch++;
                }
            }
        }
        while (fast < lo.length()) {
            // check match
            if (typesMatch == map.size() && isMatch(map)) {
                // add to result
                result.add(slow);
            }
            fast++;
            // if next window valid
            if (fast < lo.length()) {
                // window add next
                char nextChar = lo.charAt(fast);
                Integer left = map.get(nextChar);
                if (left != null) {
                    map.put(nextChar, left - 1);
                    if (left == 0) {
                        typesMatch--;
                    }
                    if (left - 1 == 0) {
                        typesMatch++;
                    }
                }
                // window remove last
                char lastChar = lo.charAt(slow);
                left = map.get(lastChar);
                if (left != null) {
                    map.put(lastChar, left + 1);
                    if (left == 0) {
                        typesMatch--;
                    }
                    if (left + 1 == 0) {
                        typesMatch++;
                    }
                }
            }
            slow++;
        }
        return result;
    }

    private boolean isMatch(Map<Character, Integer> map) {
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if(entry.getValue() != 0) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        StringBasics ins = new StringBasics();
        String s = "bbbaabbbbcccctc";
        System.out.println(ins.deDuplication(s));
        System.out.println(ins.repeatDeDuplication(s));
        String s2 = "  I  Love Yahoo   ";
        System.out.println(ins.removeSpaces(s2));
        String s3 = "eggeplp";
        System.out.println(ins.reverseString2(s3));
        ins.allAnagrams("aab","ababacbbaac");
    }

    private void swap(char[] sArray, int first, int second) {
        char temp = sArray[first];
        sArray[first] = sArray[second];
        sArray[second] = temp;
    }
}
