import java.io.File;
import java.io.IOException;
import java.util.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
public class DicitionTree {
	static Node head=new Node();
	public DicitionTree() {
		
	}
	
	static double[][] attribute=new double[100][4];
	static String[] Data=new String[100];
	
	public Node input() throws BiffException, IOException {
			int[] DataIndex=new int[100];
			for(int i=0;i<100;++i)
				DataIndex[i]=i;
			Workbook workbook = Workbook.getWorkbook(new File("Train.xls"));	//�}excel��
			Sheet sheet = workbook.getSheet("Train.xls");
			
		
		for(int i=0;i<100;++i) {	//�N�ݩ�Ū��attribute�}�C�A���Ū��Data�}�C��
			attribute[i][0]=Double.parseDouble(sheet.getCell(0,i).getContents());	
			attribute[i][1]=Double.parseDouble(sheet.getCell(1,i).getContents());
			attribute[i][2]=Double.parseDouble(sheet.getCell(2,i).getContents());
			attribute[i][3]=Double.parseDouble(sheet.getCell(3,i).getContents());
			Data[i]=sheet.getCell(4,i).getContents();
			
		}
		workbook.close();	//����excel��
		head=LearnTree(DataIndex);	//�V�m�M����A�ç�Ĥ@����ƪ���}�]��head�ܼ�
		return head;
	}
	
	static int countattr=3;
	static int iter=0;	//�p��`�I���ƶq
	public Node LearnTree(int[] dataIndex) {//D=data,A=attribute
		iter++;	//�C�����j��+1
		String[] Class=new String[dataIndex.length];	
		for(int i=0;i<dataIndex.length;++i) {	//�HClass�}�C�s�Ӹs���
			Class[i]=Data[dataIndex[i]];	//���ӵ���ƪ�data
		}
		int countA=0,countB=0,countC=0;
		double percentA,percentB,percentC;
		for(int i=0;i<Class.length;++i) {	//�p��A,B,C�U�����b�Ӹs��ƪ��ƶq
			if(Class[i].equals("A"))
				countA++;
			else if(Class[i].equals("B"))
				countB++;
			else if(Class[i].equals("C"))
				countC++;
		}
		
		if((countA+countB+countC)==0 || iter>30) {	//�Y�Ӹs�õL��Ʃθ`�I�W�L30�ӫh����j
			Node leaf=new Node();
			leaf.setLeaf("C");	//���𸭺�C����
			return leaf;
		}
		
			percentA=countA/(countA+countB+countC);	//�p��C�Ӻ����e�Ӹs��ƪ��ʤ���
			percentB=countB/(countA+countB+countC);
			percentC=countC/(countA+countB+countC);
		
		if(percentA>0.95 ) {	//�Y�Ӹ�ƪ��ƶq�e90%�H�W�A�h����j
			Node leaf=new Node();
			leaf.setLeaf("A");
			return leaf;
		}
		else if(percentB>0.95 ) {
			Node leaf=new Node();
			leaf.setLeaf("B");
			return leaf;
		}
		else if(percentC>0.95 ) {
			Node leaf=new Node();
			leaf.setLeaf("C");
			return leaf;
		}
		
		
		else {
			Node root=new Node();	//�s�W���`�I
			int count=countattr;	//�ݩ�D->C->B->A���`��
			root.setCon(find_best_split(dataIndex,(count%4)),count);	//0=A,1=B,2=C,3=D
			countattr--;
			if(countattr==-1)
				countattr=3;
			int leftArr=0,rightArr=0;
			for(int i=0;i<dataIndex.length;++i) {	//�p�⥪�l��M�k�l�𪺭Ӽ�
				if(attribute[dataIndex[i]][count%4]<=root.getConV()) {	//�p����쥪�l��Υk�l�𪺸�ƦU�X��
					leftArr++;
				}
				else 
					rightArr++;
				}
			int[] left=new int[leftArr];	//�ʺA�}�C�s�񥪤l����
			int[] right=new int[rightArr];	//�ʺA�}�C�s��k�l����
			int j=0;
			for(int i=0;i<left.length;++i) {	//����s���p��,�]�A����
				while(attribute[dataIndex[j]][count%4]>root.getConV()) {
					j++;
				}
				left[i]=dataIndex[j];
				j++;
			}
			j=0;
			for(int i=0;i<right.length;++i) {	//�k��s���j��
				while(attribute[dataIndex[j]][count%4]<=root.getConV()) {
					j++;
				}
				right[i]=dataIndex[j];
				j++;
			}
			
			root.setLeftLink(LearnTree(left));	//���j�s�W�`�I
			root.setRightLink(LearnTree(right));
			return root;
			}	
		}
		
	public double find_best_split(int[] dataIndex,int attri) {	//0=D,1=C,2=B,3=A
		int attrValue;	//�����
		double[] attributeValue=new double[dataIndex.length];
		for(int i=0;i<dataIndex.length;++i)	//�Ӹs��ƪ��ݩʦs��}�Cattribute��
			attributeValue[i]=attribute[dataIndex[i]][attri];
		for(int i=attributeValue.length;i>0;--i)	//�Ƨ�
			for(int j=0;j<i-2;++j)
				if(attributeValue[j]>attributeValue[j+1]) {
					double temp;
					temp=attributeValue[j];
					attributeValue[j]=attributeValue[j+1];
					attributeValue[j+1]=temp;
				}
		attrValue=(int)attributeValue.length/3;	//����Ȭ��}�C����1/3��m����
		return attributeValue[attrValue];	//�^�Ǳ����		
	}	
}