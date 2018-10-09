import java.io.File;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TrainTree {
	static double [][] A=new double[50][4];	//�s����ո�ƪ��}�C
	
	public static void main(String[] args) throws BiffException, IOException {
		// TODO Auto-generated method stub
		DicitionTree training=new DicitionTree();	//�إ߰V�m�𪺪���
		
		Node s=training.input();	//�V�m���G�^�ǨM���𪺰_�l��}
		
		String[] result=new String[50];	//�s��������G
		Workbook workbook = Workbook.getWorkbook(new File("Test.xls"));	//�}excel��
		Sheet sheet = workbook.getSheet("Test");
		for(int i=0;i<50;++i) {	//Ū�Jexcel���ݩʸ��
			A[i][0]=Double.parseDouble(sheet.getCell(0,i).getContents());
			A[i][1]=Double.parseDouble(sheet.getCell(1,i).getContents());
			A[i][2]=Double.parseDouble(sheet.getCell(2,i).getContents());
			A[i][3]=Double.parseDouble(sheet.getCell(3,i).getContents());
		}
		for(int i=0;i<50;++i) {	//��Ƥ@���@����J�M����
			Node start=s;	//��l�ƾ𪺰_�l��}
			while(start.getLeftLink()!=null && start.getRightLink()!=null) {	//�]�M�����X�����
				//System.out.println(start.getConV());
				int index=start.getConA();
				if(A[i][index]<=start.getConV()) {
					start=start.getLeftLink();
				}
				else
					start=start.getRightLink();
			}
		result[i]=start.getclass();	//�N�����s�J�}�Cresult��
		System.out.println("��"+(i+1)+"��: "+result[i]);	//�L�X���G
		}
	}
}