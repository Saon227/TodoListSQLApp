package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);

		System.out.print(" ������ �Է��ϼ��� > ");
		title = sc.next().trim();
		
		if (list.isDuplicate(title)) {
			System.out.println(" -> ������ �ߺ��Ǿ����ϴ�.");
			return;
		}
		
		System.out.print(" ī�װ��� �Է��ϼ��� > ");
		category = sc.next().trim();
		
		sc.nextLine();
		System.out.print(" ������ �Է��ϼ��� > ");
		desc = sc.nextLine().trim();
		
		System.out.print(" ������¥�� �Է��ϼ��� > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
//		list.addItem(t);
		if(list.addItem(t)>0)
			System.out.println(" -> ����Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print(" ������ �׸��� ��ȣ�� �Է��ϼ��� > ");
		int num = sc.nextInt();
		
		if (num < 1 || l.getList().size() < num) {
			System.out.println(" -> �ش� ��ȣ�� �������� �ʽ��ϴ�.");
			return;
		}
		
		if(l.deleteItem(num) > 0)
			System.out.println(" -> �����Ǿ����ϴ�.");
		
//		int index = 0;
//		for (TodoItem item : l.getList()) {
//			index++;
//			if (index == num) {
//				System.out.println(" " + index + ". "+ item.toString());
//				System.out.print(" �� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
//				String flag = sc.next().trim();
//				
//				if(flag.equals("y")) {
//					l.deleteItem(item);
//					System.out.println(" -> �����Ǿ����ϴ�.");
//				}
//				break;
//			}
//		}
		//System.out.println(" " + num + ". "+ getItem(num-1).toString());
		
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		System.out.print(" ������ �׸��� ��ȣ�� �Է��ϼ��� > ");
		int num = sc.nextInt();
		
		if (num < 1 || l.getList().size() < num) {
			System.out.println(" -> �ش� ��ȣ�� �������� �ʽ��ϴ�.");
			return;
		}
		
		System.out.println(" " + num + ". "+ l.getList().get(num-1).toString());

		System.out.print(" ���ο� ������ �Է��ϼ��� > ");
		String new_title = sc.next().trim();
		
		if (l.isDuplicate(new_title)) {
			System.out.println(" -> ������ �ߺ��Ǿ����ϴ�.");
			return;
		}
		
		System.out.print(" ������ ī�װ��� �Է��ϼ��� > ");
		String new_category = sc.next().trim();
		
		sc.nextLine();
		System.out.print(" ���ο� ������ �Է��ϼ��� > ");
		String new_description = sc.nextLine().trim();
		
		System.out.print(" ���ο� ������¥�� �Է��ϼ��� > ");
		String new_due_date = sc.nextLine().trim();
		
//		int index = 0;
//		for (TodoItem item : l.getList()) {
//			index++;
//			if (index == num) {
//				l.deleteItem(item);
//				TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
//				l.addItem(t);
//				System.out.println(" -> �����Ǿ����ϴ�.");
//				break;
//			}
//		}
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		t.setId(num);
		if(l.updateItem(t) > 0)
			System.out.println(" -> �����Ǿ����ϴ�.");
	}
	
	public static void listCategory(TodoList l) {
		// List �غ�
		List<String> list = new ArrayList();
		for (TodoItem item : l.getList()) {
			list.add(item.getCategory());
		}
		// List�� Set���� ����
		Set<String> set = new HashSet<String>(list);
		// Set�� List�� ����
		List<String> newList =new ArrayList<String>(set);

		for (String cate : newList) {
			System.out.print(" " + cate);
			if(newList.indexOf(cate) != (newList.size()-1)) {
				System.out.print(" /");
			} else {
				System.out.println();
			}
		}		
		System.out.println(" -> �� " + newList.size() + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}

	
	public static void findKeyword(TodoList l, String keyword) {
		int index = 0;
		int count = 0;
		for (TodoItem item : l.getList()) {
			index++;
			if (item.getTitle().contains(keyword) || item.getDesc().contains(keyword)) {
				System.out.println(" " + index + ". "+ item.toString());
				count++;
			}
		}
		System.out.println(" -> �� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void findCategory(TodoList l, String cate) {
		int index = 0;
		int count = 0;
		for (TodoItem item : l.getList()) {
			index++;
			if (item.getCategory().contains(cate)) {
				System.out.println(" " + index + ". "+ item.toString());
				count++;
			}
		}
		System.out.println(" -> �� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void listAll(TodoList l) {
		//System.out.println("[��ü ��� (�� " + l.getList().size() + "��)]");
		// -> TodoMain.java����
		int index = 0;
		for (TodoItem item : l.getList()) {
			index++;
			System.out.println(" " + index + ". "+ item.toString());
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		//BufferedReader, FileReader, StringTokenizer ���
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String oneline;
			int count = 0;
			while((oneline = br.readLine()) != null) {
				count++;
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem t = new TodoItem(title, desc, current_date, category, due_date);
				l.addItem(t);
			}
			br.close();
			System.out.println(count + "���� �׸��� �о����ϴ�.");
			
		} catch(FileNotFoundException e) {
			System.out.println(filename + " ������ �����ϴ�.");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		//FileWriter ���
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			
			System.out.println(" -> ��� �����Ͱ� ����Ǿ����ϴ�.");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
}
