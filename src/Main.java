import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String [] args)
    {
        File file = new File("/home/anik/IdeaProjects/DictionarySearch/src/WordList.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BPlusTree bPlusTree = new BPlusTree(2);
        bPlusTree.bPlusTreeCreate();
        while (scanner != null && scanner.hasNext())
            bPlusTree.bPlusTreeInsert(scanner.next());

        LinkedList<Node> visitedList = new LinkedList<>();
        bPlusTree.levelOrder(bPlusTree.getRoot(), visitedList);

        String search = "use";
        System.out.println("\nSearch: " + search);
        System.out.println(bPlusTree.bPlusTreeSearch(bPlusTree.getRoot(), search) ? "Yes" : "No");

    }
}
