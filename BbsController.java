package com.donggoo.bbs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BbsController {

	final String FILE_NAME = "C:" + File.separator + "Users" + File.separator + "dongg" + File.separator + "Desktop"+ File.separator + "Project"+ File.separator+ "Project"+ File.separator + "database.txt";
	final String NAME = "database.txt";
	final String DIR = "C:" + File.separator + "Users" + File.separator + "dongg" + File.separator + "Desktop"+ File.separator + "Project"+ File.separator+ "Project"+ File.separator;
	final String IDX_NAME = "index.txt";
	
	final String DATA_SPLIT = "=||=";
	final String NEW_LINE = "=new=";
	final String END_LINE = "=end=";
	
	private int count;
	
	public BbsController() {
		count = 0;
		count = createDB();
	}
	
	/** ����� �� �� �б�
	 * 
	 * @return
	 */
	private int createDB() {
		String cnt = FileUtil.readNioBbs(DIR, IDX_NAME);
		if ( isDigit(cnt) == true) {
			return Integer.parseInt(cnt);
		}
		return 0;
	}

	/**
	 * �����Ǵ�.
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str)
	{
		char[] arr = str.toCharArray();
		
		int idx = 0;
		int size = arr.length;
		
		if (size == 0)
		{
			return false;
		}
		
		for (idx = 0; idx < size; idx++) {
			if ( !(arr[idx] >= '0' && arr[idx] <= '9')) {
				return false;
			}
		}
		return true;
	}
	
	//�Խ��Ǳ� �߰�
	public void createBbs(String str) {
	    String cont = str;
	    //���� ����
	    FileUtil.writeNioBbs(DIR, NAME, cont);
	    
	    //�Խ��� ��������
	    count++;
	    FileUtil.writeNioOverwrite(DIR, IDX_NAME, count + "");
	}
	
	public String readBbs(String search) {
		String str = "";
		// ���� ���� ����
		str = FileUtil.readNioBbs(DIR, NAME);
		//�� �Խñ� ������ �и�
		String[] sArr = str.split(END_LINE);
		
		for ( int i = 0; i < sArr.length; i++)
		{
			String[] sArr2 = sArr[i].split(DATA_SPLIT);
			String temp = sArr2[0].replace(DATA_SPLIT, "");
			//���� ��ȣ �ִ��� üũ
			if ( Arrays.equals(temp.toCharArray(), search.toCharArray()) ) {
				//������ �Խñ۳��� ����
				return sArr[i];
			}
		}
		//���� ��ȣ ������ null ����
		return null;
	}
	
	public void updateBbs(String search, String content) {
		String str = "";
		//���Ͽ��� �о��.
		str = FileUtil.readNioBbs(DIR, NAME);
		String[] sArr = str.split(END_LINE);
		str = "";
		for ( int i = 0; i < sArr.length; i++)
		{
			String[] sArr2 = sArr[i].split(DATA_SPLIT);
			String temp = sArr2[0].replace(DATA_SPLIT, "");
			//���� �Խñ� ��ȣ Ȯ��
			if ( Arrays.equals(temp.toCharArray(), search.toCharArray()) ) {
				//������ ������ ������ ��ü
				sArr[i] = content;
			}
			//������ split���� ���������Ƿ� �߰�.
			str += sArr[i] + END_LINE;
		}
		//���� ���
		FileUtil.writeNioOverwrite(DIR, NAME, str);
	}
	
	public void removeBbs(int number) {
		String str = "";
		//���� �о��.
		str = FileUtil.readNioBbs(DIR, NAME);
		String[] sArr = str.split(END_LINE);
		str = "";
		for ( int i = 0; i < sArr.length; i++)
		{
			String[] sArr2 = sArr[i].split(DATA_SPLIT);
			String temp = sArr2[0].replace(DATA_SPLIT, "");
			//���� �۹�ȣ�� �ִ��� Ȯ��.
			if ( temp.equals(number+"") == false ) {
				//���� �۹�ȣ������ �����ϰ� ������ ���ڿ� ����.
				str += sArr[i] + END_LINE;
			}
		}
		//������ �Խñ� ���� ������ ����
		FileUtil.writeNioOverwrite(DIR, NAME, str);
		//��ü �� �������� 1�� �������� ����.
		count--;
		FileUtil.writeNioOverwrite(DIR, IDX_NAME, count + "");
	}
	
	public String listBbs() {
		//��ü�� ����
		return FileUtil.readNioBbs(DIR, NAME);
	}
	
	int getCount()  {
		return count;
	}
	
	String getDate() {
		return FileUtil.getDate();
	}
}