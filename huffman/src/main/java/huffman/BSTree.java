package huffman;

import java.util.HashMap;

/**
 * The {@code BSTree} class implements a modified version of the binary search tree made by imns1ght
 * (https://github.com/imns1ght/bs-tree) to compute the code of each char from the text.
 * 
 * @author imns1ght
 */
public class BSTree {
	private int bs_size; // Size of the tree.
	private Node bs_root; // Main node.

	/**
	 * Constructs an empty bs-tree.
	 */
	public BSTree() {
		this.bs_root = null;
		this.bs_size = 0;
	}

	/**
	 * Constructs a bs-tree containing the specified letter.
	 * 
	 * @param letter letter to add to the bs-tree.
	 */
	public BSTree(int letter) {
		this.bs_root = new Node(letter);
		this.bs_size = 1;
	}

	/**
	 * Constructs a bs-tree containing the specified node.
	 * 
	 * @param root Node that will be added as root.
	 */
	public BSTree(Node root) {
		this.bs_root = root;
		this.bs_size = 1;
	}

	/**
	 * Constructs a bs-tree containing the letters from a hash map.
	 * 
	 * @param hashMap letter that will be added to the tree.
	 */
	public BSTree(HashMap<Integer, Integer> hashMap) {
		hashMap.entrySet().forEach(entry -> {
			add(entry.getKey(), entry.getValue());
		});
		
		hashMap.clear();
	}

	/**
	 * Add the passed node to the bs-tree.
	 *
	 * @param new_node Node to add to the bs-tree.
	 * @return Reference to the added node.
	 */
	public Node add(Node new_node) {
		return add(new_node.getLetter(), new_node.getAmount());
	}

	/**
	 * Add the passed node to the bs-tree.
	 *
	 * @param letter Letter to add to the bs-tree.
	 * @param amount Amount of that letter in the text.
	 * @return Reference to the added node.
	 */
	public Node add(int letter, int amount) {
		Node new_node = new Node(letter, amount);
		Node parent = null; // Auxiliary node.

		// Travel the tree.
		for (Node i = this.getRoot(); i != null;) {
			parent = i;

			if (new_node.getLetter() < i.getLetter()) {
				i = i.getLeft();
			} else if (new_node.getLetter() > i.getLetter()) {
				i = i.getRight();
			} else {
				return i; // No modification in the tree.
			}
		}

		new_node.setParent(parent);

		// Connect nodes.
		if (parent == null) {
			this.setRoot(new_node); // empty tree.
		} else if (new_node.getLetter() < parent.getLetter()) {
			parent.setLeft(new_node);
		} else {
			parent.setRight(new_node);
		}

		this.setSize(this.getSize() + 1);

		return new_node;
	}

	/**
	 * Delete nodes in the bs-tree.
	 *
	 * @param value Value of the node that will be deleted.
	 */
	public void delete(int value) {
		Node del_node = search(value);

		if (del_node != null) {
			delete(del_node);
		}
	}

	/**
	 * Delete nodes in the bs-tree.
	 *
	 * @param node Node that will be deleted.
	 */
	public void delete(Node node) {
		if (node.getLeft() == null) {
			transplant(this, node, node.getRight()); // Case the node has no left child.
		} else if (node.getRight() == null) {
			transplant(this, node, node.getLeft()); // Case the node has no right child.
		} else { // Case the node has two children.
			Node SuccOfNode = min(node.getRight()); // Sucessor of the node.

			if (SuccOfNode.getParent() != node) {
				transplant(this, SuccOfNode, SuccOfNode.getRight());
				SuccOfNode.setRight(node.getRight());
				SuccOfNode.getRight().setParent(SuccOfNode);
			}

			transplant(this, node, SuccOfNode);
			SuccOfNode.setLeft(node.getLeft());
			SuccOfNode.getLeft().setParent(SuccOfNode);
		}

		this.setSize(this.getSize() - 1);
	}

	/**
	 * Replace one subtree as a child of its parent with another subtree.
	 *
	 * @param tree Binary Search Tree.
	 * @param u    Subtree to be replaced.
	 * @param v    Subtree that will replace the another subtree.
	 */
	private void transplant(BSTree tree, Node u, Node v) {
		if (u.getParent() == null) {
			tree.setRoot(v); // Case u is the root of the tree.
		} else if (u == u.getParent().getLeft()) {
			u.getParent().setLeft(v); // Case u is the left child.
		} else {
			u.getParent().setRight(v); // Case u is the right child.
		}

		if (v != null) {
			v.setParent(u.getParent());
		}
	}

	/**
	 * Find letter in the bs-tree.
	 *
	 * @param letter letter to find in the bs-tree.
	 * @return Node found; otherwise, return null.
	 */
	public Node search(int letter) {
		return search(this.getRoot(), letter);
	}

	/**
	 * Find value starting from the node passed by argument.
	 *
	 * @param node Node to be the "root" in the search.
	 * @return Node found; otherwise, return null.
	 */
	public Node search(Node node, int letter) {
		Node i = node;

		while (i != null && i.getLetter() != letter) {
			if (i.getLetter() > letter) {
				i = i.getLeft();
			} else {
				i = i.getRight();
			}
		}

		return i;
	}

	/**
	 * Return the node with the minimum value in the bs-tree.
	 * 
	 * @return Node with the minimum value in the bs-tree.
	 */
	public Node min() {
		Node root = this.getRoot();

		return min(root);
	}

	/**
	 * Return the node with the minimum value in the bs-tree.
	 * 
	 * @param node Root of the bs-tree.
	 * @return Node with the minimum value in the bs-tree.
	 */
	public Node min(Node node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}

		return node;
	}

	/**
	 * Return the number of nodes in the bs-tree.
	 * 
	 * @return Number of nodes in the bs-tree.
	 */
	public int getSize() {
		return bs_size;
	}

	/**
	 * Set the number of nodes in the bs-tree..
	 * 
	 * @param new_size Set the number of nodes in the bs-tree.
	 */
	private void setSize(int new_size) {
		this.bs_size = new_size;
	}

	/**
	 * Return the root of the bs-tree.
	 * 
	 * @return root of the bs-tree.
	 */
	public Node getRoot() {
		return bs_root;
	}

	/**
	 * Set the root of the bs-tree..
	 * 
	 * @param new_root Set the root of the bs-tree.
	 */
	private void setRoot(Node new_root) {
		this.bs_root = new_root;
	}

	/**
	 * Print the bs-tree inorder.
	 */
	public void print() {
		print(this.getRoot());
	}

	/**
	 * Print the bs-tree inorder starting from the node passed by argument.
	 */
	private void print(Node node) {
		if (node != null) {
			print(node.getLeft());
			System.out.println("[ " + (char) node.getLetter() + " | " + node.getAmount()
					+ " ]");
			print(node.getRight());
		}
	}
}
