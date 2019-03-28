package tv.zhangjia.bike.util;

import java.util.List;
import java.util.Scanner;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.entity.Bike;
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
	private BikeDao bikedao = new BikeDaoImpl();

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
		System.out.print("��ѡ�����������Ĳ���:");
		int nextInt = input.nextInt();
		switch (nextInt) {
		case 1:
			queryBike();
			break;
		case 2:
			saveBike();
			break;
		case 3:
			editBike();
			break;
		case 4:
			deleteBike();
			break;
		case 5:
			personInfo();
			break;
		case 6:
			queryRecord();
			break;
		case 7:
			logout();// �˳���¼
			break;
		case 8:
			exit();// �˳�
			break;

		}

	}

	private void logout() {
		// TODO Auto-generated method stub

	}

	private void queryRecord() {
		// TODO Auto-generated method stub

	}

	private void personInfo() {
		// TODO Auto-generated method stub

	}

	private void deleteBike() {
		// TODO Auto-generated method stub
		System.out.println("��������Ҫɾ���ĵ���ID��");
		int id = input.nextInt();
		
		
		if(bikedao.queryById(id) == null) {
			System.out.println("�����ڴ�id");
			
		} else {
			if(bikedao.doDelete(id) ) {
				System.out.println("ɾ���ɹ�");
			} else {
				System.out.println("ɾ��ʧ��");
			}
		}
		returnMenu();
	}

	private void editBike() {
		// TODO Auto-generated method stub
		List<Bike> bikes = bikedao.queryAll();
		System.out.println("��������Ҫ�޸ĵĵ���ID");
		int id = input.nextInt();
		System.out.println("�����뵥�����ͣ�");
		String type = input.next();
		System.out.println("������۸�");
		double price = input.nextDouble();
		System.out.println("������λ�ã�");
		String location = input.next();
		System.out.println("������״̬��");
		int status = input.nextInt();
		System.out.println("�����������");
		int amount = input.nextInt();
		System.out.println("������qr");
		String qr = input.next();

		Bike bike = bikedao.queryById(id);
		if (bike == null) {
			System.out.println("û�и�ID");
		} else {
			Bike bike2 = new Bike(type, price, location, status, amount, qr);
			boolean doUpdate = bikedao.doUpdate(bike2);
			if (doUpdate) {
				System.out.println("�޸ĳɹ�");
			} else {
				System.out.println("�޸�ʧ��");
			}
		}

		returnMenu();

	}

	private void saveBike() {
		System.out.println("��ӵ���");
		System.out.println("�����뵥�����ͣ�");
		String type = input.next();
		System.out.println("������۸�");
		double price = input.nextDouble();
		System.out.println("������λ�ã�");
		String location = input.next();
		System.out.println("������qr");
		String qr = input.next();

		Bike bike = new Bike(type, price, location, 1, 0, "��ά��");
		boolean doInsert = bikedao.doInsert(bike);
		if (doInsert) {
			System.out.println("��ӳɹ�");
		} else {
			System.out.println("���ʧ��");
		}

		System.out.println();
		returnMenu();
	}

	private void queryBike() {
		// TODO Auto-generated method stub
		System.out.println("���������еĵ���");
		List<Bike> bike = bikedao.queryAll();
		System.out.println("���\t����\t�۸�\tλ��\t״̬\t����\t��ά��");
		for (Bike bike2 : bike) {
			System.out.println(bike2);
		}
		returnMenu();
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
		String password, password2;
		while (true) {
			System.out.print("�������������룺");
			password = input.next();
			System.out.print("�ٴ������������룺");
			password2 = input.next();
			if (!password.equals(password2)) {
				System.out.println("�������벻һ�£����ٴ�����");
			} else {
				break;
			}
		}

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
		if (next.equals("1")) {
			if (user.isAdmin()) {
				adminMenu();// ����Ա�˵�
			} else {
				userMenu();// ��ͨ�û��˵�
			}
		} else {
			exit();
		}
	}

}
