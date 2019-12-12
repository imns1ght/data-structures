package imns1ght.me;

public class AVL<T extends Comparable<T>> {
	private int t_size; // Size of the tree.
	private Node<T> t_root; // Main node.

	//////////////////////////////////////////////////////////////
	// Constructors
	//////////////////////////////////////////////////////////////

	/**
	 * Constructs an empty bs-tree.
	 */
	public AVL() {
		this.t_root = null;
		this.t_size = 0;
	}

	/**
	 * Constructs a bs-tree containing the specified value.
	 * 
	 * @param value Value to add to the bs-tree.
	 */
	public AVL(T value) {
		this.t_root = new Node<T>(value);
		this.t_size = 1;
	}

	/**
	 * Constructs a bs-tree containing the specified node.
	 * 
	 * @param root Node that will be added as root.
	 */
	public AVL(Node<T> root) {
		this.t_root = root;
		this.t_size = 1;
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
		return t_size;
	}

	/**
	 * Return the root of the bs-tree.
	 * 
	 * @return root of the bs-tree.
	 */
	public Node<T> getRoot() {
		return t_root;
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
	public void insert(T value) {
		Node<T> new_node = new Node<T>(value);

		this.t_root = insert(new_node, this.t_root);
	}

	/**
	 * Add the passed node to the bs-tree.
	 * 
	 * @param new_node Node to add to the bs-tree.
	 * @return Reference to the added node; otherwise, if it already exists, a reference to the
	 *         existing node with the same value.
	 */
	private Node<T> insert(Node<T> new_node, Node<T> parent) {
		if (parent == null) {
			return new_node;
		} else if (new_node.getValue() == parent.getValue()) {
			parent.setValue(new_node.getValue());
			return parent;
		} else if (new_node.getValue().compareTo(parent.getValue()) < 0) {
			parent.setLeft(insert(new_node, parent.getLeft()));
		} else {
			parent.setRight(insert(new_node, parent.getRight()));
		}

		return balance(parent);
	}

	private Node<T> balance(Node<T> parent) {
		if (parent == null) {
			return null;
		}

		if (parent.getBalanceFactor() >= -1 && parent.getBalanceFactor() <= 1) {
			return parent;
		}

		if (parent.getBalanceFactor() > 1) {
			// L
			if (parent.getLeft().getBalanceFactor() > 0) {
				// LL
				return rotateRight(parent);
			} else {
				// LR
				parent.setLeft(rotateLeft(parent.getLeft()));
				return rotateRight(parent);
			}
		} else {
			// R
			if (parent.getRight().getBalanceFactor() < 0) {
				// RR
				return rotateLeft(parent);
			} else {
				// RL
				parent.setRight(rotateRight(parent.getRight()));
				return rotateLeft(parent);
			}
		}
	}

	public void remove(T value) {
		Node<T> del_node = new Node<T>(value);

		if (this.t_root != null) {
			this.t_root = remove(del_node, this.t_root);
		}
	}

	private Node<T> remove(Node<T> del_node, Node<T> root) {
		if (root == null) {
			return null;
		}

		if (del_node.getValue().compareTo(root.getValue()) < 0) {
			root.setLeft(remove(del_node, root.getLeft()));
		} else if (del_node.getValue().compareTo(root.getValue()) > 0) {
			root.setRight(remove(del_node, root.getRight()));
		} else {
			if (root.getLeft() == null) {
				return balance(root.getRight());
			} else if (root.getRight() == null) {
				return balance(root.getLeft());
			}
			int balanceFactor = root.getBalanceFactor();

			if (balanceFactor < 0) {
				Node<T> sucessor = max(root.getLeft());
				root.setValue(sucessor.getValue());
				root.setLeft(remove(sucessor, root.getLeft()));
			} else {
				Node<T> sucessor = min(root.getRight());
				root.setValue(sucessor.getValue());
				root.setRight(remove(sucessor, root.getRight()));
			}
		}

		return balance(root);
	}

	public boolean isBalanced() {
		return isBalanced(this.getRoot());
	}

	public boolean isBalanced(Node<T> node) {
		if (node == null) {
			return true;
		}

		return Math.abs(this.t_root.getBalanceFactor()) <= 1;
	}

	private Node<T> rotateRight(Node<T> parent) {
		Node<T> new_root = parent.getLeft();
		parent.setLeft(new_root.getRight());
		new_root.setRight(parent);

		return new_root;
	}

	private Node<T> rotateLeft(Node<T> parent) {
		Node<T> new_root = parent.getRight();
		parent.setRight(new_root.getLeft());
		new_root.setLeft(parent);

		return new_root;
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
}
