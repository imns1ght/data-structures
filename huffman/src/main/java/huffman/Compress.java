package huffman;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * The {@code Compress} class implements functions to compress text files using
 * the Huffman's coding algorithm.
 * 
 * @author Ranieri Santos
 * @author imns1ght
 */
public class Compress {
        private IOFile iofile;
        private PriorityQueue heap;
        private HashMap<Integer, String> symbolTable;
        private String input_path;
        private String compressed_path;
        private String table_path;

        /**
         * Default constructor
         * 
         * @param input_path path to the input file.
         * @throws UnsupportedEncodingException
         */
        public Compress(String input_path, String compressed_path, String table_path)
                        throws UnsupportedEncodingException {
                this.input_path = input_path;
                this.compressed_path = compressed_path;
                this.table_path = table_path;

                this.symbolTable = new HashMap<Integer, String>();
                this.iofile = new IOFile(this.input_path);
                HashMap<Integer, Integer> charCount = new HashMap<Integer, Integer>(iofile.fileToMap());
                BSTree charTree = new BSTree(charCount);
                this.heap = new PriorityQueue(charTree);
        }

        /**
         * Starts the compression process.
         */
        protected void startCompress() {
                mergeHeapToTree();
                treeToSymbolTable(this.heap.peek(), "");

                try {
                        iofile.writeToFile(this.symbolTable, this.compressed_path, this.table_path);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Creates father node while heap size > 1.
         */
        private void mergeHeapToTree() {
                while (this.heap.getSize() > 1) {
                        Node a = this.heap.poll();
                        Node b = this.heap.poll();
                        Node new_node = new Node(a, b);
                        this.heap.add(new_node);
                }
        }

        /**
         * Creates, recursively, unique "binary" code for each leaf of the tree.
         * 
         * @param node Current node.
         * @param bin  Concatenated "binary" code of iteractions.
         * @return "Binary" code of leaf.
         */
        private String treeToSymbolTable(Node node, String bin) {
                if (node != null) {
                        if (node.getLeft() == null) {
                                // If node is leaf.
                                this.symbolTable.put(node.getLetter(), bin);
                                bin = bin.length() > 0 ? bin.substring(0, bin.length() - 1) : ""; // bin--
                                return bin;
                        } else {
                                bin += '0';
                                bin = treeToSymbolTable(node.getLeft(), bin);
                        }

                        if (node.getRight() == null) {
                                // If node is leaf.
                                this.symbolTable.put(node.getLetter(), bin);
                                bin = bin.length() > 0 ? bin.substring(0, bin.length() - 1) : ""; // bin--
                                return bin;
                        } else {
                                bin += '1';
                                bin = treeToSymbolTable(node.getRight(), bin);
                        }
                }

                if (bin.length() > 0)
                        bin = bin.length() > 0 ? bin.substring(0, bin.length() - 1) : ""; // bin--

                return bin;
        }

        /**
         * Print the hash map.
         */
        protected void print() {
                this.symbolTable.entrySet().forEach(entry -> {
                        int temp = entry.getKey();
                        System.out.println("[ " + (char) temp + " | " + entry.getValue() + "\t]");
                });
        }
}
