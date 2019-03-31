package tv.zhangjia.bike.util;

import java.util.List;
import java.util.Scanner;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.dao.impl.BillDaoImpl;
import tv.zhangjia.bike.dao.impl.LeaseRecordDaoImpl;
import tv.zhangjia.bike.dao.impl.LocationDaoImpl;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.dao.impl.WalletDaoImpl;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.VerificationCode;
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
	private VerificationCode vc = new VerificationCode();
	private UserDao userDao = new UserDaoImpl();
	private BikeDao bikeDao = new BikeDaoImpl();
	private LeaseRecordDao leaseRecordDao = new LeaseRecordDaoImpl();
	private WalletDao walletDao = new WalletDaoImpl();
	private InputIsValid iiv = new InputIsValid();
	private BillDao billDao = new BillDaoImpl();
	private LocationDao locationDao = new LocationDaoImpl();

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

	private User retrievePassword(int userId) {
		System.out.println("��������Ƿ��һ����룿");
		String s = input.next();
		if (s.equals("y")) {
			System.out.println("�����������ֻ��ţ�");
			while (true) {
				String tel = input.next();

				int x = userDao.isTrueTel(userId, tel);
				if (x != 1) {
					System.out.println("���ֻ��ź������û�����ƥ��,���������룺");
				} else {
					break;
				}
			}
			String codes = VerificationCode.randomCode();
			System.out.print("������������֤�룺");

			try {
				Thread.sleep(1500);//
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//û���ֻ����㷢��֤�룬ֻ��ί�����Լ������
			System.out.print(codes);
			
			try {
				Thread.sleep(500);//
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("   ��֤�ɹ���");

			System.out.println("�����������룺");
			String newPassword = input.next();
			return userDao.retrievePassword(userId, newPassword);
		} else {
			mainMenu();
		}
		return null;
	}

	/**
	 * �û���¼
	 */
	private void userLogin() {
		System.out.println("-----------------------------------");
		System.out.print("�����������û�����");
		String username;
		while (true) {
			username = input.next();
			if (userDao.isTrueUserName(username) != 1) {
				System.out.println("û�и��û���,����������");
			} else {
				break;
			}
		}

		System.out.print("�������������룺");
		String password;
		int wa = 0; // �ж�������뼸��
		User login;
		while (true) {
			password = input.next();
			login = userDao.login(username, password);
			if (login == null) {
				wa++;
				if (wa == 2) {
					login = retrievePassword(userDao.queryUserId(username));
					userLogin();
					break;
				}
				System.out.println("��¼ʧ�ܣ������������,���������룺");
			} else {
				break;
			}
		}

		this.user = login;
		if (user.isAdmin()) {
			adminMenu();
		} else {
			userMenu();
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
		System.out.println("\t7.��ѯλ��");
		System.out.println("\t8.ϵͳ����");
		System.out.println("\t9.��Ҫ����");
		System.out.println("\t10.������");
		System.out.println("\t11.�˳�ϵͳ");
		System.out.println("\t11.�˳�ϵͳ");
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
					userInfo();
					break;
				case 6:
					leaseRecord();
					break;
				case 7:
					queryLocation();//
					break;
				case 8:
					systemSettings();// �˳�
					break;
				case 9:
					dispatch();// ����
					break;
				case 10:
					damage();
					break;
				case 11:
					logout();// �˳�
					break;
				case 12:
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

	private void damage() {
		// TODO Auto-generated method stub

	}

	private void dispatch() {
		List<String> arr = locationDao.dispatch();
		for (String string : arr) {
			System.out.println(string);
		}
		locationDao.dispatch();

	}

	private void systemSettings() {
		// TODO Auto-generated method stub

	}

	private void queryLocation() {
		System.out.println("���������е�λ����Ϣ��");
		List<Location> locations = locationDao.queryAll();
		System.out.println("���\tλ������\t��������");
		for (Location location : locations) {
			System.out.println(location);
		}

		while (true) {
			System.out.print("��ѯָ��λ�ã�");
			int id = input.nextInt();
			if (id == -1)
				break;
			Location lo = locationDao.queryLocation(id);
			System.out.println("���\tλ������\t��������");
			System.out.println(lo.getBikes());

		}

		adminMenu();
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

	private void userInfo() {
		if (user.isAdmin()) {
			System.out.println("���������л�Ա��Ϣ");
			List<User> user = userDao.queryAll();
			System.out.println("���\t�û���\t�û��ֻ���\t����ʱ��\tע��ʱ��");
			System.out.println(user);
			returnMenu();
		} else {
			System.out.println("���������ĸ�����Ϣ");
			User user = userDao.queryByUserId(this.user.getId());
			System.out.println("���\t�û���\t�û��ֻ���\t����ʱ��\tע��ʱ��");
			System.out.println(user);
			returnMenu();
		}

	}

	/**
	 * ����IDɾ������
	 */
	private void deleteBike() {
		System.out.println("-----------------------------------");
		System.out.println("��������Ҫɾ���ĵ���ID��");
		int id = input.nextInt();

		if (bikeDao.queryById(id) == null) {
			System.out.println("�����ڴ�id");

		} else {
			if (bikeDao.doDelete(id)) {
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

		Bike bike = bikeDao.queryById(id);
		if (bike == null) {
			System.out.println("û�и�ID");
		} else {
			Bike bike2 = new Bike(id, type, price, locationId, status, amount, qr);
			boolean doUpdate = bikeDao.doUpdate(bike2);
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
		boolean doInsert = bikeDao.doInsert(bike);
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
		List<Bike> bike = bikeDao.queryAll();
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
					userInfo();
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
				if (walletDao.recharge(user.getWalletID(), m) == 1) {
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
		System.out.println("X�����Ѽ�¼\t C����ֵ v:��Ա");
		String s = input.next();
		if (s.equals("x")) {
			billMenu();
		} else if (s.equals("c")) {
			recharge();
		} else if (s.equals("v")) {
			becomeVIPMenu();
		} else {

			userMenu();
		}
	}

	private void becomeVIPMenu() {
		System.out.println("��������Ҫ��ֵ���·�");

		int month = 0;
		while (true) {
			String m = input.next();
			if (iiv.isNumber(m)) {
				month = Integer.parseInt(m);
				break;
			} else {
				System.out.println("���������룺");
			}
		}

		int result = walletDao.becomeVIP(user.getId(), month);
		if (result == -5) {
			recharge();
		} else {
			System.out.println("��ϲ����ͨ�ɹ�");
			userMenu();
		}

	}

	private void billMenu() {
		System.out.println("���������������˵�");
		// List<Bill> userBills = billDao.queryAll();
		List<Bill> userBills = billDao.queryUserBill(user.getId());
		System.out.println("�û����\t�û����\t�Ż�ȯ���\t�û��ȼ�\tVIPʱ��");
		System.out.println(userBills);
	}

	private void Setting() {
		// TODO Auto-generated method stub

	}

	private void returnBike() {
		System.out.println("-----------------------------------");
		System.out.println("��������Ҫ�黹�ĵ���Id");
		int bikeId = input.nextInt();
		int result = leaseRecordDao.returnBike(bikeId, user.getId());

		if (result == 1) {
			System.out.println("�黹�ɹ�");
			userMenu();
		} else if (result == 0 || result == 11) {
			System.out.println("��δ���õ���");
			userMenu();
		} else if (result == -5) {
			recharge();
		} else {
			System.out.println("��ID������");
			userMenu();
		}
	}

	private void leaseBike() {
		System.out.println("-----------------------------------");
		System.out.println("��������Ҫ���ĵ���ID��");
		int bikeId = input.nextInt();
		int result = leaseRecordDao.doInsert(user.getId(), bikeId);
		if (result == 1) {
			System.out.println("����ɹ���");
			userMenu();
		} else if (result == 10) {
			System.out.println("�ó����Ѿ������");
			userMenu();
		} else {
			System.out.println("�ó���ID������");
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

		int register = userDao.register(username, password);

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
