package algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    public int key;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int key) {
        this.key = key;
    }
}

public class BinaryTreeAndBst {

    /**
     * in-order traversal of a given binary tree
     * pre-order traversal of a given binary tree
     * post-order traversal of a given binary tree
     * return the list of keys of each node in the tree as it is in-order traversed.
     */
    public List<Integer> treeTraversal(TreeNode root) {
        List<Integer> inOrderList = new ArrayList<>();
        List<Integer> preOrderList = new ArrayList<>();
        List<Integer> postOrderList = new ArrayList<>();

        if (root == null) {
            return new ArrayList<>();
        }

        inOrderTraversal(root, inOrderList);
        preOrderTraversal(root, preOrderList);
        postOrderTraversal(root, postOrderList);

        return inOrderList;
        // return preOrderList;
        // return postOrderList;
    }

    // node pre 左右
    // 自己 左 右
    public void preOrderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        list.add(root.key);
        preOrderTraversal(root.left, list);
        preOrderTraversal(root.right, list);
    }

    // node in 左右
    // 左 自己 右
    public void inOrderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        inOrderTraversal(root.left, list);
        list.add(root.key);
        inOrderTraversal(root.right, list);
    }

    // node post 左右
    // 左 右 自己
    public void postOrderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        preOrderTraversal(root.left, list);
        preOrderTraversal(root.right, list);
        list.add(root.key);
    }

    /**
     * iterative, pre-order traversal of a given binary tree
     * return the list of keys of each node in the tree as it is pre-order traversed.
     */
    public List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> preOrderList = new ArrayList<>();
        if (root == null) {
            return preOrderList;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offerFirst(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pollFirst();
            // the root is the top element
            // once the root is traversed, we can print it directly
            // and don't need to store it in the stack any more
            preOrderList.add(cur.key); // print
            // traverse left sub first
            // so the right sub should be retained in the stack before the left sub is done
            if (cur.right != null) {
                stack.offerFirst(cur.right);
            }
            if (cur.left != null) {
                stack.offerFirst(cur.left);
            }
        }
        return preOrderList;
    }

    /**
     * iterative, in-order traversal of a given binary tree
     * return the list of keys of each node in the tree as it is in-order traversed.
     *
     * 用左斜的斜线，向左一路撸到底
     */
    public List<Integer> inOrderTraversal(TreeNode root) {
        List<Integer> inOrderList = new ArrayList<>();
        if (root == null) {
            return inOrderList;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root; // helper
        while (cur != null || !stack.isEmpty()) { // 3个traversal里唯一需要判断cur的
            // always try go to the left side to see if there is any node
            // should be traversed before the cur node,
            // cur node is stored on stack since it has not been traversed yet
            if (cur != null) {
                // 一路向左走到底
                stack.offerFirst(cur);
                cur = cur.left;
            } else {
                // if can not go left, meaning all the nodes on the left side of
                // the top node on stack have been traversed, the next traversing
                // node should be the top node on stack
                cur = stack.pollFirst();
                inOrderList.add(cur.key); // print
                cur = cur.right; // 向右走一步，再向左走到底
            }
        }
        return inOrderList;
    }

    /**
     * maintain a previous Node to recode the previous visiting node on the traversing path
     * 第三次看见一个结点才打印
     */
    public List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> postOrderList = new ArrayList<>();
        if (root == null) {
            return postOrderList;
        }
        // store the nodes to be expanded later
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offerFirst(root);
        TreeNode pre = null; // record the previous node to determine the direction
        while (!stack.isEmpty()) {
            TreeNode cur = stack.peekFirst(); // don't poll it now
            // if we are going down, either left/right direction
            if (pre == null || cur == pre.left || cur == pre.right) {
                // if we can still going down, go left first
                if (cur.left != null) {
                    stack.offerFirst(cur.left);
                } else if (cur.right != null) {
                    stack.offerFirst(cur.right);
                } else {
                    // if we can't, cur is on the leaf
                    postOrderList.add(cur.key);
                    stack.pollFirst();
                }
            } else if (pre == cur.right || pre == cur.left && cur.right == null) {
                // if we are going up from the right or
                // going up from the left side but can't go to the right side after that
                postOrderList.add(cur.key);
                stack.pollFirst();
            } else {
                // otherwise, we are going up from the left side and
                // we can go to the right side after that
                stack.offerFirst(cur.right);
            }
            pre = cur; // move pre to cur
        }
        return postOrderList;
    }

    /**
     * Height of Binary Tree
     * Find the height of binary tree.
     * Time = O(n)  // n is the total number in the tree
     * Space = O(height)  // worst case O(n)
     */
    public int getHeight(TreeNode root) {
        if (root == null) { // base case
            return 0;
        }
        // post-order 把value从下传递
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    /**
     * Check If Binary Tree Is Balanced
     * Check if a given binary tree is balanced.
     * A balanced binary tree is one in which the depths of every node’s left and right subtree differ by at most 1.
     * 只把value从下往上传递
     * 1 问左要值 问右要值
     * 2 在当前层计算
     * 3 返回给parent
     * Height = log(n) levels
     * each level time = O(n)
     * This is not a optimal solution !!
     * Time = O(n log n)
     * Space = O(log n) // worst case, since it will return earlier when not balanced
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) { // base case
            return true;
        }
        // post-order traversal
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }

    /**
     * optimal solution
     * Time = O(n)
     * Space = O(height)
     */
    public boolean isBalanced2(TreeNode root) {
        if (root == null) { // base case
            return true;
        }
        // use -1 to denote the tree is not balanced
        // >= 0 value means the tree is balanced and it is the height of the tree
        return getHeight2(root) >= 0;
    }

    private int getHeight2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight2(root.left);
        // if left subtree is already not balanced, we do not need to continue, return -1 directly
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = getHeight2(root.right);
        if (rightHeight == -1) {
            return -1;
        }
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Check if a given binary tree is symmetric.
     *              10
     *           5a | 5b
     *        1a 3a | 3b 1b
     * 2a 4a  6a 8a | 8b 6b  4b 2b
     * case 1 left == null && right == null
     * case 2 left == null && right != null
     * case 3 left != null && right == null
     * case 4 left != null && right != null
     * Time = O(n)
     * Space = O(height)
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode left, TreeNode right) {
        // post-order traversal
        if (left == null && right == null) {
            // case 1
            return true;
        } else if (left == null || right == null) {
            // case 2 3
            return false;
        } else if (left.key != right.key) {
            // case 4
            return false;
        }
        // 注意对称轴的位置 是左的左和右的右 左的右和右的左
        return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
    }

    /**
     * Tweaked Identical Binary Trees
     * Determine whether two given binary trees are identical assuming any number of ‘tweak’s are allowed.
     * A tweak is defined as a swap of the children of one node in the tree.
     * Time = O(n ^ 2)  // Quadra tree = 4 ^ (log_2(n))
     * Space = O(height)
     */
    public boolean isTweakedIdentical(TreeNode one, TreeNode two) {
        if (one == null && two == null) {
            return true;
        } else if (one == null || two == null) {
            return false;
        } else if (one.key != two.key) {
            return false;
        }
        return isTweakedIdentical(one.left, two.left) && isTweakedIdentical(one.right, two.right) // case 1
                || isTweakedIdentical(one.left, two.right) && isTweakedIdentical(one.right, two.left);  // case 2
    }

    /**
     * Is Binary Search Tree Or Not
     * Determine if a given binary tree is binary search tree.
     *
     * Assumption:
     * 1 the keys stored in the binary search tree with in (Integer.MIN_VALUE, Integer.MAX_VALUE).
     * 2 no duplicate keys
     * Time = O(n)
     * Space = O(height)
     */
    public boolean isBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBST(TreeNode root, int min, int max) {
        // 把value先从上往下传递，再从下往上传递
        if (root == null) {
            return true;
        }
        // pre-order traversal
        // 当前层只关心当前层, 不要管其它层
        if (root.key <= min || root.key >= max) {
            return false;
        }
        // 越往左越小, 越往右越大
        // 往左走, Node值域 (not change, 当前root的值)
        // 往右走, Node值域 (当前root的值, no change)
        return isBST(root.left, min, root.key) && isBST(root.right, root.key, max);
    }

    /**
     * Get Keys In Binary Search Tree In Given Range
     * Get the list of keys in a given binary search tree in a given range[min, max] in ascending order
     * both min and max are inclusive.
     * Time = O(n) // worst case, better answer O(height + # of nodes in the range of [min, max])
     * Space = O(height)
     */
    public List<Integer> getRange(TreeNode root, int min, int max) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        getRange(list, root, min, max);
        return list;
    }

    private void getRange(List<Integer> list, TreeNode root, int min, int max) {
        // in-order traversal
        if (root == null) {
            return;
        }
        // only when root.key > min, we should traverse the left subtree
        if (root.key > min) {
            getRange(list, root.left, min, max);
        }
        // determine if root should be stored
        if (root.key >= min && root.key <= max) {
            list.add(root.key);
        }
        // only when root.key < max, we should traverse the right subtree
        if (root.key < max) {
            getRange(list, root.right, min, max);
        }
    }

    /**
     * Search In Binary Search Tree
     * Find the target key K in the given binary search tree, return the node
     * that contains the key if K is found, otherwise return null.
     * recursive
     * Time = O(height)
     * Space = O(height)
     */
    public TreeNode searchInBst(TreeNode root, int target) {
        // process root && terminate condition
        if (root == null || root.key == target) {
            return root;
        }

        // check left node if target less than root.key
        if (target < root.key) {
            return searchInBst(root.left, target);
        } else { // check right node if target greater than root.key
            return searchInBst(root.right, target);
        }
    }

    /**
     * iterative
     * Time = O(height)
     * Space = O(1)
     */
    public TreeNode searchInBst2(TreeNode root, int target) {
        while (root != null && root.key != target) { // terminate condition
            if (target < root.key) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        // root == null || root.key == target
        return root;
    }

    /**
     * Insert In Binary Search Tree
     * Insert a key in a binary search tree if the binary search tree does not already contain the key.
     * Return the root of the binary search tree.
     * insert只管insert 不要管调整
     * insert不是在找到坑的那一步插入，而是返回到父节点插入
     * Time = O(height)
     * Space = O(height)
     */
    public TreeNode insertInBst(TreeNode root, int key) {
        if (root == null) {
            return new TreeNode(key);
        }
        if (root.key < key) {
            root.right = insertInBst(root.right, key);
        } else if (root.key > key) {
            root.left = insertInBst(root.left, key);
        }
        // 注意这里不是return null, 不然会被剪枝
        return root;
    }

    /**
     * recursive and remove redundant operation
     */
    public TreeNode insertInBst2(TreeNode root, int key) {
        if (root == null) {
            return new TreeNode(key);
        }
        insert(root, key);
        return root;
    }

    private void insert(TreeNode root, int key) {
        if (root.key == key) {
            return;
        } else if (key < root.key) {
            if (root.left == null) {
                root.left = new TreeNode(key);
            } else {
                insert(root.left, key);
            }
        } else {
            if (root.right == null) {
                root.right = new TreeNode(key);
            } else {
                insert(root.right, key);
            }
        }
    }

    /**
     * iterative
     * Time = O(height)
     * Space = O(1)
     */
    public TreeNode insertInBst3(TreeNode root, int key) {
        TreeNode newNode = new TreeNode(key);
        if (root == null) {
            return newNode;
        }
        TreeNode cur = root;
        while (cur.key != key) {
            if (cur.key > key) {
                if (cur.left != null) {
                    cur = cur.left;
                } else {
                    cur.left = newNode;
                    break;
                }
            } else {
                if (cur.right != null) {
                    cur = cur.right;
                } else {
                    cur.right = newNode;
                    break;
                }
            }
        }
        return root;
    }

    public TreeNode insertInBst4(TreeNode root, int key) {
        if (root == null) {
            return new TreeNode(key);
        }
        TreeNode cur = root;
        TreeNode parent = null;
        while (cur != null) {
            if (key > cur.key) {
                parent = cur;
                cur = cur.right;
            } else if (key < cur.key) {
                parent = cur;
                cur = cur.left;
            } else {
                return root;
            }
        }
        TreeNode newNode = new TreeNode(key);
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        return root;
    }

    /**
     * Delete the target key K in the given binary search tree if the binary search tree contains K.
     * Return the root of the binary search tree.
     * Time =
     * Space =
     */
    public TreeNode deleteInBst(TreeNode root, int target) {
        if (root == null) {
            return root;
        }
        // find target node
        if (root.key > target) {
            root.left = deleteInBst(root.left, target);
            return root;
        } else if (root.key < target){
            root.right = deleteInBst(root.right, target);
            return root;
        }
        // guarantee root != null && root.key == target
        if (root.left == null) { // case 1 && 2
            return root.right;
        } else if (root.right == null) { // case 3
            return root.left;
        }
        // guarantee root.left != null && root.right != null
        // case 4.1
        if (root.right.left == null) {
            root.right.left = root.left;
            return root.right;
        }
        // case 4.2
        // 1 find and delete smallest node in root.right
        TreeNode smallest = deleteSmallestInRight(root.right);
        // 2 connect the smallest node with root.left and root.right
        smallest.left = root.left;
        smallest.right = root.right;
        // 3 return the smallest node
        return smallest;
    }

    /**
     * return the node that replace target */
    private TreeNode deleteSmallestInRight(TreeNode root) {
        TreeNode prev = root;
        root = root.left;
        while (root.left != null) {
            prev = root;
            root = root.left;
        }
        // root.left = null, root is the smallest one
        prev.left = prev.left.right;
        return root;
    }

    /**
     * Given a binary tree, find the largest subtree which is a Binary Search Tree (BST),
     * where largest means subtree with largest number of nodes in it.
     * Time = O(n)
     * Space = O(height)
     */
    public int largestBSTSubtree(TreeNode root) {
        int[] largest = new int[1];
        findLargest(root, largest);
        return largest[0];
    }

    private int[] findLargest(TreeNode root, int[] largest) {
        int[] result = new int[3]; // number of nodes in the subtree (inclusive), min value boundary, max value boundary
        if (root == null) {
            return result;
        }
        int[] leftResult = findLargest(root.left, largest);
        int[] rightResult = findLargest(root.right, largest);
        if (leftResult[0] == -1 || rightResult[0] == -1
                || (root.left != null && root.key <= leftResult[2])  // 比左孩子的最大值要小
                || (root.right != null && root.key >= rightResult[1])) { // 比右孩子的最小值要大
            result[0] = -1;
        } else {
            result[0] = 1 + leftResult[0] + rightResult[0];
            result[1] = root.left == null ? root.key : leftResult[1];
            result[2] = root.right == null ? root.key : rightResult[2];
            largest[0] = Math.max(largest[0], 1 + leftResult[0] + rightResult[0]);
        }
        return result;
    }

    public static void main(String[] args) {
        BinaryTreeAndBst tree = new BinaryTreeAndBst();
        //[1, 2, 3, #, #, 4]
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        t1.left = t2;
        t1.right = t3;
        t3.left = t4;
        tree.treeTraversal(t1);
    }
}
