package imns1ght.trie;

import java.lang.String;
import java.util.LinkedList;
import java.util.List;

public class Trie {
	static final int ALPHABET_SIZE = 26;
	private Node t_root;
	private int t_size;

	public Trie() {
		this.t_root = new Node();
	}

	public Trie(LinkedList<String> wordsList) {
		this.t_root = new Node();

		for (String str : wordsList) {
			insert(str);
		}
	}

	public void insert(String word) {
		char[] wordArray = word.toLowerCase().toCharArray();
		Node currNode = this.t_root;

		for (char c : wordArray) {
			int idx = c - 'a';

			if (currNode.children[idx] == null)
				currNode.children[idx] = new Node();

			currNode = currNode.children[idx];
		}

		currNode.setWordComplete(true);
		this.t_size++;
	}

	public boolean remove(String word) {
		char[] wordArray = word.toLowerCase().toCharArray();
		Node currNode = this.t_root;

		for (char c : wordArray) {
			int idx = c - 'a';

			if (currNode.children[idx] == null)
				return false;

			currNode = currNode.children[idx];
		}

		if (currNode.isWord()) {
			currNode.setWordComplete(false);
			this.t_size--;
			return true;
		}

		return false;
	}

	public boolean search(String word) {
		char[] wordArray = word.toLowerCase().toCharArray();
		Node currNode = this.t_root;

		for (char c : wordArray) {
			int idx = c - 'a';

			if (currNode.children[idx] == null)
				return false;

			currNode = currNode.children[idx];
		}

		return (currNode != null && currNode.isWord());
	}

	public List<String> autoComplete(String prefix, int resultLimit) {
		LinkedList<String> result = new LinkedList<String>();
		List<String> slicedResult = new LinkedList<String>();
		Node currNode = this.t_root;
		prefix = prefix.toLowerCase();
		char[] prefixArray = prefix.toCharArray();

		// Find node with the query
		for (char c : prefixArray) {
			int idx = c - 'a';

			if (currNode.children[idx] == null)
				return result;

			currNode = currNode.children[idx];
		}

		result = findWords(result, currNode, prefix);
		int resultSize = result.size();
		slicedResult = result.subList(0,
				resultLimit < resultSize ? resultLimit : resultSize);
		slicedResult.sort((s1, s2) -> s1.length() - s2.length());

		return slicedResult;
	}

	private LinkedList<String> findWords(LinkedList<String> result, Node currNode,
			String word) {
		if (currNode.isWord())
			result.add(word);

		for (int i = 0; i < ALPHABET_SIZE; i++) {
			if (currNode.children[i] != null) {
				word += (char) (i + 'a');
				result = findWords(result, currNode.children[i], word);
				word = word.substring(0, word.length() - 1);
			}
		}

		return result;
	}

	public int getSize() {
		return t_size;
	}

	public boolean isEmpty() {
		return t_size == 0;
	}

	public void print() {
		print(this.t_root, new String());
	}

	private void print(Node currNode, String word) {
		if (currNode.isWord())
			System.out.println(word);

		for (int i = 0; i < ALPHABET_SIZE; i++) {
			if (currNode.children[i] != null) {
				word += (char) (i + 'a');
				print(currNode.children[i], word);
				word = word.substring(0, word.length() - 1);
			}
		}
	}
}
