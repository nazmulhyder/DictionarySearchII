import java.util.LinkedList;
import java.util.Queue;

public class BPlusTree {

    private Node root;
    private int t;

    public BPlusTree(int t) {
        this.t = t;
    }

    public Node getRoot() {
        return root;
    }

    public void bPlusTreeCreate() {
        Node x = new Node(t);
        x.leaf = true;
        x.n = 0;
        root = x;
    }

    private void bPlusTreeSplitChild(Node x, int i) {
        Node z = new Node(t);
        Node y = x.c[i];
        z.leaf = y.leaf;
        z.n = y.n - t;

        if (y.leaf) {
            for (int j = 0; j < t; j++)
                z.key[j] = y.key[j + t];
        }
        else {
            for (int j = 0; j < t - 1; j++)
                z.key[j] = y.key[j + t];
        }

        if (!y.leaf) {
            for (int j = 0; j < t; j++)
                z.c[j] = y.c[j + t];
        }

        if (y.leaf)
            y.n = t;
        else y.n = t - 1;

        for (int j = x.n; j >= i + 1; j--)
            x.c[j + 1] = x.c[j];

        x.c[i + 1] = z;

        for (int j = x.n - 1; j >= i; j--)
            x.key[j + 1] = x.key[j];

        if (y.leaf) {
            x.key[i] = y.key[t];
            x.n++;
        } else {
            x.key[i] = y.key[t - 1];
            x.n++;
        }
    }

    private void bPlusTreeInsertNonfull(Node x, String k) {
        int i = x.n - 1;

        if (x.leaf) {
            while (i >= 0 && compare(k,x.key[i]) < 0) {
                x.key[i + 1] = x.key[i];
                i--;
            }
            x.key[i + 1] = k;
            x.n++;
        }
        else {
            while (i >= 0 && compare(k, x.key[i]) < 0)
                i--;
            i++;
            if ((x.c[i].n == 2 * t && x.c[i].leaf) || (x.c[i].n == 2 * t - 1 && !x.c[i].leaf)) {
                bPlusTreeSplitChild(x, i);
                if (compare(k, x.key[i]) > 0)
                    i++;
            }
            bPlusTreeInsertNonfull(x.c[i], k);
        }
    }

    public void bPlusTreeInsert(String k) {
        Node r = root;

        if ((r.n == 2 * t && r.leaf) || (!r.leaf && r.n == 2 * t - 1)) {
            Node s = new Node(t);
            root = s;
            s.leaf = false;
            s.n = 0;
            s.c[0] = r;
            bPlusTreeSplitChild(s, 0);
            bPlusTreeInsertNonfull(s, k);
        }
        else bPlusTreeInsertNonfull(r, k);
    }

    public boolean bPlusTreeSearch(Node x, String k) {
        while (!x.leaf) {

            System.out.print("( ");
            for (int j = 0; j < x.n; j++)
                System.out.print(x.key[j] + " ");
            System.out.println(") ");

            int i = 0;
            while (i < x.n && compare(k, x.key[i]) >= 0)
                i++;
            x = x.c[i];
        }
        int i = 0;
        while (i < x.n && compare(k, x.key[i]) > 0)
            i++;

        return (i < x.n && k.equals(x.key[i]));
    }

    private int compare(String firstWord, String secondWord) {
        char[] firstWordChars = firstWord.toCharArray();
        char[] secondWordChars = secondWord.toCharArray();
        int i = 0;
        char a, b;
        while (i < Math.max(firstWordChars.length, secondWordChars.length)) {
            if (i >= firstWordChars.length)
                a = ' ';
            else a = firstWordChars[i];
            if (i >= secondWordChars.length)
                b = ' ';
            else b = secondWordChars[i];

            if (a < b)
                return -1;
            else if (a == b)
                i++;
            else return 1;
        }
        return 0;
    }

    private boolean checkVisit(Node node, LinkedList<Node> visitedList) {
        return visitedList.contains(node);
    }

    private void visit(Node node, LinkedList<Node> visitedList) {
        visitedList.add(node);
    }

    public void levelOrder(Node root, LinkedList<Node> visitedList) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (queue.peek() != null) {
            Node node = queue.remove();
            if (!checkVisit(node, visitedList)) {
                if (!node.leaf) {
                    System.out.print("( ");
                    for (int i = 0; i < node.n; i++)
                        System.out.print(node.key[i] + " ");
                    System.out.print(") ");

                    System.out.print("--> ");
                    for (int j = 0; j <= node.n; j++) {
                        System.out.print("( ");
                        for (int k = 0; k < node.c[j].n; k++)
                            System.out.print(node.c[j].key[k] + " ");
                        System.out.print(") ");
                    }
                    System.out.println();
                }
                visit(node, visitedList);

                if (!node.leaf) {
                    for (int i = 0; i <= node.n; i++)
                        queue.add(node.c[i]);

                }
            }
        }

    }
}
