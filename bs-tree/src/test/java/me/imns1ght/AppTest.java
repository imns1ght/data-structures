package me.imns1ght;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
//import org.junit.BeforeClass;
import org.junit.Test;

public class AppTest {
    // TODO

    @Test
    public void deleteTest() {
        BSTree<Integer> tree = new BSTree<Integer>();
        final int value = 50; 

        // Preparing the tree.
        Node<Integer> toRemove = tree.insert(value); // Node we want to remove
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);
        tree.insert(30);
        tree.insert(10);
        tree.insert(35);
        tree.insert(55);
        tree.insert(65);
        tree.insert(5);
        tree.insert(1);
        tree.inorder();

        // Before delete.
        assertTrue(tree.getSize() == 12);
        Node<Integer> result = tree.search(value);
        assertTrue(result != null && result.getValue() == value);

        tree.delete(toRemove);

        // After delete.
        tree.inorder();
        assertTrue(tree.getSize() == 11);
        result = tree.search(value);
        assertFalse(result != null && result.getValue() == value);
    }

    @Test
    public void balancedTreeTest() {
        // Preparing the tree.
        BSTree<Integer> tree = new BSTree<Integer>();
		tree.insert(50);
		tree.insert(40);
		tree.insert(70);
		Node<Integer> rm01 = tree.insert(60);
		Node<Integer> rm02 = tree.insert(55);

		// Before delete
		assertFalse(tree.isBalanced());

        tree.delete(rm02);
		tree.delete(rm01);
        
        // After delete.
		assertTrue(tree.isBalanced());
    }
}