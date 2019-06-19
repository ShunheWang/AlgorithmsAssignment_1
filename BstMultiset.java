import java.io.PrintStream;
import java.util.*;

public class BstMultiset<T extends Comparable<T>> extends Multiset<T> {

	private class Node {
		private T data;
		private Node leftChildNode;
		private Node rightChildNode;
		private Node parentNode;
		private int nodeCount;

		public Node(T data) {
			this.data = data;
			this.nodeCount = 1;
			this.leftChildNode = null;
			this.rightChildNode = null;
			this.parentNode = null;
		}

		public void addNodeCount() {
			nodeCount++;
		}

		public void deleteNodeCount() {
			nodeCount--;
		}
	}

	private Node root;
	private int length;

	public BstMultiset() {
		this.root = null;
		this.length = 0;
	}

	public void add(T item) {
		if (this.root == null) {
			this.root = new Node(item);
		} else {
			insertNode(item, this.root);
		}
	}

	private void insertNode(T insertData, Node node) {
		int compareResult = insertData.compareTo(node.data);
		if (compareResult == 0) {
			node.addNodeCount();
		} else if (compareResult > 0) {
			if (node.rightChildNode == null) {
				Node newNode = new Node(insertData);
				node.rightChildNode = newNode;
				newNode.parentNode = node;
			} else {
				insertNode(insertData, node.rightChildNode);
			}
		} else {
			if (node.leftChildNode == null) {
				Node newNode = new Node(insertData);
				node.leftChildNode = newNode;
				newNode.parentNode = node;
			} else {
				insertNode(insertData, node.leftChildNode);
			}
		}
		length++;
	}

	public int search(T item) {
		if (this.root == null) {
			return 0;
		} else {
			return toFind(item, this.root);
		}
	}

	private int toFind(T searchData, Node currNode) {
		int compareResult = searchData.compareTo(currNode.data);
		if (compareResult == 0) {
			return currNode.nodeCount;
		} else if (compareResult > 0 && currNode.rightChildNode != null) {
			return toFind(searchData, currNode.rightChildNode);
		} else if (compareResult < 0 && currNode.leftChildNode != null) {
			return toFind(searchData, currNode.leftChildNode);
		} else
			return 0;
	}

	private Node toFindTarget(T searchData, Node currNode) {
		int compareResult = searchData.compareTo(currNode.data);
		if (compareResult == 0) {
			return currNode;
		} else if (compareResult > 0 && currNode.rightChildNode != null) {
			return toFindTarget(searchData, currNode.rightChildNode);
		} else if (compareResult < 0 && currNode.leftChildNode != null) {
			return toFindTarget(searchData, currNode.leftChildNode);
		} else
			return null;
	}

	public void removeOne(T item) {
		Node target = toFindTarget(item, this.root);
		if (target == null) {
			return;
		} else {
			if (target.nodeCount > 1) {
				target.deleteNodeCount();
			} else {
				deleteNode(target);
			}
		}
	}

	private void deleteNode(Node targetNode) {// the node to be deleted is a leaf node
		if (targetNode.leftChildNode == null && targetNode.rightChildNode == null) {
			if (targetNode.parentNode == null) {
				this.root = null;
				return;
			}

			// if the node to be deleted is the left child of its parent
			if (targetNode.parentNode.leftChildNode == targetNode) {
				targetNode.parentNode.leftChildNode = null;
			} else {
				targetNode.parentNode.rightChildNode = null;
			}
			length = length - targetNode.nodeCount;
		} else if (targetNode.rightChildNode != null && targetNode.leftChildNode == null) {
			Node targetRightChildNode = targetNode.rightChildNode;

			if (targetNode.parentNode == null) {
				exchange(targetNode);
				return;
			}

			if (targetNode.parentNode.leftChildNode == targetNode) {
				targetNode.parentNode.leftChildNode = targetRightChildNode;
				targetRightChildNode.parentNode = targetNode.parentNode;
			} else {
				targetNode.parentNode.rightChildNode = targetRightChildNode;
				targetRightChildNode.parentNode = targetNode.parentNode;
			}
			length = length - targetNode.nodeCount;
		} else if (targetNode.rightChildNode == null && targetNode.leftChildNode != null) {
			Node targetLeftChildNode = targetNode.leftChildNode;

			if (targetNode.parentNode == null) {
				this.root = targetNode.leftChildNode;
				return;
			}

			if (targetNode.parentNode.leftChildNode == targetNode) {
				targetNode.parentNode.leftChildNode = targetLeftChildNode;
				targetLeftChildNode.parentNode = targetNode.parentNode;
			} else {
				targetNode.parentNode.rightChildNode = targetLeftChildNode;
				targetLeftChildNode.parentNode = targetNode.parentNode;
			}
			length = length - targetNode.nodeCount;
		} else {
			exchange(targetNode);
		}
	}

	private void exchange(Node targetNode) {
		Node rightNode = targetNode.rightChildNode;
		Node smallestNode = toFindSmallestNode(rightNode);
		int deleteCount = targetNode.nodeCount;
		targetNode.data = smallestNode.data;
		targetNode.nodeCount = smallestNode.nodeCount;
		smallestNode.nodeCount = deleteCount;
		deleteNode(smallestNode);
	}

	private Node toFindSmallestNode(Node target) {
		Node currNode = target;
		if (currNode.leftChildNode == null) {
			return currNode;
		} else {
			return toFindSmallestNode(currNode.leftChildNode);
		}
	}

	public void removeAll(T item) {
		Node target = toFindTarget(item, this.root);
		if (target == null) {
			return;
		} else {
			deleteNode(target);
		}
	}

	public void print(PrintStream out) {
		inOrder(out, this.root);
	}

	private void inOrder(PrintStream out, Node node) {
		if (node.leftChildNode != null) {
			inOrder(out, node.leftChildNode);
		}
		out.println(node.data + " | " + node.nodeCount);
		if (node.rightChildNode != null) {
			inOrder(out, node.rightChildNode);
		}
	}
}
