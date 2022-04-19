package interview;

public class ByteDance {
    public void heapSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int n = nums.length;

        // 对array的前一半，从后往前建堆
        // 从底向上来完成，因为要保证heapify()每一个节点的时候，它的左右子树都已经是个堆了
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            // call max heapify on the reduced heap
            heapify(nums, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    void heapify(int arr[], int n, int i)
    {
        int largest = i;  // Initialize largest as root
        int l = 2*i + 1;  // left = 2*i + 1
        int r = 2*i + 2;  // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i)
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        ByteDance ins = new ByteDance();
        int[] nums = {1,4,2,8,5,7,9,10,15,3,1};

        for (int n : nums) {
            System.out.print(n + " ");
        }

        ins.heapSort(nums);

        for (int n : nums) {
            System.out.print(n + " ");
        }
    }
}
