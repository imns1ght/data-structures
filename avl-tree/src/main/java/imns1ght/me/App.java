package imns1ght.me;

public class App {
        public static void main(String[] args) {
                AVL<Integer> avl_tree = new AVL<Integer>();

                avl_tree.insert(10);
                avl_tree.inorder();
                System.out.println();
                avl_tree.insert(20);
                avl_tree.inorder();
                System.out.println();
                avl_tree.insert(30);
                avl_tree.inorder();
                System.out.println();
                avl_tree.insert(40);
                avl_tree.inorder();
                System.out.println();
                avl_tree.insert(50);
                avl_tree.inorder();
                System.out.println();
                avl_tree.insert(25);

                avl_tree.inorder();
                System.out.println();

                avl_tree.remove(20);
                avl_tree.inorder();
                System.out.println();
                avl_tree.remove(30);
                avl_tree.inorder();
                System.out.println();

                avl_tree.insert(540);
                avl_tree.inorder();
                System.out.println();

                avl_tree.insert(920);
                avl_tree.inorder();
                System.out.println();
        }
}