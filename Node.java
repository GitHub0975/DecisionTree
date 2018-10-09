public class Node {
	private double condition;
	private String cClass;
	private int attr;
	private Node LeftLink,RightLink;	//�s����U�@�Ӹ��
	public Node() {
		condition=-1;
		LeftLink=null;
		RightLink=null;
		attr=-1;
	}
	
	public Node(double condition,Node LeftLink,Node RightLink,int attr) {	//��l��
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
	public double getConV() {	//�����
		return condition;
	}
	public int getConA() {	//�ݩʭ�
		return attr;
	}
	public String getclass() {	//�^�����O��
		return cClass;
	}
	public Node getLeftLink() {	//�^�ǥ��`�I����}
		return LeftLink;
	}
	public Node getRightLink() { //�^�ǥk�`�I����}
		return RightLink;
	}
}
