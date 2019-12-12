package huffman;

/**
 * The {@code Node} class implements the node to compose the Binary Tree.
 * 
 * @author imns1ght
 */
public class Node {
	private int letter; // Char of the node.
	private int amount; // Amount of chars.
	private Node left; // Left child node.
	private Node right; // Right child node.
	private Node parent; // Parent node.

	/**
	 * Default constructor.
	 */
	public Node() {
		this.letter = 0;
		this.amount = 1;
		this.left = null;
		this.right = null;
		this.parent = null;
	}

	/**
	 * Constructor with param.
	 * 
	 * @param node node to copy.
	 */
	public Node(Node node) {
		this.letter = node.getLetter();
		this.amount = node.getAmount();
		this.left = node.getLeft();
		this.right = node.getRight();
		this.parent = node.getParent();
	}

	/**
	 * Constructor with param.
	 * 
	 * @param a node to join.
	 * @param b node to join.
	 */
	public Node(Node a, Node b) {
		this.letter = 0;
		this.amount = a.getAmount() + b.getAmount();
		this.left = a;
		this.right = b;
		this.parent = null;
		a.setParent(this);
		b.setParent(this);

		// Remove old connections only if node a and/or b has a letter.
		if (a.getLetter() != 0) {
			a.setLeft(null);
			a.setRight(null);
		}

		if (b.getLetter() != 0) {
			b.setLeft(null);
			b.setRight(null);
		}
	}

	/**
	 * Constructor with param.
	 * 
	 * @param letter char of the node.
	 * @param count  amount of chars.
	 */
	public Node(int letter) {
		this.letter = letter;
		this.amount = 1;
		this.left = null;
		this.right = null;
		this.parent = null;
	}

	/**
	 * Constructor with param.
	 * 
	 * @param letter char of the node.
	 * @param count  amount of chars.
	 */
	public Node(int letter, int count) {
		this.letter = letter;
		this.amount = count;
		this.left = null;
		this.right = null;
		this.parent = null;
	}

	/////////////////////////////////////////////
	// Gets/Sets
	/////////////////////////////////////////////

	public int getLetter() {
		return letter;
	}

	public void setLetter(int letter) {
		this.letter = letter;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	/////////////////////////////////////////////
	// Print
	/////////////////////////////////////////////

	public void printLetter() {
		System.out.print(this.letter);
	}

	public void printAmount() {
		System.out.print(this.amount);
	}
}
