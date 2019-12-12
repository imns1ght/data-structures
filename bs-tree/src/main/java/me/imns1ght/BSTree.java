package me.imns1ght;

public class BSTree<T extends Comparable<T>> {
	private int bs_size; // Size of the tree.
	private Node<T> bs_root; // Main node.

	//////////////////////////////////////////////////////////////
	// Constructors
	//////////////////////////////////////////////////////////////

	/**
	 * Constructs an empty bs-tree.
	 */
	public BSTree() {
		this.bs_root = null;
		this.bs_size = 0;
	}

	/**
	 * Constructs a bs-tree containing the specified value.
	 * 
	 * @param value Value to add to the bs-tree.
	 */
	public BSTree(T value) {
		this.bs_root = new Node<T>(value);
		this.bs_size = 1;
	}

	/**
	 * Constructs a bs-tree containing the specified node.
	 * 
	 * @param root Node that will be added as root.
	 */
	public BSTree(Node<T> root) {
		this.bs_root = root;
		this.bs_size = 1;
	}

	//////////////////////////////////////////////////////////////
	// Gets/Sets
	//////////////////////////////////////////////////////////////

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
	public Node<T> getRoot() {
		return bs_root;
	}

	/**
	 * Set the root of the bs-tree..
	 * 
	 * @param new_root Set the root of the bs-tree.
	 */
	private void setRoot(Node<T> new_root) {
		this.bs_root = new_root;
	}

	public int getHeight(Node<T> node) {
		if (node == null) {
			return 0;
		}

		return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
	}

	//////////////////////////////////////////////////////////////
	// Search
	//////////////////////////////////////////////////////////////

	/**
	 * Return the node with the minimum value in the bs-tree.
	 * 
	 * @return Node with the minimum value in the bs-tree.
	 */
	public Node<T> min() {
		Node<T> root = this.getRoot();

		return min(root);
	}

	/**
	 * Return the node with the minimum value in the bs-tree.
	 * 
	 * @param node Root of the bs-tree.
	 * @return Node with the minimum value in the bs-tree.
	 */
	public Node<T> min(Node<T> node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}

		return node;
	}

	/**
	 * Return the node with the maximum value in the bs-tree.
	 * 
	 * @return Node with the maximum value in the bs-tree.
	 */
	public Node<T> max() {
		Node<T> root = this.getRoot();

		return max(root);
	}

	/**
	 * Return the node with the maximum value in the bs-tree.
	 * 
	 * @param node Root of the bs-tree.
	 * @return Node with the maximum value in the bs-tree.
	 */
	public Node<T> max(Node<T> node) {
		while (node.getRight() != null) {
			node = node.getRight();
		}

		return node;
	}

	/**
	 * Find the successor of the node passed by argument.
	 * 
	 * @param node Node to find by the successor.
	 * @return Successor node.
	 */
	public Node<T> successor(Node<T> node) {
		if (node.getRight() != null) {
			return min(node.getRight());
		}

		Node<T> parent = node.getParent();

		while (parent != null && node == parent.getRight()) {
			node = parent;
			parent = parent.getParent();
		}

		return parent;
	}

	/**
	 * Find the predecessor of the node passed by argument.
	 * 
	 * @param node Node to find by the predecessor.
	 * @return Predecessor node.
	 */
	public Node<T> predecessor(Node<T> node) {
		if (node.getLeft() != null) {
			return max(node.getLeft());
		}

		Node<T> parent = node.getParent();

		while (parent != null && node == parent.getLeft()) {
			node = parent;
			parent = parent.getParent();
		}

		return parent;
	}

	/**
	 * Find value in the bs-tree.
	 * 
	 * @param value Value to find in the bs-tree.
	 * @return Node found; otherwise, return null.
	 */
	public Node<T> search(T value) {
		return search(this.getRoot(), value);
	}

	/**
	 * Find value starting from the node passed by argument.
	 * 
	 * @param node Node to be the "root" in the search.
	 * @return Node found; otherwise, return null.
	 */
	public Node<T> search(Node<T> node, T value) {
		Node<T> i = node;

		while (i != null && i.getValue() != value) {
			if (i.getValue().compareTo(value) > 0) {
				i = i.getLeft();
			} else {
				i = i.getRight();
			}
		}

		return i;
	}

	//////////////////////////////////////////////////////////////
	// Modify
	//////////////////////////////////////////////////////////////

	/**
	 * Add the passed value to the bs-tree.
	 * 
	 * @param value Value to add to the bs-tree.
	 * @return Reference to the added node; otherwise, if it already exists, a reference to the
	 *         existing node with the same value.
	 */
	public Node<T> insert(T value) {
		Node<T> new_node = new Node<T>(value);

		return insert(new_node);
	}

	/**
	 * Add the passed node to the bs-tree.
	 * 
	 * @param new_node Node to add to the bs-tree.
	 * @return Reference to the added node; otherwise, if it already exists, a reference to the
	 *         existing node with the same value.
	 */
	public Node<T> insert(Node<T> new_node) {
		Node<T> parent = null; // Auxiliary node.

		// Travel the tree.
		for (Node<T> i = this.getRoot(); i != null;) {
			parent = i;

			if (new_node.getValue().compareTo(i.getValue()) < 0) {
				i = i.getLeft();
			} else if (new_node.getValue().compareTo(i.getValue()) > 0) {
				i = i.getRight();
			} else {
				return i; // No modification in the tree.
			}
		}

		new_node.setParent(parent);

		// Connect nodes.
		if (parent == null) {
			this.setRoot(new_node); // empty tree.
		} else if (new_node.getValue().compareTo(parent.getValue()) < 0) {
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
	public void delete(T value) {
		Node<T> del_node = search(value);

		if (del_node != null) {
			delete(del_node);
		}
	}

	/**
	 * Delete nodes in the bs-tree.
	 * 
	 * @param node Node that will be deleted.
	 */
	public void delete(Node<T> node) {
		if (node.getLeft() == null) {
			transplant(this, node, node.getRight()); // Case the node has no left child.
		} else if (node.getRight() == null) {
			transplant(this, node, node.getLeft()); // Case the node has no right child.
		} else { // Case the node has two children.
			Node<T> SuccOfNode = min(node.getRight()); // Sucessor of the node.

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
	private void transplant(BSTree<T> tree, Node<T> u, Node<T> v) {
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

	//////////////////////////////////////////////////////////////
	// Print
	//////////////////////////////////////////////////////////////

	/**
	 * Print the bs-tree inorder.
	 */
	public void inorder() {
		inorder(this.getRoot());
	}

	/**
	 * Print the bs-tree inorder starting from the node passed by argument.
	 */
	public void inorder(Node<T> node) {
		if (node != null) {
			inorder(node.getLeft());
			System.out.print(node.getValue() + " ");
			inorder(node.getRight());
		}
	}

	/**
	 * Print the bs-tree preorder.
	 */
	public void preorder() {
		preorder(this.getRoot());
	}

	/**
	 * Print the bs-tree preorder starting from the node passed by argument.
	 */
	public void preorder(Node<T> node) {
		if (node != null) {
			System.out.print(node.getValue() + " ");
			preorder(node.getLeft());
			preorder(node.getRight());
		}
	}

	/**
	 * Print the bs-tree postorder.
	 */
	public void postorder() {
		postorder(this.getRoot());
	}

	/**
	 * Print the bs-tree postorder starting from the node passed by argument.
	 */
	public void postorder(Node<T> node) {
		if (node != null) {
			postorder(node.getLeft());
			postorder(node.getRight());
			System.out.print(node.getValue() + " ");
		}
	}

	//////////////////////////////////////////////////////////////
	// Misc
	//////////////////////////////////////////////////////////////

	public boolean isBalanced() {
		return isBalanced(this.getRoot());
	}

	public boolean isBalanced(Node<T> node) {
		if (node == null) {
			return true;
		}

		int lth = getHeight(node.getLeft());
		int rth = getHeight(node.getRight());

		if (Math.abs(lth - rth) <= 1) {
			if (isBalanced(node.getLeft()) && isBalanced(node.getRight())) {
				return true;
			}
		}

		return false;
	}
}
