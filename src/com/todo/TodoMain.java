package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		//TodoUtil.loadList(l, "todolist.txt");
		l.importData("todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				System.out.println("\n[�׸� �߰�]");
				TodoUtil.createItem(l);
				break;
			
			case "del":
				System.out.println("\n[�׸� ����]");
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				System.out.println("\n[�׸� ����]");
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				System.out.println("\n[��ü ��� (�� " + l.getList().size() + "��)]");
				isList = true; //TodoUtil.listAll(l);
				break;
				
			case "ls_cate":
				System.out.println("\n[ī�װ� ���]");
				TodoUtil.listCategory(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				System.out.println("\n[����� ����]");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("\n[���񿪼� ����]");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				System.out.println("\n[��¥�� ����]");
				isList = true;
				break;
				
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				System.out.println("\n[��¥���� ����]");
				isList = true;
				break;
				
			case "find":
				String keyword = sc.next().trim();
				System.out.println("\n[�׸� �˻� ���]");
				TodoUtil.findKeyword(l, keyword);
				break;
				
			case "find_cate":
				String cate = sc.next().trim();
				System.out.println("\n[ī�װ� �˻� ���]");
				TodoUtil.findCategory(l, cate);
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				System.out.println(" -> �����մϴ�.");
				break;

			default:
				System.out.println(" -> ��Ȯ�� ��ɾ �Է��ϼ���. (������ - help)");
				break;
			}
			
			if(isList) TodoUtil.listAll(l);
		} while (!quit);
		TodoUtil.saveList(l, "todolist.txt");
	}
}
