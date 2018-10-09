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
			Workbook workbook = Workbook.getWorkbook(new File("Train.xls"));	//開excel檔
			Sheet sheet = workbook.getSheet("Train.xls");
			
		
		for(int i=0;i<100;++i) {	//將屬性讀到attribute陣列，資料讀到Data陣列中
			attribute[i][0]=Double.parseDouble(sheet.getCell(0,i).getContents());	
			attribute[i][1]=Double.parseDouble(sheet.getCell(1,i).getContents());
			attribute[i][2]=Double.parseDouble(sheet.getCell(2,i).getContents());
			attribute[i][3]=Double.parseDouble(sheet.getCell(3,i).getContents());
			Data[i]=sheet.getCell(4,i).getContents();
			
		}
		workbook.close();	//關閉excel檔
		head=LearnTree(DataIndex);	//訓練決策樹，並把第一筆資料的位址設給head變數
		return head;
	}
	
	static int countattr=3;
	static int iter=0;	//計算節點的數量
	public Node LearnTree(int[] dataIndex) {//D=data,A=attribute
		iter++;	//每次遞迴都+1
		String[] Class=new String[dataIndex.length];	
		for(int i=0;i<dataIndex.length;++i) {	//以Class陣列存該群資料
			Class[i]=Data[dataIndex[i]];	//找到該筆資料的data
		}
		int countA=0,countB=0,countC=0;
		double percentA,percentB,percentC;
		for(int i=0;i<Class.length;++i) {	//計算A,B,C各種類在該群資料的數量
			if(Class[i].equals("A"))
				countA++;
			else if(Class[i].equals("B"))
				countB++;
			else if(Class[i].equals("C"))
				countC++;
		}
		
		if((countA+countB+countC)==0 || iter>30) {	//若該群並無資料或節點超過30個則停止遞迴
			Node leaf=new Node();
			leaf.setLeaf("C");	//此樹葉維C種類
			return leaf;
		}
		
			percentA=countA/(countA+countB+countC);	//計算每個種類占該群資料的百分比
			percentB=countB/(countA+countB+countC);
			percentC=countC/(countA+countB+countC);
		
		if(percentA>0.95 ) {	//若該資料的數量占90%以上，則停止遞迴
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
			Node root=new Node();	//新增父節點
			int count=countattr;	//屬性D->C->B->A坐循環
			root.setCon(find_best_split(dataIndex,(count%4)),count);	//0=A,1=B,2=C,3=D
			countattr--;
			if(countattr==-1)
				countattr=3;
			int leftArr=0,rightArr=0;
			for(int i=0;i<dataIndex.length;++i) {	//計算左子樹和右子樹的個數
				if(attribute[dataIndex[i]][count%4]<=root.getConV()) {	//計算分到左子樹及右子樹的資料各幾筆
					leftArr++;
				}
				else 
					rightArr++;
				}
			int[] left=new int[leftArr];	//動態陣列存放左子樹資料
			int[] right=new int[rightArr];	//動態陣列存放右子樹資料
			int j=0;
			for(int i=0;i<left.length;++i) {	//左邊存較小的,包括等於
				while(attribute[dataIndex[j]][count%4]>root.getConV()) {
					j++;
				}
				left[i]=dataIndex[j];
				j++;
			}
			j=0;
			for(int i=0;i<right.length;++i) {	//右邊存較大的
				while(attribute[dataIndex[j]][count%4]<=root.getConV()) {
					j++;
				}
				right[i]=dataIndex[j];
				j++;
			}
			
			root.setLeftLink(LearnTree(left));	//遞迴新增節點
			root.setRightLink(LearnTree(right));
			return root;
			}	
		}
		
	public double find_best_split(int[] dataIndex,int attri) {	//0=D,1=C,2=B,3=A
		int attrValue;	//條件值
		double[] attributeValue=new double[dataIndex.length];
		for(int i=0;i<dataIndex.length;++i)	//該群資料的屬性存到陣列attribute中
			attributeValue[i]=attribute[dataIndex[i]][attri];
		for(int i=attributeValue.length;i>0;--i)	//排序
			for(int j=0;j<i-2;++j)
				if(attributeValue[j]>attributeValue[j+1]) {
					double temp;
					temp=attributeValue[j];
					attributeValue[j]=attributeValue[j+1];
					attributeValue[j+1]=temp;
				}
		attrValue=(int)attributeValue.length/3;	//條件值為陣列索引1/3位置的值
		return attributeValue[attrValue];	//回傳條件值		
	}	
}