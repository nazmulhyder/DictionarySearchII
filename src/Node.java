public class Node {

    public int n;
    public String [] key;
    public Node[] c;
    public boolean leaf;

    public Node(int t) {
        key = new String[2 * t];
        c = new Node[2 * t];
    }
}
