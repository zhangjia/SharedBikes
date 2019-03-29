package tv.zhangjia.bike.util;

import java.util.List;
import java.util.Scanner;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.RecordDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.dao.impl.LeaseRecordDaoImpl;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.dao.impl.WalletDaoImpl;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;

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
	private RecordDao<LeaseRecord> leaseRecordDao = new LeaseRecordDaoImpl();
	private WalletDao walletDao = new WalletDaoImpl();
	private InputIsValid iiv = new InputIsValid();

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

		// ��whileѭ��ʵ�����ѡ����ڣ���������
		while (true) {
			String choose = input.next();
			// �ж�����������Ƿ�Ϊ����
			if (iiv.isNumber(choose)) {
				int index = Integer.parseInt(choose);
				switch (index) {
				case 1:
					userLogin();
					break;
				case 2:
					userRegister();
					break;
				case 33:
					exit();
					break;
				default:
					System.out.print("û�и�ѡ����������룺");
				}
			} else {
				System.out.print("���벻�Ϸ�,�����������룺");
			}

		}
	}

	/**
	 * �û���¼
	 */
	private void userLogin() {
		System.out.println("-----------------------------------");
		System.out.print("�����������û�����");
		String username = input.next();
		System.out.print("�������������룺");
		String password = input.next();
		User login = userdao.login(username, password);

		if (login == null) {
			System.out.println("��¼ʧ�ܣ������û��������������");
			System.out.println("�Ƿ����µ�¼��y");
			String againLogIn = input.next();
			if (againLogIn.equals("y")) {
				userLogin();

			} else {
				mainMenu();
			}
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
		System.out.println("-----------------------------------");
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
		while (true) {
			String nextInt = input.next();
			if (iiv.isNumber(nextInt)) {
				int index = Integer.parseInt(nextInt);
				switch (index) {
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
					leaseRecord();
					break;
				case 7:
					logout();// �˳���¼
					break;
				case 8:
					exit();// �˳�
					break;
				default:
					System.out.print("û�и�ѡ����������룺");
				}
			} else {
				System.out.println("���벻�Ϸ������������룺");
			}
		}

	}

	private void logout() {
		System.out.println("-----------------------------------");
		// user = null;
		mainMenu();

	}

	private void leaseRecord() {
		System.out.println("-----------------------------------");
		if (user.isAdmin()) {
			System.out.println("���������еĵ������޼�¼");
			List<LeaseRecord> bike = leaseRecordDao.queryAll();
			System.out.println("���\t���г�ID\t�û�ID\t�����û�\t���ʱ��\t�黹ʱ��\t���ѽ��");
			for (LeaseRecord leaseRecord : bike) {
				System.out.println(leaseRecord);
			}
			returnMenu();
		} else {
			System.out.println("���������ĵ������޼�¼");
			List<LeaseRecord> bike = leaseRecordDao.queryByUserId(user.getId());
			System.out.println("���\t���г�ID\t�û�ID\t�����û�\t���ʱ��\t�黹ʱ��\t���ѽ��");
			for (LeaseRecord leaseRecord : bike) {
				System.out.println(leaseRecord);
			}
			returnMenu();
		}

	}

	private void personInfo() {
		// TODO Auto-generated method stub

	}

	/**
	 * ����IDɾ������
	 */
	private void deleteBike() {
		System.out.println("-----------------------------------");
		System.out.println("��������Ҫɾ���ĵ���ID��");
		int id = input.nextInt();

		if (bikedao.queryById(id) == null) {
			System.out.println("�����ڴ�id");

		} else {
			if (bikedao.doDelete(id)) {
				System.out.println("ɾ���ɹ�");
			} else {
				System.out.println("ɾ��ʧ��");
			}
		}

		System.out.println("�Ƿ����ɾ����");
		String againDe = input.next();
		if (againDe.equals("y")) {
			deleteBike();

		} else {
			adminMenu();
		}
	}

	/**
	 * ����ID�޸ĵ�����Ϣ
	 */
	private void editBike() {
		System.out.println("-----------------------------------");
		System.out.println("��������Ҫ�޸ĵĵ���ID");
		int id = input.nextInt();
		System.out.println("�����뵥�����ͣ�");
		String type = input.next();
		System.out.println("������۸�");
		double price = input.nextDouble();
		System.out.println("������λ��ID��");
		int locationId = input.nextInt();
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
			Bike bike2 = new Bike(id, type, price, locationId, status, amount, qr);
			boolean doUpdate = bikedao.doUpdate(bike2);
			if (doUpdate) {
				System.out.println("�޸ĳɹ�");
			} else {
				System.out.println("�޸�ʧ��");
			}
		}
		System.out.println("�Ƿ�����޸ģ�");
		String againEdit = input.next();
		if (againEdit.equals("y")) {
			editBike();

		} else {
			adminMenu();
		}

	}

	/**
	 * ��ӵ���
	 */
	private void saveBike() {
		System.out.println("-----------------------------------");
		System.out.println("��ӵ���");
		System.out.println("�����뵥�����ͣ�");
		String type = input.next();
		System.out.println("������۸�");
		double price = input.nextDouble();
		System.out.println("������λ�ã�");
		int locationId = input.nextInt();
		System.out.println("������qr");
		String qr = input.next();

		Bike bike = new Bike(type, price, locationId, 1, 0, qr);
		boolean doInsert = bikedao.doInsert(bike);
		if (doInsert) {
			System.out.println("��ӳɹ�");
		} else {
			System.out.println("���ʧ��");
		}
		System.out.println("�Ƿ������ӣ�");
		String againAdd = input.next();
		if (againAdd.equals("y")) {
			saveBike();

		} else {
			adminMenu();
		}
	}

	/**
	 * ��ѯ���еĵ���
	 */
	private void queryBike() {
		System.out.println("-----------------------------------");
		/*
		 * System.out.println("���������еĵ���"); List<LeaseRecord> leaseRecordDaos =
		 * leaseRecordDao.queryAll(); System.out.println("���\t����\t�۸�\tλ��\t״̬\t����\t��ά��");
		 * for (LeaseRecord record : leaseRecordDaos) { System.out.println(record); }
		 * returnMenu();
		 */

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
		System.out.println("-----------------------------------");
		System.out.println("�𾴵�" + user.getUsername() + "�û������ã�");
		System.out.println("\t1.��ѯ����");
		System.out.println("\t2.��赥��");
		System.out.println("\t3.�黹����");
		System.out.println("\t4.������Ϣ");
		System.out.println("\t5.����Ǯ��");
		System.out.println("\t6.���޼�¼");
		System.out.println("\t7.��������"); // TODO ����ѡ���޸ĸ�����Ϣ��������������
		System.out.println("\t8.�˳���¼");
		System.out.println("\t9.�˳�ϵͳ");
		System.out.print("��ѡ�����������Ĳ���:");
		while (true) {
			String nextInt = input.next();

			if (iiv.isNumber(nextInt)) {
				int index = Integer.parseInt(nextInt);
				switch (index) {
				case 1:
					queryBike();
					break;
				case 2:
					leaseBike();
					break;
				case 3:
					returnBike();
					break;
				case 4:
					personInfo();
					break;
				case 5:
					personWallet();
					break;
				case 6:
					leaseRecord();
					break;
				case 7:
					Setting();
					break;
				case 8:
					logout();// �˳���¼
					break;
				case 9:
					exit();// �˳�
					break;
				default:
					System.out.print("û�и�ѡ����������룺");
				}
			} else {
				System.out.println("���벻�Ϸ������������룺");
			}
		}

	}

	private void recharge() {
		while (true) {
			System.out.println("�������ֵ��");
			String money = input.next();
			if (iiv.isDouble(money)) {
				double m = Double.parseDouble(money);
				if(walletDao.recharge(user.getWalletID(), m) == 1) {
				System.out.println("��ֵ�ɹ�");
				personWallet();
				break;
				} else {
					System.out.println("��ֵʧ��");
				}
			} else {
				System.out.print("���벻�Ϸ���");
			}
		}
	}

	private void personWallet() {
		System.out.println("����Ǯ����ʾ����");
		Wallet wallet = walletDao.queryByUserId(user.getId());
		System.out.println("�û����\t�û����\t�Ż�ȯ���\t�û��ȼ�\tVIPʱ��");
		System.out.println(wallet);
		System.out.println("X�����Ѽ�¼\t C����ֵ");
		String s = input.next();
		if (s.equals("x")) {

		} else if (s.equals("c")) {
			recharge();
		} else {

			userMenu();
		}
	}

	private void Setting() {
		// TODO Auto-generated method stub

	}

	private void returnBike() {
		System.out.println("-----------------------------------");
		System.out.println("��������Ҫ�黹�ĵ���Id");
		int id = input.nextInt();
		int result = bikedao.doReturn(id);

		if (result == 1) {
			System.out.println("�黹�ɹ���");
			userMenu();
		} else if (result == 0) {
			System.out.println("ID������");
			userMenu();
		} else {
			System.out.println("��ID״̬���ɽ�");
			userMenu();
		}
	}

	private void leaseBike() {
		System.out.println("-----------------------------------");
		System.out.println("��������Ҫ���ĵ���ID��");
		int id = input.nextInt();
		int result = bikedao.doLease(id, user);
		if (result == 1) {
			System.out.println("����ɹ���");
			userMenu();
		} else if (result == 0) {
			System.out.println("ID������");
			userMenu();
		} else {
			System.out.println("��ID״̬���ɽ�");
			userMenu();
		}
	}

	/**
	 * �û�ע��
	 * 
	 * @Title userRegister
	 */
	private void userRegister() {
		System.out.println("-----------------------------------");
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
			System.out.println("�Ƿ�����ע�᣿");
			String againregister = input.next();
			if (againregister.equals("y")) {
				userRegister();

			} else {
				mainMenu();
			}
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
		System.out.println("-----------------------------------");
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

		System.out.println("����y����,������˳�");
		String next = input.next();

		if (next.equals("y")) {
			if (user == null) {
				mainMenu();
				return;
			}
			if (user.isAdmin()) {
				adminMenu();// ����Ա�˵�
			} else {
				userMenu();// ��ͨ�û��˵�
			}
		} else {
			// isTrueInput(0, 2); // 0�����¼ע��ҳ�棬1����adiminҳ�棬2������ͨ�û�
			exit();
		}
	}

	public <T> T isTrueInput(T a) {

		return a;

	}
}
