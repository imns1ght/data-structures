package me.imns1ght;

public class Node<T> {
	private T value; // Data of the node.
	private Node<T> left; // Left child node.
	private Node<T> right; // Right child node.
	private Node<T> parent; // Parent node.

	public Node() {
		this.value = null;
		this.left = null;
		this.right = null;
		this.parent = null;
	}

	public Node(T value) {
		this.value = value;
		this.left = null;
		this.right = null;
		this.parent = null;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Node<T> getLeft() {
		return left;
	}

	public void setLeft(Node<T> left) {
		this.left = left;
	}

	public Node<T> getRight() {
		return right;
	}

	public void setRight(Node<T> right) {
		this.right = right;
	}

	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public void print() {
		System.out.print(this.value);
	}
}
