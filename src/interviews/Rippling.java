package interviews;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Find which employee team has the highest average rating
 */
public class Rippling {

    static class Employee {
        final String id;
        final int rating;
        final Employee manager;
        final List<Employee> subsidiary;

        public Employee(String id, Employee manager, int rating) {
            this.id = id;
            this.rating = rating;
            this.manager = manager;
            this.subsidiary = new ArrayList<>();
        }
    }

    public Employee getMaxRatingEmployee(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return null;
        }

        List<Employee> managers = employees.stream().filter(e -> e.manager == null).toList();

        double highestRating = 0;
        Employee highestTeam = null;
        for (Employee e : managers) {
            Employee[] candidate = new Employee[1];
            double[] teamRating = new double[1];
            getRating(e, teamRating, candidate);
            if (teamRating[0] > highestRating) {
                highestRating = teamRating[0];
                highestTeam = candidate[0];
            }
        }

        return highestTeam;
    }

    // returns [ratingSum, num of employees]
    private double[] getRating(Employee e, double[] candidateRating, Employee[] candidate) {
        if (e.subsidiary.isEmpty()) {
            if (e.rating > candidateRating[0]) {
                candidateRating[0] = e.rating;
                candidate[0] = e;
            }
            return new double[]{e.rating, 1.0D};
        }

        // calculate rating of Team e
        double ratingSum = 0;
        double peopleInTeam = 0;
        for (Employee sub : e.subsidiary) {
            double[] teamInfo = getRating(sub, candidateRating, candidate);
            ratingSum += teamInfo[0];
            peopleInTeam += teamInfo[1];
        }
        ratingSum = ratingSum + e.rating;
        peopleInTeam++;
        double averageRating = ratingSum / peopleInTeam;

        // update global max
        if (averageRating > candidateRating[0]) {
            candidateRating[0] = averageRating;
            candidate[0] = e;
        }

        return new double[]{ratingSum, peopleInTeam};
    }

    /**
     * 68. Text Justification
     * https://leetcode.com/problems/text-justification/
     * Given an array of strings words and a width maxWidth, format the text such that each line
     * has exactly maxWidth characters and is fully (left and right) justified.
     *
     * You should pack your words in a greedy approach; that is, pack as many words as you can in
     * each line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.
     *
     * Extra spaces between words should be distributed as evenly as possible. If the number of spaces
     * on a line does not divide evenly between words, the empty slots on the left will be assigned
     * more spaces than the slots on the right.
     *
     * For the last line of text, it should be left-justified and no extra space is inserted between words.
     */
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int index = 0;
        StringBuilder sb = new StringBuilder();

        while (index < words.length) {
            int fromWordIndex = index;
            int wordsLength = 0; // length for only words in the line
            int lineLength = 0; // length for current line with spaces
            while (index < words.length && words[index].length() + lineLength <= maxWidth) {
                wordsLength += words[index].length();
                lineLength += words[index++].length();

                if (lineLength < maxWidth) {
                    lineLength++; // padding with at least one space
                }
            }

            int lineWordCount = index - fromWordIndex;
            if (index == words.length || lineWordCount == 1) { // last line or single word
                for (int i = fromWordIndex; i < index; i++) {
                    sb.append(words[i]).append(' ');
                }
                sb.deleteCharAt(sb.length() - 1); // remove trailing space

                // don't think from lineLength, think from how many spaces left
                for (int i = sb.length(); i < maxWidth; i++) {
                    sb.append(' ');
                }
            } else {
                int midSpace = (maxWidth - wordsLength) / (lineWordCount - 1); // fill the slot between words
                int remain = (maxWidth - wordsLength) % (lineWordCount - 1);
                for (int i = fromWordIndex; i < index; i++) {
                    sb.append(words[i]);
                    if (i < index - 1) {
                        for (int j = 0; j < midSpace; j++) {
                            sb.append(' ');
                        }
                        if (remain-- > 0) {
                            sb.append(' ');
                        }
                    }
                }
            }

            result.add(sb.toString());
            sb.setLength(0); // O(1) if the new length is less than the old one
        }

        return result;
    }

    /**
     * 839. Similar String Groups
     * https://leetcode.com/problems/similar-string-groups/
     * Two strings X and Y are similar if we can swap two letters (in different positions) of X,
     * so that it equals Y. Also two strings X and Y are similar if they are equal.
     *
     * For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats"
     * and "arts" are similar, but "star" is not similar to "tars", "rats", or "arts".
     *
     * Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.
     * Notice that "tars" and "arts" are in the same group even though they are not similar.
     * Formally, each group is such that a word is in the group if and only if it is similar to at least
     * one other word in the group.
     *
     * We are given a list strs of strings where every string in strs is an anagram of every other
     * string in strs. How many groups are there?
     */
    public int numSimilarGroups(String[] strs) {
        if (strs == null || strs.length < 2) {
            return 1;
        }

        boolean[] visited = new boolean[strs.length];
        int group = 0;
        for (int i = 0; i < strs.length; i++) {
            if (!visited[i]) {
                group++;
                visited[i] = true;
                getSameGroupStrings(strs[i], strs, visited);
            }
        }
        return group;
    }

    private void getSameGroupStrings(String s, String[] strs, boolean[] visited) {
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(s);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            for (int i = 0; i < strs.length; i++) {
                if (!visited[i] && isSameOrSimilar(cur, strs[i])) {
                    queue.offer(strs[i]);
                    visited[i] = true;
                }
            }
        }
    }

    private boolean isSameOrSimilar(String s1, String s2) {
        int diffChar = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                diffChar++;
            }
            if (diffChar > 2) {
                return false;
            }
        }
        return diffChar == 0 || diffChar == 2; // same or similar
    }
}