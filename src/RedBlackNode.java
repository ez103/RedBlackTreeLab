
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
	
	
	
	public void setColor(int c) {
		myColor = c;
	}
	public int getColor() {
		return myColor;
	}
}
