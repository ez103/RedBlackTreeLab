import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
	private BinaryNode root;
	
	
	public BinaryTree() {
		root = null;
	}
	
	
	
	public String toString() {
		return inOrder();
	}
	
	
	
	private BinaryNode successor(BinaryNode k) {
		BinaryNode temp = k;
		temp = temp.right();
		while(temp.left() != null)
			temp = temp.left();
		return temp;
	}
	private void swap(BinaryNode x, BinaryNode y)
	{
		Comparable k = x.getValue();
		x.setValue(y.getValue());
		y.setValue(k);
	}
	public BinaryNode remove(Comparable target) {
		if (root == null) {
			return null;
		}
		
		BinaryNode temp = root;
		BinaryNode inorderSuccessor;
		
		if (root.getValue().equals(target)) {
			// degree 0; a leaf, no children
			if (root.left() == null && root.right() == null) {
				root = null;
				return temp;
			}
			// has a right child only
			else if (root.left() == null) {
				root = root.right();
				temp.setRight(null);
				return temp;
			}
			// has a left child only
			else if (root.right() == null) {
				root = root.left();
				temp.setLeft(null);
				return temp;
			}
			// has 2 children
			else {
				inorderSuccessor = successor(root);
				swap(root, inorderSuccessor);
				
				if (root.right() == inorderSuccessor) {
					root.setRight(inorderSuccessor.right());
					inorderSuccessor.setRight(null);
					return inorderSuccessor;
				}
				
				return remove(root.right(), target);
			}
		}
		return remove(root, target);
	}
	private BinaryNode remove(BinaryNode startNode, Comparable target) {
		BinaryNode nodeToRemove, inorderSuccessor;
		BinaryNode parent = search(startNode,target);
		if(parent == null) return null;
		//decide if it is a left or right child
		boolean isLeft = parent.left()!=null && parent.left().getValue().equals(target);
		nodeToRemove = isLeft ? parent.left() : parent.right();
		
		if (nodeToRemove.left() == null && nodeToRemove.right() == null) {
			if(isLeft)
				parent.setLeft(null);
			else
				parent.setRight(null);
			return nodeToRemove;
		}
		else if (nodeToRemove.left() == null)
		{
			if(isLeft)
				parent.setLeft(nodeToRemove.right());
			else
				parent.setRight(nodeToRemove.right());
			nodeToRemove.setRight(null);
			return nodeToRemove;
		}
		else if (nodeToRemove.right() == null) {
			if(isLeft)
				parent.setLeft(nodeToRemove.left());
			else
				parent.setRight(nodeToRemove.left());
			nodeToRemove.setLeft(null);
			return nodeToRemove;
		}
		else {
			inorderSuccessor = successor(nodeToRemove);
			swap(inorderSuccessor, nodeToRemove);
			if(nodeToRemove.right()==inorderSuccessor) {
				nodeToRemove.setRight(inorderSuccessor.right());
				inorderSuccessor.setRight(null);
				return inorderSuccessor;
			}
			return remove(nodeToRemove.right(), target);
		}
	}
	private BinaryNode search(BinaryNode parent, Comparable target) {
		if (parent == null) {
			return null;
		}
		if (parent.left()!=null && parent.left().getValue().equals(target) 
				|| parent.right()!=null && parent.right().getValue().equals(target)) {
			return parent;
		}
		else if (target.compareTo(parent.getValue()) < 0) {
			return search(parent.left(), target);
		}
		else {
			return search(parent.right(), target);
		}
	}
	
	public String preOrder() {
		return preOrder(root).trim();
	}
	private String preOrder(BinaryNode bn) {
		String temp = "";
		if (bn != null) {
			temp += bn.getValue() + " ";
			temp += preOrder(bn.left());
			temp += preOrder(bn.right());
		}
		return temp;
	}
	
	public String inOrder() {
		return inOrder(root).trim();
	}
	private String inOrder(BinaryNode bn) {
		String temp = "";
		if (bn != null) {
			temp += inOrder(bn.left());
			temp += bn.getValue() + " ";
			temp += inOrder(bn.right());
		}
		return temp;
	}
	
	public String postOrder() {
		return postOrder(root).trim();
	}
	private String postOrder(BinaryNode bn) {
		String temp = "";
		if (bn != null) {
			temp += postOrder(bn.left());
			temp += postOrder(bn.right());
			temp += bn.getValue() + " ";
		}
		return temp;
	}
	
	public String reverseOrder() {
		return reverseOrder(root).trim();
	}
	private String reverseOrder(BinaryNode bn) {
		String temp = "";
		if (bn != null) {
			temp += reverseOrder(bn.right());
			temp += bn.getValue() + " ";
			temp += reverseOrder(bn.left());
		}
		return temp;
	}
	
	public String levelOrder() {
		String temp = "";
		Queue<BinaryNode> q = new LinkedList<>();
		q.offer(root);
		
		while (!q.isEmpty()) {
			BinaryNode bn = q.poll();
			temp += bn.getValue() + " ";
			if (bn.left() != null) {
				q.offer(bn.left());
			}
			if (bn.right() != null) {
				q.offer(bn.right());
			}
		}
		
		return temp.trim();
	}
	
	public boolean isFull() {
		return isFull(root);
	}
	private boolean isFull(BinaryNode bn) {
		return isLeafOrParentOfTwo(bn) && isFull(bn.left()) && isFull(bn.right());
	}
	private boolean isLeafOrParentOfTwo(BinaryNode bn) {
		return bn == null || (bn.left() == null && bn.right() == null) || (bn.left() != null) && (bn.right() != null);
	}
	
	public boolean contains(Comparable x) {
		BinaryNode current = root;
		while (current != null) {
			if (x.equals(current.getValue())) { // current node is the value, so return true
				return true;
			}
			else if (x.compareTo(current.getValue()) > 0) { // x is greater than parent node, so go to the right
				current = current.right();
			}
			else {
				current = current.left();
			}
		}
		
		return false;
	}
	
	public int getHeight() {
		return getHeight(root);
	}
	private int getHeight(BinaryNode bn) {
		if (bn == null) {
			return -1;
		}
		return 1 + Math.max(getHeight(bn.left()), getHeight(bn.right()));
	}
	
	public int getDiameter() {
		return getHeight(root.left()) + getHeight(root.right()) + 3;
	}
	
	public int getNumNodes() {
		return getNumNodes(root);
	}
	private int getNumNodes(BinaryNode bn) {
		if (bn == null) {
			return 0;
		}
		else {
			return 1+ getNumNodes(bn.left()) + getNumNodes(bn.right());
		}
	}
	
	public int getNumLevels() {
		return getHeight() + 1;
	}
	
	public int getNumLeaves() {
		return getNumLeaves(root);
	}
	private int getNumLeaves(BinaryNode bn) {
		if (bn == null) {
			return 0;
		}
		else if (bn.left() == null && bn.right() == null) { // degree 0, it has no children, it is a leaf
			return 1;
		}
		else {
			return getNumLeaves(bn.left()) + getNumLeaves(bn.right());
		}
	}
	
	public void add(BinaryNode bn) {
		if (root == null) {
			root = bn;
		}
		else {
			add(root, bn);
		}
	}
	private void add(BinaryNode parent, BinaryNode x)
	{
		if(parent == null) return;
		if(x.getValue().compareTo(parent.getValue()) < 0) {
			if(parent.left() == null) {
				parent.setLeft(x);
			}
			else {
				add(parent.left(), x);
			}
		}
		else {
			if(parent.right() == null) {
				parent.setRight(x);
			}
			else {
				add(parent.right(), x);
			}
		}
	}
	
	public int getWidth() {
		int width = Integer.MIN_VALUE;
		int levelWidth = 0;

		Queue<BinaryNode> q = new LinkedList<>();

		if (root == null) {
			return 0; // the tree is empty
		}
		else {
			q.add(root);

			while (q.size() != 0) {
				levelWidth = q.size();
				width = Math.max(width, levelWidth);

				while (levelWidth > 0) {
					BinaryNode g = q.poll();

					if (g.left() != null) {
						q.add(g.left());
					}
					if (g.right() != null) {
						q.add(g.right());
					}
					levelWidth--;
				}
			}
		}

		return width;
	}
	
	public Comparable getLargest() {
		BinaryNode bn = root;
		
		while (bn.right() != null) {
			bn = bn.right();
		}
		
		return bn.getValue();
	}
	public Comparable getSmallest() {
		BinaryNode bn = root;
		
		while (bn.left() != null) {
			bn = bn.left();
		}
		
		return bn.getValue();
	}
}
