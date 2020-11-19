/*
二叉树就是父节点最多只有两个子节点的树。
我这里打算使用最基础的链表来实现，
需要给定树的深度，或者其实应该给定树中包含的元素，也就是一个数组
把这些数组元素填充进去。
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// define tree's Node class
class Node {
    // a parametric structure give value
    int value;
    Node left;
    Node right;

    // a parametric structure give value
    Node(int value) {
        this.value = value;
    }
}

// main class
public class TraverseTree {

    public Node createTree(int n) {
        return process(1, n);
    }

    /*************** create a tree ***********************/
    public Node process(int i, int n) {

        // create father node with vlue i
        Node root = new Node(i);
        // judge weather i meets the conditions
        if (2 * i <= n) {
            // create left son node with value i + 1
            root.left = process(2 * i, n);
        }
        if (2 * i + 1 <= n) {
            // create right son node with value i+ 2
            root.right = process(2 * i + 1, n);
        }
        // return tree's root node
        return root;
    }

    /*************** Travser the tree use recursion ***********************/
    // function' parameter is root node
    public void preOrderTraversal(Node root) {
        // give the value to node, save root address
        Node node = root;
        // if root equals null, return
        if (root == null)
            return;
        // print root's value
        System.out.println(node.value);
        // recursion left son node
        preOrderTraversal(node.left);
        // recursion right son node
        preOrderTraversal(node.right);
    }

    // function' parameter is root node
    public void midOrderTraversal(Node root) {
        // give the value to node, save root address
        Node node = root;
        // if root equals null, return
        if (root == null)
            return;

        // recursion left son node
        midOrderTraversal(node.left);
        // print root's value
        System.out.println(node.value);
        // recursion right son node
        midOrderTraversal(node.right);
    }

    public void postOrderTraversal(Node root) {
        // give the value to node, save root address
        Node node = root;
        // if root equals null, return
        if (root == null)
            return;

        // recursion left son node
        postOrderTraversal(node.left);
        // recursion right son node
        postOrderTraversal(node.right);
        // print root's value
        System.out.println(node.value);
    }

    /*************** Travser the tree use cycle ***********************/
    //preorder traversal by cycle
    public void preOrderTravCycle(Node root) {
        // not null judgement
        if (root != null) {
            // need to use a stack
            Stack<Node> stack = new Stack<Node>();
            // push root node in stack
            stack.push(root);
            // use while , judge condition, is stack empty now?
            while (!stack.isEmpty()) {
                // pop stack head, print it's value
                Node head = stack.pop();
                System.out.println(head.value);
                // push head right son node , push left son node
                if (head.right != null) {
                    stack.push(head.right);
                }
                // if head has right son node and left son node
                if (head.left != null) {
                    stack.push(head.left);
                }

            }
        }
    }


    /**
     * mid traversal is left mid right, so left son node appear before parent node, so the key point is how to
     * save parent node's informatiron.
     * how? use a new node to save. so wo need two tmp node, one save son's information, one save parent's information.
     */

    //define method, argument(root node)
    public List<Integer> midTraversalCycle(Node root) {
        List<Integer> list = new ArrayList<>();

        //define son node cur; define parent node parent
        Node cur = root;
        Node curParent = null;

        //define stack
        Stack<Node> stack = new Stack<Node>();
        //while cur is null & stack is empty
        while (cur != null || !stack.isEmpty()) {
            //cur in stack,cur = cur.left,
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                curParent = stack.pop();
//                System.out.println(curParent.value);
                list.add(curParent.value);
                //curparent = stack.pop, print value
                //cur = curparent.right
                cur = curParent.right;
            }
        }

        System.out.println(list);

        return list;
    }


    public static void main(String[] args) {
        TraverseTree tree = new TraverseTree();
        Node root = tree.createTree(7);

//        tree.preOrderTraversal(root);

//        tree.midOrderTraversal(root);

//        tree.postOrderTraversal(root);

        tree.midTraversalCycle(null);

        System.out.println("done!");
    }
}