package tv.zhangjia.bike.util;

import java.util.Scanner;

import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.entity.User;

/**
 * ���˵���
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.util
 * @ClassName Menu
 * @author ZhangJia
 * @Version v1.0
 * @date 2019��3��25�� ����6:37:45
 */
public class Menu {
	private Scanner input = new Scanner(System.in);
	private User user = null;
	private UserDao userdao = new UserDaoImpl();

	/**
	 * ���˵��������ϵͳ���û������ĵ�һ������
	 * 
	 * @Title mainMenu
	 */
	public void mainMenu() {
		System.out.println("---��ӭ��ʹ�ù���������ϵͳ---");
		System.out.println("\t1.��¼");
		System.out.println("\t2.ע��");
		System.out.println("\t3.�˳� ");
		System.out.println("-------�㿴���ҵĵ�����-------");
		System.out.print("��ѡ�����������Ĳ���:");
		int choose = input.nextInt();
		switch (choose) {
		case 1:
			userLogin();
			break;
		case 2:
			userRegister();
			break;
		case 3:
			exit();
			break;
		}
		returnMenu();
	}

	/**
	 * �û���¼
	 */
	private void userLogin() {
		System.out.print("�����������û�����");
		String username = input.next();
		System.out.print("�������������룺");
		String password = input.next();
		User login = userdao.login(username, password);

		if (login == null) {
			System.out.println("��¼ʧ�ܣ������û��������������");
		} else {
			this.user = login;
			if (user.isAdmin()) {
				adminMenu();
			} else {
				userMenu();
			}
		}

	}

	/**
	 * ����Ա������
	 */
	private void adminMenu() {
		System.out.println("�𾴵�" + user.getUsername() + "����Ա�����ã�");
		System.out.println("\t1.��ѯ����");
		System.out.println("\t2.��ӵ���");
		System.out.println("\t3.�޸ĵ���");
		System.out.println("\t4.ɾ������");
		System.out.println("\t5.�û���Ϣ");
		System.out.println("\t6.���޼�¼");
		System.out.println("\t7.�˳���¼");
		System.out.println("\t8.�˳�ϵͳ");
	}

	/**
	 * ��ͨ�û�������
	 */
	private void userMenu() {
		System.out.println("�𾴵�" + user.getUsername() + "�û������ã�");
		System.out.println("\t1.��ѯ����");
		System.out.println("\t2.��赥��");
		System.out.println("\t3.�黹����");
		System.out.println("\t4.ɾ������");
		System.out.println("\t5.������Ϣ");
		System.out.println("\t6.���޼�¼");
		System.out.println("\t7.�˳���¼");
		System.out.println("\t8.�˳�ϵͳ");

	}

	/**
	 * �û�ע��
	 * 
	 * @Title userRegister
	 */
	private void userRegister() {
		System.out.print("�����������û�����");
		String username = input.next();
		System.out.print("�������������룺");
		String password = input.next();
		int register = userdao.register(username, password);

		if (register == -1) {
			System.out.println("ע��ʧ�ܣ������û����Ѿ�����");
		} else {
			System.out.println("ע��ɹ�");
			userLogin();
		}
	}

	/**
	 * �û��˳�ϵͳ
	 * 
	 * @Title exit
	 */
	private void exit() {
		input.close();
		System.out.println("���Ѿ��˳�ϵͳ��û�к�ڵ������");
		System.exit(0);
	}

	/**
	 * ���ص�mainMenu
	 * 
	 * @Title returnMenu
	 */
	private void returnMenu() {
		System.out.println("����1����,������˳�");
		String next = input.next();
		if(next.equals("1")) {
			mainMenu();
		} else {
			exit();
		}
	}

}
