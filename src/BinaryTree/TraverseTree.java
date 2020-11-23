package BinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// define tree's BinaryTree.Node class
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

    /*
        二叉树就是父节点最多只有两个子节点的树。
        我这里打算使用最基础的链表来实现，
        需要给定树的深度，或者其实应该给定树中包含的元素，也就是一个数组
        把这些数组元素填充进去。
    */
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

    /*
    利用循环后序遍历一棵树
    利用两个栈实现这个效果
    s1用左右中的顺序入栈，然后依次出栈再入栈s2，此时s2再出栈也是左右中
        这里就相当于是用两个栈实现了一个队列

    定义方法（参数：根节点root）返回值 List<Integer>
    root非空判断
    定义两个栈，临时节点node，List
    root赋值给node，并入s1栈
    while循环（s1）不为空
    s1出栈，存入node，然后s2入栈
    node左子节点入栈s1，node右子节点入栈s1
    s2出栈，并将数值保存到list中
*/
    public List<Integer> backTreeCycle(Node root) {
        List<Integer> list = new ArrayList<>();
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        Node node = root;
        stack1.push(node);
        while (!stack1.isEmpty()) {
            node = stack1.pop();
            stack2.push(node);
            if (node.left != null) stack1.push(node.left);
            if (node.right != null) stack1.push(node.right);
        }
        while (!stack2.isEmpty()) {
            list.add(stack2.pop().value);
        }
        System.out.println(list);
        return list;
    }

        /*
		利用一个栈实现树的后序遍历，后序遍历是左右中。
		那关键就是在遍历树的过程中，不断的找到左子节点，并保留该节点的根节点。
		然后就可以找到该节点的右子节点，然后就可以输出该根节点，这样就能达到按照左右中的顺序遍历树的效果
		也就是说，这个过程是入栈出栈交替进行的，并不是把一颗完整的树全部入栈之后，再按照特定的顺序出栈。
		这就是所谓的算法
    */

    /*
        具体的过程，也就是伪代码
        定义方法（参数：根节点root）返回值 List<Integer>
        定义返回的list
        定义栈
        定义临时节点head=root，cur为空
        head入栈
        while循环（栈不为空）
        cur指针指向栈顶元素
        判断cur节点的左子节点不为空，
            同时判断cur节点的左子节点和head节点不相等，以及cur节点的右子节点和head节点不相等。条件满足就入栈。
        判断cur节点的右子节点不为空，且不和head节点相等，满足条件就入栈，
        如果为空，cur出栈，并将该值赋给head节点，保存节点的vlalue值到list
    */
    public List<Integer> backTreeCycle1(Node root) {
        List<Integer> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Node head = root;
        Node cur = null;
        if (root != null){
            stack.push(head);
            while (!stack.isEmpty()) {
                cur = stack.peek();
                if (cur.left != null && cur.left != head && cur.right != head) {
                    stack.push(cur.left);
                } else if (cur.right != null && cur.right != head) {
                    stack.push(cur.right);
                } else {
                    head = stack.pop();
                    list.add(head.value);
                }
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

//        tree.midTraversalCycle(null);

        tree.backTreeCycle1(root);

        System.out.println("done!");
    }
}