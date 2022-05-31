package algorithms;

import java.util.*;

public class StringBasics {

    private StringBasics() {
    }

    /**
     * Remove Certain Characters
     * Remove given characters in input string, the relative order of other characters
     * should be remained. Return the new string after deletion. Do it in place.
     * Time = O(n)
     * Space = O(n)
     */
    public String removeCertainCharacters(String input, String t) {
        // assumption: neither input nor t is null
        if (input.length() == 0 || t.length() == 0) {
            return input;
        }
        // pro-processing: put all characters from t into a set
        Deque<Character> set = new ArrayDeque<>();
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
     * Determine whether a String is a substring of another String
     * Determine if a small string is a substring of another large string.
     * Return the index of the first occurrence of the small string in the large string.
     * Return -1 if the small string is not a substring of the large string.
     * <p>
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
     * Time = O(n)
     * Space = O(n)
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
        // 虚线框框框在 a[ppl]e
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
        for (int fast = 0; fast < chars.length; fast++) {
            if (fast == chars.length - 1 || chars[fast + 1] == ' ') {
                reverse(chars, slow, fast);
                slow = fast + 2;
            }
        }
        return new String(chars);
    }

    private void reverse(char[] chars, int left, int right) {
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
     * <p>
     * Time = O(n)
     * Space = O(n)
     */
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
     * <p>
     * can optimized by rabin karp or kmp to O(n) by find all match then replace => O(n)
     * notice replace from right to left with extended space
     */
    public String replaceString(String input, String s, String t) {
        StringBuilder sb = new StringBuilder();
        int fromIndex = 0;
        // Returns the index within this string of the first occurrence of the specified substring
        int matchIndex = input.indexOf(s, fromIndex); // complexity O(m * n)
        while (matchIndex >= 0) {
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
     * Space = O(logn)
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
        int length = right - left + 1; // how many elements in the section
        if (length <= 2) { // base case
            return;
        }
        /* calculate the mid points
         * 0 1 2 3 4 5 6 7
         * l   lm  m   rm
         * 0 1 2 3 4 5 6 7 8 9
         * l   lm    m   rm    */
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

    private void reverse(int[] array, int left, int right) {
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
     * Given a String with possible duplicate characters, return a list with all permutations of the characters.
     * e.g. input = "aba", all permutations are ["aab", "aba", "baa"]
     * Time = O(n!)
     * Space = O(n ^ 2)
     */
    public List<String> permutations(String s) {
        List<String> result = new ArrayList<>();
        if (s == null) {
            return result;
        }
        if (s.length() == 0) {
            result.add("");
            return result;
        }
        char[] chars = s.toCharArray();
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
     * Compress String II
     * Given a String, replace adjacent, repeated characters with the character followed by
     * the number of repeated occurrences
     * e.g. “abbcccc” → “a1b2c4”
     */
    public String compress(final String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        char[] array = input.toCharArray();
        return encode(array);
    }

    private String encode(char[] input) {
        /* Step 1: deal with the cases where the adjacent occurrence of the letters >= 2 */
        int slow = 0;
        int fast = 0;
        int newLength = 0;
        while (fast < input.length) {
            int begin = fast;
            while (fast < input.length && input[fast] == input[begin]) {
                fast++;
            }
            input[slow++] = input[begin];
            if (fast - begin == 1) {
                newLength += 2;
            } else {
                int len = copyDigits(input, slow, fast - begin);
                slow += len;
                newLength += len + 1;
            }
        }
        /* Step 2: deal with the cases where the adjacent occurrence of the letters == 1
         * Notice: if it is required to do this in place, usually the input array is a
         * sufficient large one, you will not need to allocate a new array. */
        char[] result = new char[newLength];
        fast = slow - 1;
        slow = newLength - 1;
        while (fast >= 0) {
            if (Character.isDigit(input[fast])) {
                while (fast >= 0 && Character.isDigit(input[fast])) {
                    result[slow--] = input[fast--];
                }
            } else {
                result[slow--] = '1';
            }
            result[slow--] = input[fast--];
        }
        return new String(result);
    }

    /**
     * copy "count" as digits into "input", starting at "index"
     */
    private int copyDigits(char[] input, int index, int count) {
        int len = 0;
        for (int i = count; i > 0; i /= 10) {
            index++;
            len++;
        }
        for (int i = count; i > 0; i /= 10) {
            int digit = i % 10;
            input[--index] = (char)('0' + digit);
        }
        return len;
    }

    /**
     * Decompress String II
     * Given a String in compressed form, decompress it to the original string.
     * The adjacent repeated characters in the original string are compressed to
     * have the character followed by the number of repeated occurrences.
     * e.g. “a1c0b2c4” → “abbcccc”
     * Time = O(n * m) // m is the average amount of each character
     * Space = O(n)
     */
    public String decompressString(String input) { // in-place
        /* assumptions:
         * 1 the string is not null
         * 2 the characters used in the original string are guaranteed to be 'a' = 'z'
         * 3 there are no adjacent repeated characters with length > 9 */
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
        /* assumptions:
         * 1. the string is not null
         * 2. the characters used in the original string are guaranteed to be 'a' = 'z'
         * 3. there are no adjacent repeated characters with length > 9 */
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
        final Set<Character> set = new HashSet<>();
        int slow = 0;
        int fast = 0; // [slow, fast - 1] is the sliding window
        int max = 0;
        while (fast < input.length()) {
            if (!set.add(input.charAt(fast))) {
                // there is duplicate character, we need to move the slow pointer
                set.remove(input.charAt(slow++));
            } else {
                // there is no duplicate character, we can slide fast pointer and we
                // have a new sliding window of [slow, fast) containing all distinct characters
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
     * e.g, s = "ab", l = "abcbac"
     * Time = O(n) // was O(n * m) but optimized to O(n) by the typesToMatch counter
     * Space = O(n)
     */
    public List<Integer> allAnagrams(String s, String l) {
        // assumption: s is not null or empty, l is not null
        List<Integer> result = new ArrayList<>();
        if (l.isEmpty() || s.length() > l.length()) {
            return result;
        }
        /* this map records for each of the distinct characters in s, how many characters are needed.
         * e.g. s = "abbc", map = {'a' : 1, 'b' : 2, 'c' : 1}
         * when we get an instance of 'a' in l, we let count of 'a' decremented by 1
         * and only when the count is from 1 to 0, we have 'a' totally matched */
        Map<Character, Integer> map = initializeMap(s);
        /* typesMatch records how many distinct characters have been matched
         * only when all the distinct characters are matched, A.K.A. match == map.size(). we found an anagram */
        int typesMatch = 0;

        /* we have a sliding window of size s.length(), and since the size is fixed,
         * we only need to record the end index of the sliding window.
         * also, when move the sliding window by one step from left to right, we ONLY need to:
         * 1. remove the leftmost character at the previous sliding window
         * 2. add the rightmost character at the current sliding window */
        for (int i = 0; i < l.length(); i++) {
            // handle the new added character(rightmost) at the current sliding window
            char temp = l.charAt(i);
            Integer count = map.get(temp);
            if (count != null) {
                // the number of needed count should be --
                map.put(temp, count - 1);
                // and only when the count is from 1 to 0, we find an additional match of distinct character
                // so we have to make sure it's not added from -1 to 0
                if (count == 1) {
                    typesMatch++;
                }
            }
            // handle the leftmost character at the previous sliding window
            if ( i >= s.length()) {
                temp = l.charAt(i - s.length());
                count = map.get(temp);
                if (count != null) {
                    // the number of needed count should be ++
                    map.put(temp, count + 1);
                    // and only when the count is from 0 to 1, we are short for one match of distinct character
                    if (count == 0) {
                        typesMatch--;
                    }
                }
            }
            // for the current sliding window, if all the distinct characters are matched (the count are all zero)
            // we found a new anagram
            if (typesMatch == map.size()) {
                result.add(i - s.length() + 1);
            }
        }
        return result;
    }

    private Map<Character, Integer> initializeMap(final String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }

    private void swap(char[] sArray, int first, int second) {
        char temp = sArray[first];
        sArray[first] = sArray[second];
        sArray[second] = temp;
    }

    public static void main(String[] args) {
        StringBasics ins = new StringBasics();

        String s = "bbbaabbbbcccctc";
        System.out.println(ins.deDuplication(s));
        System.out.println(ins.repeatDeDuplication(s));
        String s2 = ins.removeSpaces("  I  Love Yahoo   ");
        System.out.println(s2);
        String s3 = ins.reverseString2("eggeplp");
        System.out.println(s3);
        List<Integer> list = ins.allAnagrams("aab", "ababacbbaac");
        System.out.println(list);
        String s4 = ins.compress("abbcccc");
        System.out.println(s4);

        System.out.println("~~~~~~~~~~~~~~~~~");
        String ss1 = "abc";
        String ss2 = "a";
        String ss3 = s2 + "bc";
        String ss4 = "a" + "bc";
        String ss5 = s3.intern();
        final String ss6 = "a";
        String ss7 = ss6 + "bc";

        System.out.println("ss1 == ss3: " + (ss1 == ss3));
        System.out.println("ss1 == ss4: " + (ss1 == ss4));
        System.out.println("ss1 == ss5: " + (ss1 == ss5));
        System.out.println("ss1 == ss7: " + (ss1 == ss7));

        String sss = "This String s is used for testing the .indexOf() method.";
        System.out.println(sss.indexOf("t"));
        System.out.println(sss.indexOf("S"));
        System.out.println(sss.indexOf(65)); // A
        System.out.println(sss.indexOf(97)); // a
        System.out.println(sss.indexOf(102)); // f
        System.out.println(sss.indexOf("i"));
        System.out.println(sss.indexOf("i", 3));
    }
}