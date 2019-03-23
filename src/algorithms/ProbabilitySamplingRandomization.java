package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProbabilitySamplingRandomization {
    /**
     * Perfect Shuffle
     * Given an array of integers (without any duplicates), shuffle the array
     * such that all permutations are equally likely to be generated.
     */
    public void shuffle(int[] array) {
        for (int i = 0; i < array.length; i++) {
            // Random.nextInt(x) - unifomly distributed [0, x - 1]
            Random rd = new Random();
            int j = rd.nextInt(array.length - i) + i; // 0 to deck.size - i - 1
            swap(array, i, j);
        }
    }


    /**
     * Random7 Using Random5
     * Given a random generator random5(), the return value of random5() is 0 - 4
     * with equal probability. Use random5() to implement random7().
     */
    public int random7() {
        while (true) {
            int x = random5() * 5 + random5();
            if (x < 21) {
                return x % 7;
            }
        }
    }

    /**
     * Random1000 Using Random5
     * Given a random generator random5(), the return value of random5() is 0 - 4
     * with equal probability. Use random5() to implement random1000().
     */
    public int random1000() {
        // 5 * 5 * 5 * 5 * 5 = 3125 > 1000
        int digits = (int) (Math.log(1000) / Math.log(5));
        while (true) {
            int sum = 0;
            for (int i = 0; i <= digits; i++) {
                sum = sum * 5 + random5();
            }
            if (sum < 3000) {
                return sum % 1000;
            }
        }
    }

    /**
     * Reservoir Sampling
     * Consider an unlimited flow of data elements. How do you sample one element from this flow
     * such that at any point during the processing of the flow, you can return a random element
     * from the n elements read so far.
     */
    public class ReservoirSampling {
        Integer res;
        int count;
        Random rd;

        public ReservoirSampling() {
            this.rd = new Random();
        }

        public void read(int value) {
            count++;
            if (rd.nextInt(count) == 0) {
                res = value;
            }
        }

        public Integer sample() {
            return res;
        }
    }

    // Generalized Reservoir Sampling
    public class ReservoirSampling2 {
        private final int k;
        List<Integer> res;
        int count;
        Random rd;

        public ReservoirSampling2(int k) {
            this.k = k;
            res = new ArrayList<>();
            this.rd = new Random();
        }

        public void read(int value) {
            count++;
            if (count <= k) {
                res.add(value);
            } else {
                int r = rd.nextInt(count);
                if (r < k) {
                    res.set(r, value);
                }
            }
        }

        public List<Integer> sample() {
            return res;
        }
    }

    /**
     * 95 Percentile
     * Given a list of integers representing the lengths of urls, find the 95 percentile
     * of all lengths (95% of the urls have lengths <= returned length).
     *
     * Assumptions
     * The maximum length of valid url is 4096
     * The list is not null and is not empty and does not contain null
     *
     * Time = O(n + m) // n = size of the input array, m = maximum length of URL = 4096
     * Space = O(n + m)
     */
    public int percentile95(List<Integer> lengths) {
        int[] urlCount = new int[4096 + 1]; // urlCount[i] records how many url with length i
        for (int i : lengths) {
            urlCount[i]++;
        }
        int totalSoFar = 0;
        for (int i = 0; i < urlCount.length; i++) {
            totalSoFar += urlCount[i];
            // totalSoFar = sum(counter[0...i])
            if (totalSoFar >= 0.95 * lengths.size()) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int i = random.nextInt(1);
        System.out.println(i);

    }

    private int random5() {
        Random random = new Random();
        return random.nextInt(5); // 0 - 4
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
