package practice;

import java.util.ArrayList;
import java.util.List;

public class Microsoft {

    public void quickSort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }

        quickSort(a, 0, a.length - 1);
    }

    private void quickSort(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }

        // now the pivot is sorted
        int pivot = partition(a, left, right);
        // sort the left side of the pivot
        quickSort(a, left, pivot - 1);
        // sort the right side of the pivot
        quickSort(a, pivot + 1, right);
    }

    private int partition(int[] a, int left, int right) {
        int pivot = (int) (left + Math.random() * (right - left + 1));
        int pivotValue = a[pivot];
        int last = right;
        // move pivot to the last, and keep it there for comparison
        swap(a, pivot, right--);

        while (left <= right) {
            if (a[left] < pivotValue) { // find elements on left is greater than pivot
                left++;
            } else if (a[right] > pivotValue) { // find elements on right is less than pivot
                right--;
            } else {
                swap(a, left++, right--);
            }
        }

        // left on the first element that greater than pivot
        swap(a, left, last);
        return left;
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * 给定一个含有重复元素的有序数组nums，和一个数A，在有序数组中寻找数A的起止位置。如果没找到返回[-1, -1]
     *
     * e.g. Nums = [1,3,5,5,5,6,9], A = 5
     * 输出结果为[2,4]
     */
    public int[] binarySearch(int a[], int target) {
        if (a == null || a.length == 0) {
            return new int[]{-1, -1};
        }

        int left = 0;
        int right = a.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (a[mid] == target) {
                int i = mid; // go left
                int j = mid; // go right
                while (i >= 0 && a[i] == target) {
                    i--;
                }
                while (j <= a.length - 1 && a[j] == target) {
                    j++;
                }

                return new int[]{i + 1, j - 1};
            } else if (a[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new int[]{-1, -1};
    }

    /**
     * ### 英文句子换行
     * 把英文句子打印在纸上，每行有最大字符数限制，不足时换行。
     * 输入句子和每行字符数，输出一个数组，其中每个元素为本行内容。
     *
     * 句子只由“a-z.,”空格组成。其中“.,”后面有空格。有下列额外要求：
     * 一个单词只能出现在单行上，不能断开。此时需要从上一个空格提前换行。
     * 如果一行的最后是“.,”，那么这行可以超出字符数限制1个字符。
     *
     * ```
     * "The quick brown fox jumps over the lazy dog", 9
     * ["The quick", "brown fox", "jumps", "over the", "lazy dog"]
     *
     * "Hello, world. Hello Microsoft.", 12
     * ["Hello, world.", "Hello", "Microsoft."]
     *
     * "Hello, world. Hello Microsoft.", 11
     * ["Hello,", "world.", "Hello", "Microsoft."]
     */
    public List<String> printWords(String s, int limit) {
        List<String> result = new ArrayList<>();
        String[] words = s.split(" ");
        int count = 0;
        int index = 0;

        StringBuilder sb = new StringBuilder();

        while (index < words.length) {
            while (index < words.length && (words[index].length() + count < limit)) {
                sb.append(words[index]);
                count += words[index++].length();
                if (count < limit) {
                    sb.append(" ");
                }
            }

            if (sb.charAt(sb.length() - 1) == ' ') {
                sb.deleteCharAt(sb.length() - 1); // remove tailing space
            }

            // case 1: index > words
            if (index == words.length && !sb.isEmpty()) {
                result.add(sb.toString());
                break;
            }

            // case 2: index < words && sb + word > limit
            String curWord = words[index];
            int curWordLength = curWord.length();
            if ((count + curWordLength == limit + 1)
                    && ((curWord.charAt(curWordLength - 1) == '.'
                        || curWord.charAt(curWordLength - 1) == ','))) {
                sb.append(curWord);
            }

            count = 0;
            result.add(sb.toString());
            sb.setLength(0);
        }

        return result;
    }

    public static void main(String[] args) {
        Microsoft ins = new Microsoft();
        // [1,3,5,5,5,6,9] A = 5,
        int[] a = ins.binarySearch(new int[]{1,3,5,5,5,6,9}, 5);
        System.out.println(a[0] + " " + a[1]);

        String s = "The quick brown fox jumps over the lazy dog";
        List<String> list = ins.printWords(s, 9);
        System.out.println(list);
    }
}
