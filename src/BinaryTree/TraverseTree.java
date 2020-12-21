package BinaryTree;

import java.util.*;

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
        if (root != null) {
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


    /*
定义方法（根结点）返回null{
	根结点非空判断
	判断是否有左子树
	如果有{
		一直遍历到最右子节点，且不能等于当前节点
		如果最右子节点的右指针指向null{
			该指针指向cur当前节点
			cur = cur.left
		}否则{
			该指针指向null
			cur = cur.right
		}
	} 否则 {
		cur = cur.right
	}
}*/

    public void morris(Node root) {
        Node cur = root;
        while (cur != null) {
            System.out.println(cur.value);
            if (cur.left != null) {
                Node tmp = cur.left;
                while (tmp.right != null && tmp.right != cur) {
                    tmp = tmp.right;
                }
                if (tmp.right == null) {
                    tmp.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    tmp.right = null;
                }
            }
            cur = cur.right;
        }

    }

    public void morris1(Node root) {
        Node cur = root;
        while (cur != null) {
            if (cur.left != null) {
                Node mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    System.out.println(cur.value);
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                // 这里就就是叶子节点
                // 如果不加else，那么所有的while循环之后，除了continu之外的值，都会被打印一遍。
                System.out.println(cur.value);
            }
            cur = cur.right;
        }
    }

    public void morrisPre(Node root) {
        if (root == null) return;
        Node cur = root;
        while (cur != null) {
            if (cur.left != null) {
                Node mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    System.out.println(cur.value);
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                System.out.println(cur.value);
            }

            cur = cur.right;
        }
    }

    public void morrisMid(Node root) {
        if (root == null) return;
        Node cur = root;
        while (cur != null) {
            if (cur.left != null) {
                Node mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            System.out.println(cur.value);
            cur = cur.right;
        }
    }

    public void morrisBak(Node root) {
        if (root == null) return;
        Node cur = root;
        while (cur != null) {
            if (cur.left != null) {
                Node mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        printEdge(root);
    }

    private void printEdge(Node head) {
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null) {
            System.out.println(cur.value);
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    private Node reverseEdge(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.right;
            head.right = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public void seriesTree(Node root, String way) {
        if (root == null) {
            return;
        }
        String s = "";
        if (way.equals("pre"))
            System.out.println(byPreOrder(root));
        if (way.equals("mid"))
            System.out.println(byMidOrder(root));
        if (way.equals("back"))
            System.out.println(byBackOrder(root));
    }

    private String byPreOrder(Node cur) {
        if (cur == null) {
            return "#!";
        }
        // 这样就相当于，每次递归都创建一个String的字符串
        String s = cur.value + "!";
        s += byPreOrder(cur.left);
        s += byPreOrder(cur.right);
        return s;
    }

    private String byMidOrder(Node cur) {
        if (cur == null) {
            return "#!";
        }
        String s = "";
        s += byMidOrder(cur.left);
        s += cur.value + "!";
        s += byMidOrder(cur.right);
        return s;
    }

    private String byBackOrder(Node cur) {
        if (cur == null)
            return "#!";
        String s = "";
        s += byBackOrder(cur.left);
        s += byBackOrder(cur.right);
        s += cur.value + "!";
        return s;
    }

    /**
     * 反向序列化，这个思路是，利用栈保存整个树，然后根据先序遍历树的顺序，将数组中的值依次填写进去。
     * 需要注意的是，遍历数组的时候，一定要考虑边界值，否则一定会出现数组越界和空栈异常。
     * 但是，还有一个做这个题更好的数据结构，我没有想到，就是队列。
     *
     * @param s
     * @return
     */
    public Node deSerilizationTree(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        String[] sarr = s.split("!");
        Node root = new Node(Integer.parseInt(sarr[0]));
        Node cur = root;
        Stack<Node> stack = new Stack<>();
        stack.push(cur);
        for (int i = 1; i < sarr.length; ) {
            if (sarr[i].equals("#")) {
                stack.pop();
                if (!stack.isEmpty()) // 这里为什么会出现空栈异常
                    cur = stack.pop();
                i += 2;
                if (i < sarr.length) { // 这里为什么会出现数组越界
                    cur.right = new Node(Integer.parseInt(sarr[i]));
                    cur = cur.right;
                    stack.push(cur);
                    i++; // 这里为什么没有考虑到入栈后还得再向后进一位
                }
            } else {
                cur.left = new Node(Integer.parseInt(sarr[i]));
                cur = cur.left;
                stack.push(cur);
                i += 1;
            }
        }
        return root;
    }

    public Node deSerilizeQueue(String s) {
        String[] sari = s.split("!");
        Queue<String> queue = new LinkedList<String>();
        for (String value : sari) {
            queue.offer(value);
        }
        return byRecursion(queue);
    }

    private Node byRecursion(Queue<String> queue) {
        String s = queue.poll();
        // 这里的断言，不知道会怎么处理，没有用过。
        assert s != null;
        if (s.equals("#")) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("a" + " !");

        Node head = new Node(Integer.parseInt(s));
        head.left = byRecursion(queue);
        head.right = byRecursion(queue);
        return head;
    }

/*
定义方法 按层遍历序列化二叉树 返回值字符串（根结点）{
	定义队列
	头节点入队
	定义一个StringBuffer
	while队列不为空，{
		临时节点 = 出队
		如果节点为空{
			sb加入 #！
		} 否则 {
			sb加入出队节点的值！
			如果节点值左子节点不为空，入队
			如果节点值右子节点不为空，入队
		}
	}
}
*/

    /**
     * 我之前的想法是，如果树的节点为空，那么也在队列中填充一个null。
     * 这样确实显得，就有点多此一举了。
     * 队列只是用来保证，按层的顺序遍历树的，所以没有必要对空子节点进行额外的处理。
     * 其次就是，如果树本身为空，那就应该返回 #！而不是一个空字符串。
     */
    public String layerSerilize(Node head) {
        if (head == null) {
            return "#！";
        }
        Queue<Node> queue = new LinkedList<>();
        String s = head.value + "!";
        queue.offer(head);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if (cur.left != null) {
                s += (cur.left.value + "!");
                queue.offer(cur.left);
            } else {
                s += "#!";
            }
            if (cur.right != null) {
                s += (cur.right.value + "!");
                queue.offer(cur.right);
            } else {
                s += "#!";
            }

        }
        return s;
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

//        tree.morrisBak(root);

//        tree.seriesTree(root, "pre");
//        Node root1 = tree.deSerilizeQueue("1!2!4!#!#!5!#!#!3!6!#!#!7!#!#!");
//        tree.printTree(root1);
        System.out.println(tree.layerSerilize(root));
        System.out.println("done!");
    }
}