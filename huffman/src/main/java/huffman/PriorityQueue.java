package huffman;

import java.util.Arrays;

/**
 * The {@code PriorityQueue} class implements... TODO
 * 
 * @author imns1ght
 */
public class PriorityQueue {
        private Node[] data; // Nodes of the heap.
        private int capacity; // Max capacity of the array.
        private int size; // Number of nodes in the PQueue.

        /**
         * Default constructor.
         * 
         * @param capacity max capacity of the PQueue.
         */
        PriorityQueue(int capacity) {
                this.data = new Node[capacity];
                this.capacity = capacity;
        }

        PriorityQueue(BSTree tree) {
                this.capacity = tree.getSize();
                this.data = new Node[this.capacity];

                while (tree.getRoot() != null) {
                        add(tree.getRoot());
                        tree.delete(tree.getRoot());
                }
        }

        /**
         * Maintain the heap property of a node and your childrens.
         * 
         * @param index index of the node.
         */
        private void minHeapify(int index) {
                int leftChild = getLeftIndex(index);
                int rightChild = getRightIndex(index);
                int min;

                if (leftChild < getCapacity() && this.data[leftChild] != null
                                && this.data[leftChild].getAmount() < this.data[index].getAmount()) {
                        min = leftChild;
                } else {
                        min = index;
                }

                if (rightChild < getCapacity() && this.data[rightChild] != null
                                && this.data[rightChild].getAmount() < this.data[min].getAmount()) {
                        min = rightChild;
                }

                if (this.data[min] != null && this.data[min].getAmount() != this.data[index].getAmount()) {
                        Node tmp = this.data[index];
                        this.data[index] = this.data[min];
                        this.data[min] = tmp;
                        minHeapify(min);
                }
        }

        /**
         * Insert a node in the PQueue.
         * 
         * @param node node to add.
         */
        protected void add(Node node) {
                Node new_node = new Node(node);
                ensureCapacity();
                this.data[getSize()] = new_node;
                decreaseNode(getSize());
                this.size++;
        }

        /**
         * Retrieve and remove the minimum element of the PQueue.
         * 
         * @return minimum node.
         */
        protected Node poll() {
                if (getSize() < 1) {
                        return null;
                }

                Node min = new Node(this.data[0]);
                this.data[0] = new Node(this.data[getSize() - 1]);
                this.data[getSize() - 1] = null;
                this.size--;
                minHeapify(0);

                return min;
        }

        /**
         * Retrieve the minimum element.
         * 
         * @return minimum element.
         */
        protected Node peek() {
                return this.size > 0 ? this.data[0] : null;
        }

        /**
         * Correct the place of a node.
         * 
         * @param index index of the node.
         *
         * @return index of the node after the operation.
         */
        private int decreaseNode(int index) {
                if (getParentIndex(index) < 0) {
                        return -1;
                }

                while (this.data[index].getAmount() < this.data[getParentIndex(index)].getAmount()) {
                        Node tmp = this.data[index];
                        this.data[index] = this.data[getParentIndex(index)];
                        this.data[getParentIndex(index)] = tmp;
                        index = getParentIndex(index);
                }

                return index;
        }

        /**
         * Clear the PQueue.
         */
        protected void clear() {
                this.data = new Node[this.capacity];
                this.size = 0;
        }

        /**
         * Check if the PQueue is empty.
         * 
         * @return true if the PQueue is empty, false otherwise.
         */
        protected boolean empty() {
                return this.size == 0;
        }

        /**
         * Check if PQueue is full and double its capacity.
         */
        private void ensureCapacity() {
                if (this.size == this.capacity) {
                        this.data = Arrays.copyOf(this.data, this.capacity * 2);
                        this.capacity = this.capacity * 2;
                }
        }

        /////////////////////////////////////////////
        // Gets/Sets
        /////////////////////////////////////////////

        protected int getSize() {
                return size;
        }

        protected int getCapacity() {
                return capacity;
        }

        protected int getParentIndex(int k) {
                return (k - 1) / 2;
        }

        protected Node getParentNode(int k) {
                return k > 0 ? this.data[getParentIndex(k)] : null;
        }

        protected int getLeftIndex(int k) {
                return (2 * k) + 1;
        }

        protected Node getLeftNode(int k) {
                return getLeftIndex(k) < getCapacity() ? this.data[getLeftIndex(k)] : null;
        }

        protected int getRightIndex(int k) {
                return (2 * k) + 2;
        }

        protected Node getRightNode(int k) {
                return getRightIndex(k) < getCapacity() ? this.data[getRightIndex(k)] : null;
        }

        /////////////////////////////////////////////
        // Print
        /////////////////////////////////////////////

        public void print() {
                for (int i = 0; i < this.size; i++) {
                        System.out.println("[ " + (char) this.data[i].getLetter() + " | " + this.data[i].getAmount()
                                        + " ]");
                }
        }
}