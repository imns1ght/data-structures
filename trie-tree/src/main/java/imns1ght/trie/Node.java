package imns1ght.trie;

public class Node {
	static final int ALPHABET_SIZE = 26;
	protected Node[] children;
	private boolean isWord;

	public Node() {
		this.children = new Node[ALPHABET_SIZE];
	}

	public boolean isWord() {
		return isWord;
	}

	public void setWordComplete(boolean isWord) {
		this.isWord = isWord;
	}

	public boolean isEmpty() {
		for (Node node : children) {
			if (node != null) {
				return false;
			}
		}

		return true;
	}
}
