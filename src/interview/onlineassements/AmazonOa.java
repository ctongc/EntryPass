package interview.onlineassements;

import java.util.*;

public class AmazonOa {
    /**
     * Delivery plan
     * 给定一组delivery location的坐标, 返回离你最近的k个location的的坐标
     * Time = O(nlogk)
     * Space = O(k)
     */
    public List<List<Integer>> closestXDestinations(int numDestinations, List<List<Integer>> allLocations,
                                                    int numDeliveries) {
        List<List<Integer>> deliveryPlan = new ArrayList<>(); // stores result
        // sanity check
        if (numDestinations == 0 && numDeliveries == 0) {
            return deliveryPlan;
        }
        // PriorityQueue stores the current "numDeliveries" nearest locations
        PriorityQueue<List<Integer>> maxHeap = new PriorityQueue<>(numDeliveries,
                Comparator.comparingDouble(this::computeDistance).reversed());
        for (List<Integer> loc : allLocations) {
            if (maxHeap.size() < numDeliveries) {
                maxHeap.offer(loc);
            } else {
                // if a location to me has less distance than the farthest
                // location I need to deliver the packages to, replace
                // the farthest location in my PriorityQueue with it
                if (computeDistance(loc) < computeDistance(maxHeap.peek())) {
                    maxHeap.poll();
                    maxHeap.offer(loc);
                }
            }
        }
        // now I have my deliveryPlan in descending order by the distance
        while(!maxHeap.isEmpty()) {
            deliveryPlan.add(maxHeap.poll());
        }
        return deliveryPlan;
    }

    private double computeDistance(List<Integer> location) {
        return Math.sqrt(location.get(0) * location.get(0) + location.get(1) * location.get(1));
    }

    /**
     * 二维矩阵 从左上角出发 可以往上下左右走 碰到障碍物不能走 问到9最少要走多少步
     * given a grid in a 2-d array representation consist of 3 values: 1, 0, 9,
     * Where 1 = road, 0 = obstacle, 9 = destination. A robot starts at top left [0,0]
     * find number of steps required to find the destination.
     * Time = O(numRows * numColumns)
     * Space = O(numRows * numColumns)
     */
    public int minimumDistance(int numRows, int numColumns, List<List<Integer>> area) {
        // assume the path costs are the same
        Queue<Node> queue = new ArrayDeque<>(); // stores the place on going
        boolean[][] visited = new boolean[numRows][numColumns]; // if visited a node
        // initialize
        int minDistance = 0;
        queue.offer(new Node(0, 0));
        visited[0][0] = true;
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while(!queue.isEmpty()) {
            int size = queue.size(); // how many places we can go in this step
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll(); // the place we are dealing with now
                if (area.get(cur.row).get(cur.col) == 9) { // delivered
                    return minDistance;
                }
                // we can go four direction after check the new place is isValid
                for(int[] move : directions) {
                    int newRow = cur.row + move[0];
                    int newCol = cur.col + move[1];
                    if (isValid(newRow, newCol, area, visited)) { // up
                        visited[newRow][newCol] = true;
                        queue.offer(new Node(newRow, newCol));
                    }
                }
            }
            minDistance++;
        }
        return -1;
    }

    // check a place is valid if notOutOfBounds && is accessible && never visit
    private boolean isValid(int newRow, int newCol, List<List<Integer>> area,
                            boolean[][] visited) {
        return newRow >= 0 && newRow < area.size()
                && newCol >= 0 && newCol < area.get(0).size()
                && area.get(newRow).get(newCol) != 0
                && !visited[newRow][newCol];
    }

    private static class Node {
        final int row; // rowIndex
        final int col; // colIndex
        private Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    /**
     * 飞机里程表，input:两个list的飞机飞往和飞回两个里程表。
     * 选择飞机飞往里程, 和飞回里程, 要求里程的和不超过给定的最大里程限制, 但是两数和最大
     * 返回的是所有符合条件的列表
     */

    /**
     * 给log排序
     * 所有log都有字母和数字组成的标识符，标识符不参与排序，只是在两条log除了标识符以外完全相同的情况下
     * 要根据标识符的字典序进行排序。log有两种， 一种是单纯由字母构成，另一种是单纯由数字构成。如果是字母构成的log就根据字典序排序，
     * 如果是数字则不需要排序，按照原来的顺序放在所有字母log的下面即可
     */

    public static void main(String[] args) {
        AmazonOa ins = new AmazonOa();

        List<Integer> n1 = Arrays.asList(1, 8);
        List<Integer> n2 = Arrays.asList(2, 15);
        List<Integer> n3 = Arrays.asList(3, 9);
        List<Integer> n4 = Arrays.asList(2, 9);
        List<Integer> n5 = Arrays.asList(1, 9);
        List<Integer> n6 = Arrays.asList(0, 0);
        List<List<Integer>> input = Arrays.asList(n1,n2,n3,n4,n5,n6);
        List<List<Integer>> res = ins.closestXDestinations(input.size(), input, 5);
        for (List<Integer> l : res) {
            System.out.println(l.toString());
        }
        // List<List<Integer>> grid;
        // grid.size(); // row number
        // gird.get(0).size // col number
        //
        // row/col 0 1 2 3
        //  0      1 0 1 0
        //  1      1 1 1 0
        //  2      0 1 1 1
        //  3      0 1 9 0
        List<List<Integer>> area = Arrays.asList(
                Arrays.asList(1,0,1,0),Arrays.asList(1,1,1,0),Arrays.asList(0,1,1,1), Arrays.asList(0,1,9,0));
        int steps = ins.minimumDistance(area.size(), area.get(0).size(), area);
        System.out.println(steps);
    }
}
