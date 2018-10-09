import java.io.File;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TrainTree {
	static double [][] A=new double[50][4];	//存放測試資料的陣列
	
	public static void main(String[] args) throws BiffException, IOException {
		// TODO Auto-generated method stub
		DicitionTree training=new DicitionTree();	//建立訓練樹的物件
		
		Node s=training.input();	//訓練結果回傳決策樹的起始位址
		
		String[] result=new String[50];	//存放分類結果
		Workbook workbook = Workbook.getWorkbook(new File("Test.xls"));	//開excel檔
		Sheet sheet = workbook.getSheet("Test");
		for(int i=0;i<50;++i) {	//讀入excel的屬性資料
			A[i][0]=Double.parseDouble(sheet.getCell(0,i).getContents());
			A[i][1]=Double.parseDouble(sheet.getCell(1,i).getContents());
			A[i][2]=Double.parseDouble(sheet.getCell(2,i).getContents());
			A[i][3]=Double.parseDouble(sheet.getCell(3,i).getContents());
		}
		for(int i=0;i<50;++i) {	//資料一筆一筆丟入決策樹中
			Node start=s;	//初始化樹的起始位址
			while(start.getLeftLink()!=null && start.getRightLink()!=null) {	//跑決策樹找出其種類
				//System.out.println(start.getConV());
				int index=start.getConA();
				if(A[i][index]<=start.getConV()) {
					start=start.getLeftLink();
				}
				else
					start=start.getRightLink();
			}
		result[i]=start.getclass();	//將種類存入陣列result中
		System.out.println("第"+(i+1)+"筆: "+result[i]);	//印出結果
		}
	}
}