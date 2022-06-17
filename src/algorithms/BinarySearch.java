package algorithms;

/**
 * Binary search 关键点
 * 1. 每一轮的search space必须缩小一半
 * 2. target不能被rule out
 */
public class BinarySearch {

    /**
     * Classical Binary Search
     * Given a target integer T and an integer array A sorted in ascending order find
     * the index i such that A[i] == T or return -1 if there is no such index.
     *
     * Time = O(logn)
     * Space = O(1)
     */
    public int binarySearch(int[] array, int target) {
        /* Assumptions: there can be duplicate elements in the array.
         * Return any of the indices i such that A[i] == T */
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2; // (right + left) / 2 might cause overflow
            if (array[mid] == target) {
                return mid; // return the index
            } else if (array[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }

    /**
     * Search In Sorted Matrix I
     * Given a 2D matrix that contains integers only, which each row is sorted in ascending
     * order. The first element of next row is larger than (or equal to) the last element
     * of previous row.
     * Given a target number, returning the position that the target locates within the
     * matrix. If the target number does not exist in the matrix, return {-1, -1}.
     *
     * Time = O(log(n*m))
     * Space = O(1)
     */
    public int[] searchInSortedMatrix(int[][] matrix, int target) {
        /* Assumptions: The given matrix is not null
         * and has size of N * M, where N >= 0 and M >= 0 */
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[]{-1, -1};
        }

        int row = matrix.length;
        int col = matrix[0].length;
        // stretched to a 1D array
        int left = 0;
        int right = row * col - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int r = mid / col; // r is the row of mid
            int c = mid % col; // c is the column of mid
            if (matrix[r][c] == target) {
                return new int[]{r, c};
            } else if (matrix[r][c] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return new int[]{-1, -1};
    }

    /**
     * binary search version 2
     * Take advantage of it.
     */
    public int binarySearch2(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;
        /* This trick can avoid infinity loop
         * if left + 1 == right, mid will === left
         * then it will stop when there are two elements left */
        while (left + 1 < right) { // if left neighbors right -> terminate
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                return mid; // return the index
            } else if (array[mid] > target) {
                right = mid; // right = mid - 1? WRONG
            } else {
                left = mid; // left = mid + 1? WRONG
            }
        }

        if (array[left] == target) {
            return left;
        } else if (array[right] == target) {
            return right;
        }

        return -1;
    }

    /**
     * Closest In Sorted Array
     * Given a target integer T and an integer array A sorted in ascending order, find
     * the index i in A such that A[i] is closest to T.
     *
     * Time = O(logn)
     * Space = O(1)
     */
    public int closestElementToTarget(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;
        // do binary search to find the target or where it should at
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                // if we find the target, it is the closest
                return mid; // right = mid is also correct, right = mid - 1? WRONG
            } else if (array[mid] > target) {
                right = mid; // right = mid - 1? WRONG
            } else {
                left = mid; // left = mid + 1? OK
            }
        }

        /* Postprocessing
         * Now there will be 3 scenarios in this case:
         * 1. T < L < R
         * 2. L < T < R
         * 3. L < R < T
         * The closest one will be the one has smaller abs value after subtract by the target. */
        return Math.abs(target - array[left]) <= Math.abs(target - array[right]) ?
                left : right;
    }

    /**
     * Closest In Sorted Array by the classic binary search
     */
    public int closestElementToTarget2(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;
        // do binary search to find the target or where it should at
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                // if we find the target, it is the closest
                return mid;
            } else if (array[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        /* Now right < left and the target is not in the array, we have 3 scenarios:
         * 1. left == 0 (include only one element in array)
         * 2. right == array.length - 1
         * 3. normal right < left */
        if (left == 0) {
            return left;
        } else if (right == array.length - 1) {
            return right;
        } else {
            return Math.abs(target - array[left]) < Math.abs(target - array[right]) ? left : right;
        }
    }

    /**
     * First Occurrence
     * Given a target integer T and an integer array A sorted in ascending order, find
     * the index of the first occurrence of T in A or return -1 if there is no such index.
     *
     * Time = O(logn)
     * Space = O(1)
     */
    public int firstOccurrence(int[] array, int target) {
        /* assumption: There can be duplicate elements in the array */
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            /* If mid == target, there could be a same element on left.
             * If ask why mid but not mid +- 1, check if target could be ruled out.
             * Here both are ok, but when array[mid] = target, right can't be mid - 1,
             * since it might rule out target */
            if (array[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }

        /* Now we have two elements left, and there are 4 scenarios:
         * 1. left == right == target
         * 2. left == target, right != target
         * 3. left != target, right == target
         * 4. both left and right != target */
        if (array[left] == target) { // check array[left] first
            return left;
        } else if (array[right] == target) {
            return right;
        }

        return -1;
    }

    /**
     * First Occurrence by the classic binary search
     */
    public int firstOccurrence2(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // index of first occurrence of target <= mid
            if (array[mid] == target) {
                right = mid;
            } else if (array[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }

            // if found
            // since mid = left - 1, so left is the first occurrence of target if it equals the target
            if (array[left] == target) {
                return left;
            }
        }

        // if left > right, means not found
        return -1;
    }

    /**
     * Last Occurrence
     * Given a target integer T and an integer array A sorted in ascending order, find
     * the index of the last occurrence of T in A or return -1 if there is no such index.
     *
     * Time = O(logn)
     * Space = O(1)
     */
    public int lastOccurrence(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            // last occurrence will be mid or on the right side of mid
            if (array[mid] <= target) {
                left = mid;
            } else {
                right = mid;
            }
        }

        // check array[right] first
        if (array[right] == target) {
            return right;
        } else if (array[left] == target) {
            return left;
        }

        return -1;
    }

    /**
     * Search For a Range
     * Given an array of integers sorted in ascending order, find the starting and ending
     * position of a given target value. If the target is not found in the array, return [-1, -1].
     *
     * Time = O(log n)
     * Space = O(1)
     */
    public int[] getRangeOfSameTargetValue(int[] array, int target) {
        if (array == null || array.length == 0) {
            return new int[]{-1, -1};
        }

        // using binary search twice, find the index of the first occurrence and the last occurrence
        int left = firstOccurrence(array, target);
        int right = lastOccurrence(array, target);

        return new int[]{left, right};
    }

    /**
     * K Closest In Sorted Array
     * Given a target integer T, a non-negative integer K and an integer array A sorted
     * in ascending order. Find k elements that are the closest elements to T in A.
     *
     * Time = O(logn + k)  // can be optimized to O(logn + logk)
     * Space = O(k)
     */
    public int[] kClosestInSortedArray(int[] array, int target, int k) {
        if (array.length == 0 || k == 0) {
            return new int[0];
        }

        int[] result = new int[k];
        int left = largestSmallerOrEqual(array, target);
        int right = left + 1;

        for (int i = 0; i < k; i++) {
            if (left < 0) {
                // there is no more left element
                result[i] = array[right++];
            } else if (right > array.length - 1) {
                // there is no more right element
                result[i] = array[left--];
            } else if (Math.abs(target - array[left]) <= Math.abs(target - array[right])) {
                result[i] = array[left--];
            } else {
                result[i] = array[right++];
            }
        }

        return result;
    }

    private int largestSmallerOrEqual(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                left = mid; // right = mid => smallestLargerOrEqual
                // don't return when find left but keep shrinking
            } else if (array[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }

        // order doesn't matter
        if (array[right] <= target) {  // largestSmaller then remove equals
            return right;
        }

        if (array[left] <= target) {
            return left;
        }

        return -1;
    }

    /**
     * Smallest Element that is Larger than Target
     * Find the smallest element that is larger than a target number.
     *
     * Time = O(logn)
     * Space = O(1)
     */
    public int smallestLarger(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                left = mid; // don't return when find left but keep shrinking
            } else if (array[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }

        // post-processing
        if (array[left] > target) {
            return left;
        } else if (array[right] > target) {
            return right;
        }

        return -1;
    }

    /**
     * Given an integer dictionary A of unknown size, where the numbers in the dictionary
     * are sorted in ascending order. Determine if a given target integer T is in the
     * dictionary. Return the index of T in A, return -1 if T is not in A.
     */
    interface Dictionary {
        // the dictionary only has .get() method, no .size()
        Integer get(int index);
    }

    public int search(Dictionary dict, int target) {
        /* Assumption: dictionary.get(i) will return null if index i is out of bounds */
        if (dict == null) {
            return -1;
        }
        int left = 0;
        int right = 1; // index
        /* Find the right boundary of the binary search,
         * extends until we are sure the target is with in [left, right] range */
        while (dict.get(right) != null && dict.get(right) <= target) {
            if (dict.get(right) == target) {
                return right;
            } else {
                // since the dictionary is sorted
                // we can move left pointer to right, then double the right index
                left = right;
                right *= 2;
            }
        }

        // dict.get(right) == null || dict.get(right) > target
        // then run binary search to find the target
        return binarySearchInDict(dict, left, right, target);
    }

    private int binarySearchInDict(Dictionary dict, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // dict.get(mid) could be null
            // so can't test if (dict.get(mid) == target) return mid;
            if (dict.get(mid) == null || dict.get(mid) > target) {
                right = mid - 1;
            } else if (dict.get(mid) < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    /**
     * Search in shifted sorted array
     * Suppose an array sorted in ascending order is shifted at some pivot unknown to
     * you beforehand. (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2)
     * You are given a target value to search, if found in the array return ints index,
     * otherwise return -1. You may assume no duplicate exists in the array.
     *
     * Time = O(logn)
     * Space = O(1)
     */
    public int searchInShiftedSortedArray(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[left] <= nums[mid]) { // left to mid is ascending
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else { // mid to right is ascending
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Peak Index in a Mountain Array
     */
    public int peakIndexInMountainArray(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] <= array[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
