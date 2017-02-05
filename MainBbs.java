package com.donggoo.bbs;

import java.io.File;
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class MainBbs {
	final static String DATA_SPLIT = "=||=";	//각 항목 구분자.
	final static String NEW_LINE = "=new=";		//contents의 줄변경 구분자
	final static String END_LINE = "=end=";		//1개의 게시판 구성을 구분. 
	
	private final static String NAME = "database.txt";	//실제 데이터를 가지고 있는 파일	
	private final static String DIR = "C:" + File.separator + "Users" + File.separator + "dongg" + File.separator + "Desktop"+ File.separator + "Project"+ File.separator+ "Project"+ File.separator; // 파일저장이 되는 경로.
	private final static String IDX_NAME = "index.txt";	//게시판 갯수를 가지는 파일
	
	public static void main(String[] args) {
		MainBbs mb = new MainBbs();
		mb.runfile();
	}
	
	public void runfile() {
		BbsController control = new BbsController();
		Scanner scanner = new Scanner(System.in);
		
		String command = "";
		
		boolean flag = true;
		while(flag) {
			print_menu();
			System.out.println("명령어를 입력하세요");	
			command = scanner.nextLine();
			
			if ( command.equals("create")) {
				create(scanner, control);
			}
			else if ( command.equals("remove")) {			
				remove(scanner, control);
			}
			else if ( command.equals("update")) {
				update(scanner, control);
			}
			else if ( command.equals("read")) {
				read(scanner, control);
			}
			else if ( command.equals("list")) {
				list(control);
			}
			else if ( command.equals("exit")) {
				flag = false;
				System.out.println("Turn Off. see you!");	
			}
			else
			{
				System.out.println("");
				System.out.println("잘못입력... 재입력");
				System.out.println("");
			}
		}
	}

	public static void create(Scanner scanner, BbsController control) {

	    String Input = ""; 
	    String scanline = "";

	    System.out.println("제목을 입력하세요.");
	    scanline = scanner.nextLine();
	    Input = scanline + DATA_SPLIT;

	    System.out.println("작성자를 입력하세요.");
	    scanline = scanner.nextLine();
	    Input += scanline + DATA_SPLIT;
	    
	    System.out.println("내용을 입력하세요.(입력 완료 : wq)");

		//내용입력... wq를 입력해야만 루틴멈춤.
	    boolean in = true;
	    while (in) {
	        scanline = scanner.nextLine();

	        if ( Arrays.equals(scanline.toCharArray(), "wq".toCharArray())) {
	            in = false;
	            Input += DATA_SPLIT;
	        }else {
	            Input = Input+ scanline + NEW_LINE;
	        }
	    }

	    String str = FileUtil.readNioBbs(DIR, IDX_NAME);	//미리저장된 게시글이 있는지 파악

	    int count = 0;
	    if ( isDigit(str) == true && str.length() != 0) {
	        count = Integer.parseInt(str);
	        count++;	//미리 저장된것이 있다면 갯수에 1 추가.
	    }
	    else {
	        count = 1;
	    }

	    Input = count + DATA_SPLIT + Input;	//글번호 앞에 추가.

	    Input = Input + control.getDate() + END_LINE;	//입력일자 추가후 게시글 정보 마지막을 더함.

	    control.createBbs(Input);	//파일 저장.

	    System.out.println(".....저장완료......");
	}

	public static void read(Scanner scanner, BbsController control) {
	    System.out.println("글번호를 입력하세요.");
	    String tStr = "";
	    int idx = 2;

	    while(idx > 0) {	//숫자를 입력하지 않았을 경우 1번 기회를 더줌.
	        tStr = scanner.nextLine();
	        if ( isDigit(tStr) == true) {
	            idx = -1;
	        }
	        else {
	            System.out.println("숫자를 입력해주세요.");
	            idx--;
	        }
	    }
	    if ( idx == -1) {	//제대로 입력되었으면.
	        String str = control.readBbs(tStr);	//파일에서 같은 글번호가 있는지 확인
	        if ( str == null)
	        {
	            System.out.println("입력한 글번호는 없습니다");
	        }
	        else
	        {
	            print(str);		//같은 게시글번호가 있으면 프린트.
	        }
	    }
	    else {
	        System.out.println("사용방법을 확인해주세요.");
	    }
	}
	
	public static void update(Scanner scanner, BbsController control) {
	    System.out.println("글번호를 입력하세요.");
	    String tStr = "";
	    int idx = 2;
	    int num = 0;

	    while(idx > 0) {	//숫자 입력을 확인하고 잘못입력시 기회 더줌.
	        tStr = scanner.nextLine();
	        if ( isDigit(tStr) == true) {
	            num = Integer.parseInt(tStr);
	            idx = -1;
	        }
	        else {
	            System.out.println("숫자를 입력해주세요.");
	            idx--;
	        }
	    }
	    if ( idx == -1) {	//제대로 입력되면..
	        String str = control.readBbs(tStr);
	        if ( str == null)
	        {
	            System.out.println("입력한 글번호는 없습니다");
	        }
	        else
	        {
	            print(str);	//수정하고자하는 글 내용 보여줌.
	            System.out.println("수정하시겠습니까?? (yes? no?)");
	            tStr = scanner.nextLine();
	        }
	    }
	    else {
	        System.out.println("사용방법을 확인해주세요.");
	    }
	    if (tStr.equals("yes") == true) {	//수정을 원하면..

	        String Input = ""; 
	        String scanline = "";

			//이곳은 create 부분과 거의 같음.
	        System.out.println("제목을 입력하세요.");
	        scanline = scanner.nextLine();
	        Input = scanline + DATA_SPLIT;

	        System.out.println("작성자를 입력하세요.");
	        scanline = scanner.nextLine();
	        Input += scanline + DATA_SPLIT;
	        boolean in = true;
	        System.out.println("내용을 입력하세요.(입력 완료 : wq");

	        while (in) {
	            scanline = scanner.nextLine();

	            if ( Arrays.equals(scanline.toCharArray(), "wq".toCharArray())) {
	                in = false;
	                Input += DATA_SPLIT;
	            }else {
	                Input = Input+ scanline + NEW_LINE;
	            }
	        }

	        Input = num + DATA_SPLIT + Input;

	        Input = Input + control.getDate();	//여기서는 게시글 끝 구분자를 넣지 않음.

	        control.updateBbs(num + "", Input);

	        System.out.println(".....수정완료......");
	    }
	}
	
	public static void remove(Scanner scanner, BbsController control) {

	    System.out.println("글번호를 입력하세요.");
	    String tStr = "";
	    int idx = 2;
	    int num = 0;

	    while(idx > 0) {	//글번호 이므로 숫자 입력을 체크
	        tStr = scanner.nextLine();
	        if ( isDigit(tStr) == true) {
	            num = Integer.parseInt(tStr);
	            idx = -1;
	        }
	        else {
	            System.out.println("숫자를 입력해주세요.");
	            idx--;
	        }
	    }
	    if ( idx == -1) {	//숫자면..
	        String str = control.readBbs(tStr);
	        if ( str == null)
	        {
	            System.out.println("입력한 글번호는 없습니다");
	        }
	        else
	        {
	            print(str);		//내용 보여줌.
	            System.out.println("정말 삭제 하시겠습니까?? (yes? no?)");
	            tStr = scanner.nextLine();
	        }
	    }
	    else {
	        System.out.println("사용방법을 확인해주세요.");
	    }
	    if (tStr.equals("yes") == true) {	//삭제를 원하면.
	        control.removeBbs(num);	//삭제.

	        System.out.println(".....수정완료......");
	    }
	}
	
	
	public static void list(BbsController control) {
		String[] printAll = control.listBbs().split(END_LINE);
		
		for (String item : printAll) {
			prints(item);
		}
	}
	
	public static void print_menu() {

		System.out.println("");
		System.out.println("----------------");
		System.out.println("--- 명령어 SET ----");
		System.out.println("--- create -----");
		System.out.println("--- remove -----");
		System.out.println("--- update -----");
		System.out.println("--- read -------");
		System.out.println("--- list -------");
		System.out.println("--- exit -------");
		System.out.println("----------------");
	}
	
	public static boolean isDigit(String str)
	{
		char[] arr = str.toCharArray();
		
		int idx = 0;
		int size = arr.length;
		
		for (idx = 0; idx < size; idx++) {
			if ( !(arr[idx] >= '0' && arr[idx] <= '9')) {
				return false;
			}
		}
		return true;
	}

	static public void print(String value) {
		value = value.replace(DATA_SPLIT, ":");
		String[] split = value.split(":");
		value = "";
		
		int idx = 0;
		for (; idx <5; idx++) {
			switch (idx) {
				case 0:
					value = " No. : " + split[idx].replace(":", "\r\n");
				break;
				case 1:
					value = " Author : " + split[idx].replace(":", "\r\n");
				break;
				case 2:
					value = " Title : " + split[idx].replace(":", "\r\n");
				break;
				case 3:
					String tmp = split[idx].replaceAll(":", "\r\n");
					tmp = tmp.replace(NEW_LINE, "\r\n");
					value = " Contents : " + tmp;
				break;
				case 4:
					value = " Date : " + split[idx].replace(DATA_SPLIT, "\r\n");
				break;
			}
			System.out.println(value);
		}
	}

	static public void prints(String value) {
		value = value.replace(DATA_SPLIT, ":");
		String[] split = value.split(":");
		value = "";
		
		int idx = 0;
		for (; idx <5; idx++) {
			switch (idx) {
				case 0:
					value += " No. : " + split[idx].replace(":", "");
				break;
				case 1:
					value += " Author : " + split[idx].replace(":", "");
				break;
				case 2:
					value += " Title : " + split[idx].replace(":", "");
				break;
			}
		}
		System.out.println(value);
	}
}
