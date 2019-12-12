package imns1ght.me;

public class Node<T> {
	private T value; // Data of the node.
	private Node<T> left; // Left child node.
	private Node<T> right; // Right child node.

	public Node() {
		this.value = null;
		this.left = null;
		this.right = null;
	}

	public Node(T value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Node<T> getLeft() {
		return this.left;
	}

	public void setLeft(Node<T> left) {
		this.left = left;
	}

	public Node<T> getRight() {
		return this.right;
	}

	public void setRight(Node<T> right) {
		this.right = right;
	}

	public int getBalanceFactor() {
		int leftHeight = this.left == null ? 0 : 1 + getLeft().getHeight();
		int rightHeight = this.right == null ? 0 : 1 + getRight().getHeight();

		return leftHeight - rightHeight;
	}

	private int getHeight() {
		if (this.left == null && this.right == null) {
			return 0;
		} else if (this.left == null) {
			return 1 + this.right.getHeight();
		} else if (this.right == null) {
			return 1 + this.left.getHeight();
		}

		return 1 + Math.max(this.right.getHeight(), this.left.getHeight());
	}

	public void print() {
		System.out.print(this.value);
	}
}
