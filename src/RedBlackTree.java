
public class RedBlackTree extends BinaryTree {
	private RedBlackNode root;
	
	public RedBlackTree() {
		root = null;
	}
	public void add(RedBlackNode bn) {
		if (root == null) {
			root = bn;
		}
		else {
			add(root, bn);
		}
	}
	private void add(RedBlackNode parent, RedBlackNode x)
	{
		if(parent == null) return;
		if (parent.getColor() == 1 && ((RedBlackNode)parent.left()).getColor() == 0 && ((RedBlackNode)parent.right()).getColor() == 0) {
			colorSwap(parent);
		}
		if(x.getValue().compareTo(parent.getValue()) < 0) {
			if(parent.left() == null) {
				parent.setLeft(x);
			}
			else {
				add((RedBlackNode)parent.left(), x);
			}
		}
		else {
			if(parent.right() == null) {
				parent.setRight(x);
			}
			else {
				add((RedBlackNode)parent.right(), x);
			}
		}
	}
	
	private RedBlackNode leftLeftRotation(RedBlackNode g, RedBlackNode p) {
		g.setLeft(p.right());
		p.setRight(g);
		g.setColor(g.getColor()-1);
		p.setColor(g.getColor()+1);
		return p;
	}
	private RedBlackNode rightRightRotation(RedBlackNode g, RedBlackNode p) { 
		g.setRight(p.left());
		p.setLeft(g);
		g.setColor(g.getColor()-1);
		p.setColor(p.getColor()+1);
		return p;
	}
	private RedBlackNode leftRightRotation(RedBlackNode g, RedBlackNode p, RedBlackNode x) {
		p.setRight(x.left());
		x.setLeft(p);
		return leftLeftRotation(g, x);
	}
	private RedBlackNode rightLeftRotation(RedBlackNode g, RedBlackNode p, RedBlackNode x) {
		p.setLeft(x.right());
		x.setRight(p);
		return rightRightRotation(g, x);
	}
	
	private RedBlackNode successor(RedBlackNode k) {
		RedBlackNode temp = k;
		temp = (RedBlackNode)temp.right();
		while(temp.left() != null)
			temp = (RedBlackNode)temp.left();
		return temp;
	}
	private void swap(RedBlackNode x, RedBlackNode y)
	{
		Comparable k = x.getValue();
		x.setValue(y.getValue());
		y.setValue(k);
	}
	public RedBlackNode remove(Comparable target) {
		if (root == null) {
			return null;
		}
		
		RedBlackNode temp = root;
		RedBlackNode inorderSuccessor;
		
		if (root.getValue().equals(target)) {
			// degree 0; a leaf, no children
			if (root.left() == null && root.right() == null) {
				root = null;
				return temp;
			}
			// has a right child only
			else if (root.left() == null) {
				root = (RedBlackNode)root.right();
				temp.setRight(null);
				return temp;
			}
			// has a left child only
			else if (root.right() == null) {
				root = (RedBlackNode)root.left();
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
				
				return remove((RedBlackNode)root.right(), target);
			}
		}
		return remove(root, target);
	}
	private RedBlackNode remove(RedBlackNode startNode, Comparable target) {
		RedBlackNode nodeToRemove, inorderSuccessor;
		RedBlackNode parent = search(startNode,target);
		if(parent == null) return null;
		//decide if it is a left or right child
		boolean isLeft = parent.left()!=null && parent.left().getValue().equals(target);
		nodeToRemove = isLeft ? (RedBlackNode)parent.left() : (RedBlackNode)parent.right();
		
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
			return remove((RedBlackNode)nodeToRemove.right(), target);
		}
	}
	private RedBlackNode search(RedBlackNode parent, Comparable target) {
		if (parent == null) {
			return null;
		}
		if (parent.left()!=null && parent.left().getValue().equals(target) 
				|| parent.right()!=null && parent.right().getValue().equals(target)) {
			return parent;
		}
		else if (target.compareTo(parent.getValue()) < 0) {
			return search((RedBlackNode)parent.left(), target);
		}
		else {
			return search((RedBlackNode)parent.right(), target);
		}
	}
	
	public void colorSwap(RedBlackNode x) {
		x.setColor(x.getColor()-1);
		if (x.left() != null) {
			RedBlackNode temp = (RedBlackNode)(x.left());
			temp.setColor(temp.getColor() + 1);
		}
		if (x.right() != null) {
			RedBlackNode temp = (RedBlackNode)(x.right());
			temp.setColor(temp.getColor() + 1);
		}
	}
	public void recolor(RedBlackNode p) {
		p.setColor(p.getColor() + 1);
		RedBlackNode temp = (RedBlackNode)(p.right());
		if (temp != null) {
			temp.setColor(temp.getColor() - 1);
		}
		temp = (RedBlackNode)(p.left());
		if (temp != null) {
			temp.setColor(temp.getColor() - 1);
		}
	}
}
