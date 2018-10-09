public class Node {
	private double condition;
	private String cClass;
	private int attr;
	private Node LeftLink,RightLink;	//連結到下一個資料
	public Node() {
		condition=-1;
		LeftLink=null;
		RightLink=null;
		attr=-1;
	}
	
	public Node(double condition,Node LeftLink,Node RightLink,int attr) {	//初始化
		this.condition=condition;
		this.LeftLink=LeftLink;
		this.RightLink=RightLink;
		this.attr=attr;
	}

	public void setLeaf(String NewClass) {	//set leaf
		this.cClass=NewClass;
		this.LeftLink=null;
		this.RightLink=null;
		
	}
	public void setLeftLink(Node NewLeftLink) {	//setLeftLink
		this.LeftLink=NewLeftLink;
		
	}
	public void setRightLink(Node NewRightLink) {	//setRightLink
		this.RightLink=NewRightLink;
	}
	public void setCon(double con,int attr) {
		condition=con;
		this.attr=attr;
	}
	public double getConV() {	//條件值
		return condition;
	}
	public int getConA() {	//屬性值
		return attr;
	}
	public String getclass() {	//回傳類別值
		return cClass;
	}
	public Node getLeftLink() {	//回傳左節點的位址
		return LeftLink;
	}
	public Node getRightLink() { //回傳右節點的位址
		return RightLink;
	}
}
