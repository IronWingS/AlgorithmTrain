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

    /*
    获得数的最小深度：
        数的深度的定义是，根节点到叶子节点之间所有节点的个数。
        所以思路就是遍历树，当遍历到叶子节点的时候，比较一下这个叶子节点的高度和当前最小值，取最小即可。

        用递归先序遍历即可。得有一个树的高度计数器，每迭代一次，计数器的值就加1，递归结束，返回该计数器。

        比较一下返回的值和当前的值，谁小，保留谁。

        树遍历完毕，返回最小的高度。
     */
    public int minDeep(Node root) {
        if (root != null)
            return minDeepProcess(root, 1);
        return 0;
    }

    private int minDeepProcess(Node node, int depth) {
        if (node.left == null && node.right == null) {
            return depth;
        }
        int cur = Integer.MAX_VALUE;
        if (node.left != null) cur = Math.min(minDeepProcess(node.left, depth + 1), cur);
        if (node.right != null) cur = Math.min(minDeepProcess(node.right, depth + 1), cur);
        return cur;
    }


    /*
    算法的核心是树的反序遍历。
    不同的是打印值的方法。 每位都得保证为17，然后首先得打印层数乘17的空格，然后判断需要补多少个空格；
    （17 - 数字的长度 - 2） / 2 这就是需要补充的空格。
    然后就是右节点的标志位为v，左节点的标志位为^，根节点为H，左右各两个。

     首先得有一个递归遍历的方法，
     然后就是一个打印值的方法，

    程序入口 void（根节点）{
        反序遍历（根节点，H，17）
    }
    */
    public void printTree(Node root) {
        if (root == null) return;
        printInOrder(root, 1, "H", 17);
    }

    /*
    反序遍历树的方法 void（节点，树的层数，标记位，总长度）{
        递归结束条件，非空
        递归右子节点（右子节点，v，长度）
        lenm标记位加节点值
        左边空格=(总长度-lenm) / 2
        右边空格=总长度-左边空格-lenm
        result=拼接左右空格和lenm
        获得每层的空格，和result拼接
        递归左子节点（左子节点，^，长度）
    }
    */
    public void printInOrder(Node node, int layer, String to, int len) {
        if (node == null)
            return;
        printInOrder(node.right, layer + 1, "v", len);
        String val = to + node.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenL - lenM;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(layer * len) + val);
        printInOrder(node.left, layer + 1, "^", len);
    }

    /*
    获得空格的方法 sb（空格数）{
        定义StringBuffer
        foreach循环在sb中添加该数量的空格
        返回sb
    }*/
    public StringBuffer getSpace(int num) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            sb.append(" ");
        }
        return sb;
    }


    public static void main(String[] args) {
        TraverseTree tree = new TraverseTree();
        Node root = tree.createTree(7);

//        tree.preOrderTraversal(root);

        Integer a = null;

//        tree.midOrderTraversal(root);

//        tree.postOrderTraversal(root);

//        tree.midTraversalCycle(null);

//        tree.backTreeCycle1(root);

//        int deepth = tree.minDeep(root);
//        System.out.println(deepth);

        tree.printTree(root);
        System.out.println("done!");
    }
}