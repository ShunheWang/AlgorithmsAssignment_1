import java.io.PrintStream;
import java.util.*;

public class LinkedListMultiset<T> extends Multiset<T> {
	private class Node {

		private T data;
		private Node next; // next node
		private int nodeCount; // this obj quantity

		public Node(T data) {
			this.data = data;
			this.nodeCount=1;
		}
		
		//Add existed node
		public void addNodeCount() {
			nodeCount++;
		}
		
		//delete existed node and this node quantity is over one
		public void delteNodeCount() {
			nodeCount--;
		}

	}

	private Node root;
	private Node lastNode;

	public LinkedListMultiset() {
		root=null;
		lastNode=null;
	}

	/*
	 * Add node into linkedlistmultiset.
	 * @see Multiset#add(java.lang.Object)
	 */
	public void add(T item) {
		//If item is null, return directly
		if (item == null) {
			return;
		}
		Node newNode = new Node(item);
		//If linkedlist is null, this node is the first node.
		if(this.root==null) {
			this.root=newNode;
			this.lastNode=newNode;
		}else {
			Node temp=toFind(this.root, item);
			//If this item has been not stored before, add this node into last
			if(temp==null) {
				this.lastNode.next=newNode;
				this.lastNode=newNode;
			}else {
				//If find out this node, add this node count+1
				temp.addNodeCount();
			}
		}
		
	}
	
	/*
	 * To find this item has been stored or not
	 * If find, return this node
	 * If not find, continue to check next node until last node.
	 */
	private Node toFind(Node currNode, T newNodeValue) {
		if(currNode.data.equals(newNodeValue)) {
			return currNode;
		}else if(!(currNode.data.equals(newNodeValue))&&currNode.next!=null){
			return toFind(currNode.next,newNodeValue);
		}else {
			return null;
		}
	}
	
	
	/*
	 * To search item has been stored into linkedlist before or not
	 * @see Multiset#search(java.lang.Object)
	 */
	public int search(T item) {	
		//If linkedlist is null or item is null, return 0.
		if (this.root == null || item == null) {
			return 0;
		}else {
			//To check next node until find or last node
			Node currNode = this.root;
			return toSearch(currNode,item);
		}

	}
	
	/*
	 * To check each node in linkedlist
	 */
	private int toSearch(Node currNode,T newNodeValue) {
		//If find, return this node count
		if(currNode.data.equals(newNodeValue)) {
			return currNode.nodeCount;
		}else if(!(currNode.data.equals(newNodeValue))&&currNode.next!=null){
			//Continue to check next node until find or last node
			return toSearch(currNode.next,newNodeValue);
		}else {
			//If not find, return 0;
			return 0;
		}
	}
	

	/*
	 * Remove one time
	 * @see Multiset#removeOne(java.lang.Object)
	 */
	public void removeOne(T item) {
		//If linkedlist is null or item is null, return directly.
		if (item == null||this.root == null) {
			return;
		}
		
		//If root is this item
		if(this.root.data.equals(item)) {
			//If this node count is 1, the first node is its next. Otherwise,decrease 1 for this count.
			if(this.root.nodeCount==1) {
				if(this.root.next==null) {
					this.root=null;
				}else {
					this.root=this.root.next;
				}
			}else {
				this.root.delteNodeCount();
			}	
		}else{
			//If this item is not root and root.next is not null
			Node currNode=this.root;
			while(currNode.next!=null) {
				if(currNode.next.data.equals(item)) {
					if(currNode.next.nodeCount==1) {
						if(currNode.next.next==null) {
							currNode.next=null;
						}else {
							currNode.next=currNode.next.next;
						}
					}else {
						currNode.next.delteNodeCount();
					}
					break;
				}else {
					currNode=currNode.next;
				}
			}
		}
		
	}
	
	/*
	 * To delete all this objs
	 * @see Multiset#removeAll(java.lang.Object)
	 */
	public void removeAll(T item) {
		//If this node count is 1, the first node is its next. Otherwise,decrease 1 for this count.
		if (this.root == null || item == null) {
			return;
		}
		//If root is this item
		if(this.root.data.equals(item)) {
			if(this.root.next==null) {
				this.root=null;
			}else {
				this.root=this.root.next;
			}		
		}else{
			Node currNode=this.root;
			//If the root.next is not null.
			while(currNode.next!=null) {
				if(currNode.next.data.equals(item)) {
					if(currNode.next.next==null) {
						currNode.next=null;
					}else {
						currNode.next=currNode.next.next;
					}
					break;
				}else {
					currNode=currNode.next;
				}
				
			}
		}
	}

	/*
	 * Print out format is like: data | size
	 * @see Multiset#print(java.io.PrintStream)
	 */
	public void print(PrintStream out) {
		Node currNode=this.root;
		while(currNode!=null) {
			out.println(currNode.data+" | "+currNode.nodeCount);
			currNode=currNode.next;
		}
	}
}