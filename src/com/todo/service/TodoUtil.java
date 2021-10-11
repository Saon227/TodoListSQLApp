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
	
	public static void createItem(TodoList l) {

		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);

		System.out.print(" ������ �Է��ϼ��� > ");
		title = sc.next().trim();
		
		if (l.isDuplicate(title)) {
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
		
		TodoItem t = new TodoItem(title, desc, category, due_date, 0);

		if(l.addItem(t)>0)
			System.out.println(" -> ����Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print(" ������ �׸��� ��ȣ�� �Է��ϼ��� > ");
		int id = sc.nextInt();
		
		int flag = 0;
		for(TodoItem item : l.getList()) {
			if(item.getId() == id) {
				System.out.println(" " + id + ". "+ item.toString());
				flag++;
				break;
			}
		}
		
		if(flag == 0) {
			System.out.println(" -> �ش� ��ȣ�� �������� �ʽ��ϴ�.");
			return;
		}
		
		if(l.deleteItem(id) > 0)
			System.out.println(" -> �����Ǿ����ϴ�.");
	}

	public static void updateItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.print(" ������ �׸��� ��ȣ�� �Է��ϼ��� > ");
		int id = sc.nextInt();
		
		int flag = 0;
		for(TodoItem item : l.getList()) {
			if(item.getId() == id) {
				System.out.println(" " + id + ". "+ item.toString());
				flag++;
				break;
			}
		}
		
		if(flag == 0) {
			System.out.println(" -> �ش� ��ȣ�� �������� �ʽ��ϴ�.");
			return;
		}
		
		System.out.print("\n ���ο� ������ �Է��ϼ��� > ");
		String new_title = sc.next().trim();
		
		System.out.print(" ������ ī�װ��� �Է��ϼ��� > ");
		String new_category = sc.next().trim();
		
		sc.nextLine();
		System.out.print(" ���ο� ������ �Է��ϼ��� > ");
		String new_description = sc.nextLine().trim();
		
		System.out.print(" ���ο� ������¥�� �Է��ϼ��� > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date, 0);
		t.setId(id);
		if(l.updateItem(t) > 0)
			System.out.println(" -> �����Ǿ����ϴ�.");
	}
	
	public static void listCategory(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(" " + item);
			count++;
		}
		System.out.println("\n -> �� " + count + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}

	
	public static void findKeyword(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			count++;
			System.out.println(" " + item.getId() + ". "+ item.toString());
		}
		System.out.println(" -> �� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void findCategory(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			count++;
			System.out.println(" " + item.getId() + ". "+ item.toString());
		}
		System.out.println(" -> �� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println(" " + item.getId() + ". "+ item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("\n[��ü ��� (�� " + l.getCount() + "��)]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(" " + item.getId() + ". "+ item.toString());
		}
	}
	
	public static void completeItem(TodoList l, int id) {
		
		int flag = 0;
		for(TodoItem item : l.getList()) {
			if(item.getId() == id) {
				System.out.println(" " + id + ". "+ item.toString());
				flag++;
				break;
			}
		}
		
		if(flag == 0) {
			System.out.println(" -> �ش� ��ȣ�� �������� �ʽ��ϴ�.");
			return;
		}
		
		TodoItem t = new TodoItem(1);
		t.setId(id);
		if(l.compItem(t) > 0)
			System.out.println(" -> �����Ǿ����ϴ�.");
	}
	
	public static void listComplete(TodoList l) {
		System.out.println("\n[�Ϸ� �׸� ���]");
		int count = 0;
		for (TodoItem item : l.getListComplete()) {
			count++;
			System.out.println(" " + item.getId() + ". "+ item.toString());
		}
		System.out.println(" -> �� " + count + "���� �׸��� �Ϸ�Ǿ����ϴ�.");
	}
	
//	public static void loadList(TodoList l, String filename) {
//		//BufferedReader, FileReader, StringTokenizer ���
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(filename));
//			
//			String oneline;
//			int count = 0;
//			while((oneline = br.readLine()) != null) {
//				count++;
//				StringTokenizer st = new StringTokenizer(oneline, "##");
//				String category = st.nextToken();
//				String title = st.nextToken();
//				String desc = st.nextToken();
//				String due_date = st.nextToken();
//				String current_date = st.nextToken();
//				TodoItem t = new TodoItem(title, desc, current_date, category, due_date);
//				l.addItem(t);
//			}
//			br.close();
//			System.out.println(count + "���� �׸��� �о����ϴ�.");
//			
//		} catch(FileNotFoundException e) {
//			System.out.println(filename + " ������ �����ϴ�.");
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public static void saveList(TodoList l, String filename) {
//		//FileWriter ���
//		try {
//			Writer w = new FileWriter(filename);
//			for (TodoItem item : l.getList()) {
//				w.write(item.toSaveString());
//			}
//			w.close();
//			
//			System.out.println(" -> ��� �����Ͱ� ����Ǿ����ϴ�.");
//		} catch(FileNotFoundException e) {
//			e.printStackTrace();
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//	}	
}
