import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Test2 {
    public List<String> subSets(String set) {
        // corner case
        List<String> result = new ArrayList<String>();
        if (set == null) {
            return result;
        }

        StringBuilder prefix = new StringBuilder(); // store each subsets
        HashSet<String> cSet = new HashSet<>();

        findSubsets(set, 0, cSet, prefix, result);
        return result;
    }

    private void findSubsets(String s, int index, HashSet<String> set, StringBuilder prefix, List<String> result) {
        // base case
        if (index == s.length()) {
            System.out.print("Set: ");
            for (String ss: set) {
                System.out.print(ss + " ");
            }
            System.out.println();
            if (set.add(prefix.toString())) {
                result.add(prefix.toString());
                System.out.println("Result: " + prefix.toString());
            }
            return;
        }

        prefix.append(s.charAt(index));
        findSubsets(s, index + 1, set, prefix, result);
        prefix.deleteCharAt(prefix.length() - 1);
        findSubsets(s, index + 1, set, prefix, result);
    }

    public List<String> subSets2(String set) {
        // corner case
        List<String> result = new ArrayList<String>();
        if (set == null) {
            return result;
        }

        StringBuilder prefix = new StringBuilder(); // store each subsets
        HashSet<String> cSet = new HashSet<>();

        findSubsets2(set, 0, cSet, prefix, result);
        return result;
    }

    private void findSubsets2(String s, int index, HashSet<String> set, StringBuilder prefix, List<String> result) {
        // base case
        if (index == s.length()) {
            if (set.add(prefix.toString())) {
                result.add(prefix.toString());
            }
            return;
        }

        prefix.append(s.charAt(index));
        findSubsets2(s, index + 1, set, prefix, result);
        prefix.deleteCharAt(prefix.length() - 1);
        findSubsets2(s, index + 1, set, prefix, result);
    }

    public static void main(String[] args) {
        Test2 t = new Test2();
        //t.subSets("abab");
        List<String> list = t.subSets2("abab");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
