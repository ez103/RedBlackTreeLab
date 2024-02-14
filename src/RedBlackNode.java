
public class RedBlackNode extends BinaryNode {
	private int myColor;
	
	public RedBlackNode(Comparable x) {
		super(x);
		myColor = 0;
	}
	
	public String toString() {
		String temp = super.toString();
		temp += ", Color:" + (myColor==0?"Red":"Black");
		return temp;
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
	
	public void colorSwap() {
		
	}
	public void recolor() {
		
	}
	
	public void setColor(int c) {
		myColor = c;
	}
	public int getColor() {
		return myColor;
	}
}
