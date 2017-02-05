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
	
	/** 저장된 글 수 읽기
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
	 * 숫자판단.
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
	
	//게시판글 추가
	public void createBbs(String str) {
	    String cont = str;
	    //파일 저장
	    FileUtil.writeNioBbs(DIR, NAME, cont);
	    
	    //게시판 갯수저장
	    count++;
	    FileUtil.writeNioOverwrite(DIR, IDX_NAME, count + "");
	}
	
	public String readBbs(String search) {
		String str = "";
		// 파일 내용 읽음
		str = FileUtil.readNioBbs(DIR, NAME);
		//각 게시글 단위로 분리
		String[] sArr = str.split(END_LINE);
		
		for ( int i = 0; i < sArr.length; i++)
		{
			String[] sArr2 = sArr[i].split(DATA_SPLIT);
			String temp = sArr2[0].replace(DATA_SPLIT, "");
			//같은 번호 있는지 체크
			if ( Arrays.equals(temp.toCharArray(), search.toCharArray()) ) {
				//있으면 게시글내용 리턴
				return sArr[i];
			}
		}
		//같은 번호 없으면 null 리턴
		return null;
	}
	
	public void updateBbs(String search, String content) {
		String str = "";
		//파일에서 읽어옴.
		str = FileUtil.readNioBbs(DIR, NAME);
		String[] sArr = str.split(END_LINE);
		str = "";
		for ( int i = 0; i < sArr.length; i++)
		{
			String[] sArr2 = sArr[i].split(DATA_SPLIT);
			String temp = sArr2[0].replace(DATA_SPLIT, "");
			//같은 게시글 번호 확인
			if ( Arrays.equals(temp.toCharArray(), search.toCharArray()) ) {
				//있으면 수정한 정보로 대체
				sArr[i] = content;
			}
			//위에서 split으로 구분했으므로 추가.
			str += sArr[i] + END_LINE;
		}
		//내용 덮어씀
		FileUtil.writeNioOverwrite(DIR, NAME, str);
	}
	
	public void removeBbs(int number) {
		String str = "";
		//파일 읽어옴.
		str = FileUtil.readNioBbs(DIR, NAME);
		String[] sArr = str.split(END_LINE);
		str = "";
		for ( int i = 0; i < sArr.length; i++)
		{
			String[] sArr2 = sArr[i].split(DATA_SPLIT);
			String temp = sArr2[0].replace(DATA_SPLIT, "");
			//같은 글번호가 있는지 확인.
			if ( temp.equals(number+"") == false ) {
				//같은 글번호내용을 제외하고 나머지 문자열 읽음.
				str += sArr[i] + END_LINE;
			}
		}
		//삭제된 게시글 외의 글정보 저장
		FileUtil.writeNioOverwrite(DIR, NAME, str);
		//전체 글 갯수에서 1을 감소한후 저장.
		count--;
		FileUtil.writeNioOverwrite(DIR, IDX_NAME, count + "");
	}
	
	public String listBbs() {
		//전체글 리턴
		return FileUtil.readNioBbs(DIR, NAME);
	}
	
	int getCount()  {
		return count;
	}
	
	String getDate() {
		return FileUtil.getDate();
	}
}