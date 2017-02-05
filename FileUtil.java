package com.donggoo.bbs;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.nio.file.LinkOption;

public class FileUtil {

	public static String getDate() {
		String datetime = "";
		
		//날짜, 시간 정보를 생성하여 받아옴.
		Calendar cal = Calendar.getInstance();
		
		int[] iArr= new int[5];
		
		int yy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;	//월 정보는 0~11이기 때문에 1을 더함.
		int dd = cal.get(Calendar.DATE);
		
		int ho = cal.get(Calendar.HOUR_OF_DAY);	//24시간으로 표시된 시간
		int mi = cal.get(Calendar.MINUTE);
		int se = cal.get(Calendar.SECOND);
		
		iArr[0] = mm;
		iArr[1] = dd;
		iArr[2] = ho;
		iArr[3] = mi;
		iArr[4] = se;		
		
		datetime = yy +"-";
		
		int idx = 0;
		for ( idx = 0; idx < 5; idx++) {

			if ( iArr[idx] < 10) {	//월, 일, 시, 분, 초가  2자리가 아니면 앞에 0을 더해줌. 
				datetime = datetime + 0;
			}
			
			switch(idx) {
			case 0 :	//month
				datetime = datetime + iArr[idx] + "-";
				break;
			case 1 :	//date
				datetime = datetime + iArr[idx] + " ";
				break;
			case 2 :	//hour
				datetime = datetime + iArr[idx] + ":";
				break;
			case 3 :	//minute
				datetime = datetime + iArr[idx] + ":";
				break;
			case 4 :	//second
				datetime = datetime + iArr[idx];
				break;
			}
		}
		return datetime;		
	}
	
	public static String readNioBbs(String dir, String filename) {
		
		//경로
		Path path = Paths.get(dir, filename);
		String content = "";
		//파일이 있는지 확인
		//boolean pathExists = Files.exists(path,new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
		//http://tutorials.jenkov.com/java-nio/files.html
		if ( Files.exists(path,new LinkOption[]{ LinkOption.NOFOLLOW_LINKS}) == true) {
			try {
				byte rawData[] = Files.readAllBytes(path);
				//Format을 맞춰서 byte배열을 String 객체로 생성
				content = new String(rawData, StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				return content;
			}
		}
		else {	//파일이 없으면.
			return null;
		}
	}

	public static void writeNioBbs(String dir, String filename, String content) {
		//경로
		Path path = Paths.get(dir, filename);
		try {
			//파일에 쓰기.
			Files.write(path, content.getBytes(StandardCharsets.UTF_8)	//파일 포맷
					, StandardOpenOption.CREATE, StandardOpenOption.APPEND );	//없으면 생성, 있으면 내용 붙이기.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeNioOverwrite(String dir, String filename, String content) {
		//경로
		Path path = Paths.get(dir, filename);
		try {
			//파일에 저장.
			Files.write(path, content.getBytes(StandardCharsets.UTF_8)	//파일 포맷
					, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);	//파일이 없으면 생성, 파일이 있으면 무시하고 생성.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}