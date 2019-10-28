import java.io.PrintStream;
import java.util.*;

public class SortedLinkedListMultiset<T extends Comparable<T>> extends Multiset<T> 
{
	
	private class Node{
		private int nodeCount;
		private T data;
		private Node next;
		
		public Node(T data) {
			this.data=data;
			this.nodeCount=1;
			next=null;
		}
		
		public void addCount(){
			this.nodeCount++;
		}
		
		public void reduceCount(){
			this.nodeCount--;
		}
	}
	
	private Node root;
	private int length;
	
	public boolean isEmpty(){
	return root==null;
	}
	
	public SortedLinkedListMultiset() {
		root=null;
		length=0;
	}
	
	
	public void add(T item) {
		if(item==null) {
			return;
		}
			Node newNode=new Node(item);
		if(this.root==null) {
			this.root=newNode;
			return;
		}
			if(this.root.data.compareTo(item)>0) {
				newNode.next=this.root;
				this.root=newNode;
			}else if(this.root.data.compareTo(item)==0) {
				this.root.addCount();
			}else {
				Node temp=this.root;
				while(temp.next!=null) {
					if(temp.next.data.compareTo(item)<0){
						temp = temp.next;
					}else {
						break;
					}
				}
				if(temp.next!=null&&(temp.next.data).compareTo(item)==0) {
					temp.next.addCount();
				}else {
					if(temp.next!=null) {
						newNode.next=temp.next;
					}
					temp.next=newNode;
				}
			}
		
		length++;
	}
	
	
	public int search(T item) {
		if (item ==null) {
			return 0;
		}else {	
			Node temp=this.root;
			while(temp!=null) {
				if(temp.data.compareTo(item)<0){
					temp = temp.next;
				}else if(temp.data.compareTo(item)==0){
					return temp.nodeCount;
				}else {
					break;
				}
			}
		}
		return 0;
	}
	
	
	public void removeOne(T item) {
		if (item == null||this.root==null) {
			return;
		}else {	
			if(this.root.data.compareTo(item)>0){
				return;
			}else if(this.root.data.compareTo(item)==0) {
				if(this.root.nodeCount==1) {
					this.root=this.root.next;
					length--;
				}else {
					this.root.reduceCount();
					length--;
				}
			}else {
				Node temp=this.root;
				while(temp.next!=null) {	
					if(temp.next.data.compareTo(item)<0){
						temp = temp.next;
					}else if(temp.next.data.compareTo(item)==0){
						if(temp.next.nodeCount==1) {
							temp.next =temp.next.next;
							length--;
							break;
						}else {
							temp.next.reduceCount();
							length--;
							break;
						}
					}else {
						break;
					}
				}
			}
		}
	}
	
	
	public void removeAll(T item) {
		if (item == null||this.root==null) {
			return;
		}else {	
			if(this.root.data.compareTo(item)>0){
				return;
			}else if(this.root.data.compareTo(item)==0) {
				this.root=this.root.next;
				length=length-this.root.nodeCount;
			}else {
				Node temp=this.root;
				while(temp.next!=null) {	
					if(temp.next.data.compareTo(item)<0){
						temp = temp.next;
					}else if(temp.next.data.compareTo(item)==0){
							length=length-temp.next.nodeCount;
							temp.next =temp.next.next;
							break;
					}else {
						break;
					}
				}
			}
		}
	}
	
	public void print(PrintStream out) {
		out = new PrintStream(System.out);
		Node temp= this.root;
		while(temp!=null){
			out.println(temp.data + "  |  " + temp.nodeCount);
			temp = temp.next;
		}
	}
	
}
