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
	final static String DATA_SPLIT = "=||=";	//�� �׸� ������.
	final static String NEW_LINE = "=new=";		//contents�� �ٺ��� ������
	final static String END_LINE = "=end=";		//1���� �Խ��� ������ ����. 
	
	private final static String NAME = "database.txt";	//���� �����͸� ������ �ִ� ����	
	private final static String DIR = "C:" + File.separator + "Users" + File.separator + "dongg" + File.separator + "Desktop"+ File.separator + "Project"+ File.separator+ "Project"+ File.separator; // ���������� �Ǵ� ���.
	private final static String IDX_NAME = "index.txt";	//�Խ��� ������ ������ ����
	
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
			System.out.println("��ɾ �Է��ϼ���");	
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
				System.out.println("�߸��Է�... ���Է�");
				System.out.println("");
			}
		}
	}

	public static void create(Scanner scanner, BbsController control) {

	    String Input = ""; 
	    String scanline = "";

	    System.out.println("������ �Է��ϼ���.");
	    scanline = scanner.nextLine();
	    Input = scanline + DATA_SPLIT;

	    System.out.println("�ۼ��ڸ� �Է��ϼ���.");
	    scanline = scanner.nextLine();
	    Input += scanline + DATA_SPLIT;
	    
	    System.out.println("������ �Է��ϼ���.(�Է� �Ϸ� : wq)");

		//�����Է�... wq�� �Է��ؾ߸� ��ƾ����.
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

	    String str = FileUtil.readNioBbs(DIR, IDX_NAME);	//�̸������ �Խñ��� �ִ��� �ľ�

	    int count = 0;
	    if ( isDigit(str) == true && str.length() != 0) {
	        count = Integer.parseInt(str);
	        count++;	//�̸� ����Ȱ��� �ִٸ� ������ 1 �߰�.
	    }
	    else {
	        count = 1;
	    }

	    Input = count + DATA_SPLIT + Input;	//�۹�ȣ �տ� �߰�.

	    Input = Input + control.getDate() + END_LINE;	//�Է����� �߰��� �Խñ� ���� �������� ����.

	    control.createBbs(Input);	//���� ����.

	    System.out.println(".....����Ϸ�......");
	}

	public static void read(Scanner scanner, BbsController control) {
	    System.out.println("�۹�ȣ�� �Է��ϼ���.");
	    String tStr = "";
	    int idx = 2;

	    while(idx > 0) {	//���ڸ� �Է����� �ʾ��� ��� 1�� ��ȸ�� ����.
	        tStr = scanner.nextLine();
	        if ( isDigit(tStr) == true) {
	            idx = -1;
	        }
	        else {
	            System.out.println("���ڸ� �Է����ּ���.");
	            idx--;
	        }
	    }
	    if ( idx == -1) {	//����� �ԷµǾ�����.
	        String str = control.readBbs(tStr);	//���Ͽ��� ���� �۹�ȣ�� �ִ��� Ȯ��
	        if ( str == null)
	        {
	            System.out.println("�Է��� �۹�ȣ�� �����ϴ�");
	        }
	        else
	        {
	            print(str);		//���� �Խñ۹�ȣ�� ������ ����Ʈ.
	        }
	    }
	    else {
	        System.out.println("������� Ȯ�����ּ���.");
	    }
	}
	
	public static void update(Scanner scanner, BbsController control) {
	    System.out.println("�۹�ȣ�� �Է��ϼ���.");
	    String tStr = "";
	    int idx = 2;
	    int num = 0;

	    while(idx > 0) {	//���� �Է��� Ȯ���ϰ� �߸��Է½� ��ȸ ����.
	        tStr = scanner.nextLine();
	        if ( isDigit(tStr) == true) {
	            num = Integer.parseInt(tStr);
	            idx = -1;
	        }
	        else {
	            System.out.println("���ڸ� �Է����ּ���.");
	            idx--;
	        }
	    }
	    if ( idx == -1) {	//����� �ԷµǸ�..
	        String str = control.readBbs(tStr);
	        if ( str == null)
	        {
	            System.out.println("�Է��� �۹�ȣ�� �����ϴ�");
	        }
	        else
	        {
	            print(str);	//�����ϰ����ϴ� �� ���� ������.
	            System.out.println("�����Ͻðڽ��ϱ�?? (yes? no?)");
	            tStr = scanner.nextLine();
	        }
	    }
	    else {
	        System.out.println("������� Ȯ�����ּ���.");
	    }
	    if (tStr.equals("yes") == true) {	//������ ���ϸ�..

	        String Input = ""; 
	        String scanline = "";

			//�̰��� create �κа� ���� ����.
	        System.out.println("������ �Է��ϼ���.");
	        scanline = scanner.nextLine();
	        Input = scanline + DATA_SPLIT;

	        System.out.println("�ۼ��ڸ� �Է��ϼ���.");
	        scanline = scanner.nextLine();
	        Input += scanline + DATA_SPLIT;
	        boolean in = true;
	        System.out.println("������ �Է��ϼ���.(�Է� �Ϸ� : wq");

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

	        Input = Input + control.getDate();	//���⼭�� �Խñ� �� �����ڸ� ���� ����.

	        control.updateBbs(num + "", Input);

	        System.out.println(".....�����Ϸ�......");
	    }
	}
	
	public static void remove(Scanner scanner, BbsController control) {

	    System.out.println("�۹�ȣ�� �Է��ϼ���.");
	    String tStr = "";
	    int idx = 2;
	    int num = 0;

	    while(idx > 0) {	//�۹�ȣ �̹Ƿ� ���� �Է��� üũ
	        tStr = scanner.nextLine();
	        if ( isDigit(tStr) == true) {
	            num = Integer.parseInt(tStr);
	            idx = -1;
	        }
	        else {
	            System.out.println("���ڸ� �Է����ּ���.");
	            idx--;
	        }
	    }
	    if ( idx == -1) {	//���ڸ�..
	        String str = control.readBbs(tStr);
	        if ( str == null)
	        {
	            System.out.println("�Է��� �۹�ȣ�� �����ϴ�");
	        }
	        else
	        {
	            print(str);		//���� ������.
	            System.out.println("���� ���� �Ͻðڽ��ϱ�?? (yes? no?)");
	            tStr = scanner.nextLine();
	        }
	    }
	    else {
	        System.out.println("������� Ȯ�����ּ���.");
	    }
	    if (tStr.equals("yes") == true) {	//������ ���ϸ�.
	        control.removeBbs(num);	//����.

	        System.out.println(".....�����Ϸ�......");
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
		System.out.println("--- ��ɾ� SET ----");
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
