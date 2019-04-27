package tv.zhangjia.bike.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.UserOptionsDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.dao.impl.BillDaoImpl;
import tv.zhangjia.bike.dao.impl.LeaseRecordDaoImpl;
import tv.zhangjia.bike.dao.impl.LocationDaoImpl;
import tv.zhangjia.bike.dao.impl.OptionDaoImpl;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.dao.impl.UserOptionsDaoImpl;
import tv.zhangjia.bike.dao.impl.WalletDaoImpl;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.UserOptions;
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
	private UserDao userDao = new UserDaoImpl();
	private BikeDao bikeDao = new BikeDaoImpl();
	private LeaseRecordDao leaseRecordDao = new LeaseRecordDaoImpl();
	private WalletDao walletDao = new WalletDaoImpl();
	private InputIsValid iiv = new InputIsValid();
	private BillDao billDao = new BillDaoImpl();
	private LocationDao locationDao = new LocationDaoImpl();
	// private AdminSettingsDao as = new AdminSettinsDaoImpl();
	private OptionDao as = new OptionDaoImpl();
	private UserOptionsDao us = new UserOptionsDaoImpl();
	// private UserSettingsDao us = new UserSettingsDaoImpl();

	/**
	 * ���˵��������ϵͳ���û������ĵ�һ������
	 * 
	 * @Title mainMenu
	 */
	public void mainMenu() {
		System.out.println("----------��ӭ��ʹ�ù���������ϵͳ----------");
		System.out.println("\t\t1.��¼");
		System.out.println("\t\t2.ע��");
		System.out.println("\t\t3.�˳� ");
		System.out.println("-------------�㿴���ҵĵ�����--------------");
		System.out.print("��ѡ�����������Ĳ���:");
		int index = 1;
		// ��whileѭ��ʵ�����ѡ����ڣ���������
		while (true) {
			String choose = input.next();
			// �ж�����������Ƿ�Ϊ����
			if (iiv.isNumber(choose)) {
				index = Integer.parseInt(choose);
				if (index > 3) {
					System.out.print("�����ڴ�ѡ��,���������룺");
				} else {
					break;
				}
			} else {
				System.out.print("���벻�Ϸ�,���������룺");
			}
		}

		switch (index) {
		case 1:
			userLogin();
			break;
		case 2:
			userRegister();
			break;
		case 3:
			exit();
			break;
		default:
			System.out.print("�����ڴ�ѡ��,���������룺");
		}

	}

	private int retrievePassword(int userId) {

		System.out.print("�����������ֻ��ţ�");
		while (true) {
			String tel = input.next();

			if (!userDao.isTrueTel(tel, userId)) {
				System.out.print("���ֻ��ź������û�����ƥ��,���������룺");
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
		// û���ֻ����㷢��֤�룬ֻ��ί�����Լ������
		System.out.print(codes);

		try {
			Thread.sleep(500);//
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("   ��֤����ȷ��");

		System.out.print("�����������룺");
		String newPassword = input.next();
		User user = userDao.queryByUserId(userId);
		user.setPassword(newPassword);
		return userDao.doUpdate(user);
	}

	/**
	 * �û���¼
	 */
	private void userLogin() {
		printBoundary();
		System.out.print("�����������û�����");
		String username;
		while (true) {
			username = input.next();
			if (!userDao.isUserNameExist(username)) {
				System.out.print("û�и��û���,���������룺");
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
					System.out.println("��������Ƿ��һ����룿[ �һ� ��y | �������˵���r ] : ");
					String s = input.next();
					printBoundary();
					if (s.equalsIgnoreCase("y")) {
						// -1�������һ����룬��¼�û����޸����룬��������û���id
						retrievePassword(-1);
						System.out.println("�һسɹ��������µ�¼��");
						userLogin(); // TODO :�����Լ���
					} else {
						mainMenu();
					}
					break;
				}
				System.out.print("��¼ʧ�ܣ������������,���������룺");
			} else {
				break;
			}
		}

		this.user = login;
		if (user.getIsAdmin()) {
			adminMenu();
		} else {
			userMenu();
		}

	}

	/**
	 * ����Ա������
	 */
	private void adminMenu() {
		printBoundary();
		System.out.println("�𾴵�" + user.getUsername() + "����Ա�����ã�");
		System.out.println("\t1.��ѯ����");
		System.out.println("\t2.��ӵ���");
		System.out.println("\t3.�޸ĵ���");
		System.out.println("\t4.ɾ������");
		System.out.println("\t5.�ѻ�����");
		System.out.println("\t6.�鿴λ��");
		System.out.println("\t7.���Ƚ���");
		System.out.println("\t8.���޼�¼");
		System.out.println("\t9.�û���Ϣ");
		System.out.println("\t10.�û�Ǯ��");
		System.out.println("\t11.�û��˵�");
		System.out.println("\t12.ϵͳ����");
		System.out.println("\t13.�˳���¼");
		System.out.println("\t14.�˳�ϵͳ");
		printBoundary();
		System.out.print("��ѡ�����������Ĳ�����");
		int index = 1;
		while (true) {
			String nextInt = input.next();
			if (iiv.isNumber(nextInt)) {
				index = Integer.parseInt(nextInt);
				if (index > 14) {
					System.out.print("�����ڴ�ѡ��,���������룺");
				} else {
					break;
				}

			} else {
				System.out.print("���벻�Ϸ������������룺");
			}
		}

		switch (index) {
		case 1:
			queryBikes();
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
			damage();
			break;
		case 6:
			Location();//
			break;
		case 7:
			dispatch();// ����
			break;
		case 8:
			leaseRecord();
			break;
		case 9:
			userInfo();
			break;
		case 10:
			usersWallet();//
			break;
		case 11:
			usersBill();//
			break;
		case 12:
			systemSettings();//
			break;
		case 13:
			logout();//
			break;
		case 14:
			exit();//
			break;
		default:
			System.out.print("û�и�ѡ����������룺");
		}

	}

	private void usersBill() {

		if (user.getIsAdmin()) {
			System.out.println("-------------�����������û����˵���Ϣ-----------");
			System.out.println("���\t�û���\t�˵�����\t���仯\t\t����ʱ��");
			List<Bill> bills = billDao.queryAll();
			if (bills.isEmpty()) {
				System.out.println("Ŀǰû���κ��˵���¼��");
				printBoundary();
			} else {
				for (Bill bill : bills) {
					System.out.println(bill);
				}
				printBoundary();
				System.out.print("�Ƿ񵼳������أ�[ �ǣ�y | ��n ] ��");
				String s = input.next();
				if (s.equalsIgnoreCase("y")) {
					try {
						billDao.export();
						System.out.println("�����ɹ�");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("����ʧ��");
					}

				}
			}
			returnMenu();
		} else {
			System.out.println("-------------���������������˵���Ϣ-------------");
			List<Bill> userBills = billDao.queryUserBill(user.getId());
			System.out.println("���\t�û���\t�˵�����\t���仯\t\t����ʱ��");
			if (userBills.isEmpty()) {
				printBoundary();
				System.out.println("��Ŀǰû���κ��˵���¼��");
			} else {
				for (Bill bill : userBills) {
					System.out.println(bill);
				}
			}
			returnMenu();
		}
	}

	private void usersWallet() {

		if (user.getIsAdmin()) {
			System.out.println("-----------�����������û���Ǯ����Ϣ-----------");
			System.out.println("���\t�û���\t�˻����\t������\t�û��ȼ�\t  VIPʱ��");
			List<Wallet> wallets = walletDao.queryAll();
			for (Wallet wallet : wallets) {
				System.out.println(wallet);
			}

			returnMenu();
		} else {
			System.out.println("-------------����Ǯ����ʾ����-------------");
			// System.out.println(user.getId());
			Wallet wallet = walletDao.queryByUserId(user.getId());
			// System.out.println(wallet.getId());
			System.out.println("���\t�û���\t�û����\t�Ż�ȯ���\t�û��ȼ�\tVIPʱ��");
			System.out.println(wallet);
			returnMenu();
		}

	}

	private void damage() {
		List<Bike> bikes = bikeDao.queryByDamage();
		System.out.println("----------�������Ѿ��𻵵ĳ�����Ϣ----------");
		if (bikes.isEmpty()) {
			System.out.println("̫���ˣ�Ŀǰû�г����𻵣�");

		} else {
			System.out.println("���\t����\t�۸�\tλ��\t״̬\t����\t��ά��");
			for (Bike bike : bikes) {
				System.out.println(bike);
			}
		}
		returnMenu();

	}

	private void dispatch() {
		System.out.println("---------���������µ�λ����Ϣ---------");
		List<Location> locations = locationDao.queryAll();
		System.out.println("���\tλ������\t��������");
		for (Location location : locations) {
			System.out.println(location);
		}
		printBoundary();
		System.out.println("���������λ����Ϣ���˹����Ͻ�������");
		List<String> arr = locationDao.dispatch();
		for (String string : arr) {
			System.out.println(string);
		}
		returnMenu();

	}

	/**
	 * ��ʾ���
	 * @param user ��¼���û�
	 */
	private void advertising(User user) {
		Wallet wt = walletDao.queryByUserId(user.getId());
		// ������ǻ�Ա������ʾ���
		if (!wt.getIsVIP()) {
			System.out.print("�����ǻ�Ա��������Ҫ���㿴��棺\t");
			// ��ȡ�������
			System.out.println(as.queryValue("���"));
		}
	}

	private void systemSettings() {
		// AdminSettings ass = as.queryAdminSettings();
		System.out.println("-----------�����ǹ���Ա����-----------");
		System.out.println("1. ���ýŵų��۸�");
		System.out.println("2. �����������۸�");
		System.out.println("3. ���ÿ���Ա�۸�");
		System.out.println("4. ���û�Ա���ۿ�");
		System.out.println("5. ����վ�ڵĹ��");
		System.out.print("��ѡ�����ã�");
		int index = -1;
		while (true) {
			String s = input.next();
			if (iiv.isNumber(s)) {
				index = Integer.parseInt(s);
				if (index > 5) {
					System.out.print("�����ڴ�ѡ��,���������룺");
				} else {
					break;
				}

			} else {
				System.out.print("���벻�Ϸ���������ѡ��");
			}

		}

		switch (index) {
		case 1:
			System.out.print("������ŵų��۸�");
			while (true) {
				String price = input.next();
				if (iiv.isDouble(price)) {
					as.doUpdate(price, "�ŵų�");
					System.out.println("���óɹ�");
					break;
				} else {
					System.out.print("���벻�Ϸ�������������ŵų��۸�");
				}
			}
			break;
		case 2:
			System.out.print("�������������۸�");
			while (true) {
				String price = input.next();
				if (iiv.isDouble(price)) {
					as.doUpdate(price, "������");
					System.out.println("���óɹ�");
					break;
				} else {
					System.out.print("���벻�Ϸ��������������������۸�");

				}
			}
			break;
		case 3:
			System.out.print("�������Ա/�¼۸�");
			while (true) {
				String price = input.next();
				if (iiv.isDouble(price)) {
					as.doUpdate(price, "��Ա�۸�");
					System.out.println("���óɹ�");
					break;
				} else {
					System.out.print("���벻�Ϸ������������룺");

				}
			}
			break;
		case 4:
			System.out.print("�������Ա���ܵ��ۿۣ�");
			while (true) {
				String discount = input.next();
				if (iiv.isDouble(discount)) {
					as.doUpdate(discount, "�ۿ�");
					System.out.println("���óɹ�");
					break;
				} else {
					System.out.print("���벻�Ϸ������������룺");

				}
			}
			break;
		case 5:
			System.out.print("�����������ݣ�");
			String advertising = input.next();
			// ass.setAdvertising(advertising);
			// as.queryAlloptions().get(4).setValue(advertising);
			as.doUpdate(advertising, "���");
			System.out.println("���óɹ�");
			break;
		default:
			System.out.print("û�и�ѡ�������ѡ��");
		}

		returnMenu();

	}

	/**
	 * ��ӳ�����ʱ����ʾ����λ�õĴ�����Ϣ
	 */
	private void addBikequeryLocation() {
		printBoundary();
		System.out.println("���������е�λ����Ϣ��");
		List<Location> locations = locationDao.queryAll();
		System.out.println("���\tλ������\t��������");
		for (Location location : locations) {
			System.out.println(location);
		}
	}

	private void Location() {
		System.out.println("------------���������е�λ����Ϣ------------");
		List<Location> locations = locationDao.queryAll();
		System.out.println("���\tλ������\t��������");
		for (Location location : locations) {
			System.out.println(location);
		}

		System.out.print("��ѯָ��λ�ã�[ָ���� ID | ���أ�r ]  ��");

		int locationID = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				locationID = Integer.parseInt(str);
				Location lo = locationDao.queryLocation(locationID);
				if (lo == null) {
					System.out.print("��λ�ò����ڣ�����������λ��ID��");
				} else {
					break;
				}

			} else {
				adminMenu();
			}

		}

		printBoundary();
		Location lo = locationDao.queryLocation(locationID);
		System.out.println("���\t����\t�۸�\tλ��\t״̬\t����\t��ά��");

		List<Bike> bikesByLo = locationDao.queryBikesByLocation(lo.getId());
		// List<Bike> bs = lo.getBikes();
		for (Bike bike : bikesByLo) {

			System.out.println(bike);
		}
		returnMenu();
	}

	private void logout() {
		printBoundary();
		user = null;
		System.out.println("�����˳����ڴ����ٴε�¼��");
		mainMenu();

	}

	private void leaseRecord() {
		if (user.getIsAdmin()) {
			System.out.println("----------�����������û��ĵ������޼�¼-----------");
			List<LeaseRecord> bike = leaseRecordDao.queryAll();

			System.out.println("���\t���г�ID\t�����û�\t���ʱ��\t\t�黹ʱ��\t\t��ʼλ��\t\t\t����ʱ��\t  ���ѽ��");
			if (bike.isEmpty()) {
				printBoundary();
				System.out.println("�������ҵ���û���κ��˽賵");
			}
			for (LeaseRecord leaseRecord : bike) {
				System.out.println(leaseRecord);
			}
			returnMenu();
		} else {
			System.out.println("-----------���������ĵ������޼�¼----------");
			List<LeaseRecord> bike = leaseRecordDao.queryByUserId(user.getId());
			System.out.println("���\t���г�ID\t�����û�\t���ʱ��\t\t�黹ʱ��\t\t��ʼλ��\t\t\t����ʱ��\t  ���ѽ��");
			if (bike.isEmpty()) {
				System.out.println("��û������κε������Ͽ����һ�����԰ɣ�");
			}
			for (LeaseRecord leaseRecord : bike) {
				System.out.println(leaseRecord);
			}
			returnMenu();
		}

	}

	private void userInfo() {
		if (user.getIsAdmin()) {
			System.out.println("----------------------���������л�Ա��Ϣ----------------------");
			List<User> user = userDao.queryAll();
			System.out.println("���\t�û���\t�û��ֻ���\t\t������ʱ��\tע��ʱ��");
			for (User user2 : user) {
				System.out.println(user2);
			}
			returnMenu();
		} else {
			System.out.println("----------------------���������ĸ�����Ϣ----------------------");
			User user = userDao.queryByUserId(this.user.getId());
			System.out.println("���\t�û���\t�û��ֻ���\t\t������ʱ��\tע��ʱ��");
			System.out.println(user);
			returnMenu();
		}

	}

	/**
	 * ����IDɾ������
	 */
	private void deleteBike() {
		printBoundary();
		System.out.print("��������Ҫɾ���ĵ���ID��");
		Bike bike = null;
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				bike = bikeDao.queryById(bikeId);
				if (bike == null) {
					System.out.print("�����ڴ�id,");
				} else if (bike.getStatus() == 0) {
					System.out.print("�˵������ܱ�ɾ��");
				} else {
					break;
				}
			} else {
				System.out.print("���벻�Ϸ���");
			}

			System.out.print("����������Ҫɾ����ID��");
		}

		if (bikeDao.doDelete(bikeId) == 1) {
			System.out.println("ɾ���ɹ�");
		} else {
			System.out.println("ɾ��ʧ��");
		}
		System.out.print("�Ƿ����ɾ����[ ���� ��y | ���أ�r ]  : ");
		String againEdit = input.next();
		if (againEdit.equalsIgnoreCase("y")) {
			deleteBike();
		} else {
			adminMenu();
		}

	}

	/**
	 * ����ID�޸ĵ�����Ϣ
	 */
	private void editBike() {
		printBoundary();
		Bike bike = null;
		System.out.print("��������Ҫ�޸ĵĵ���ID��");
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				bike = bikeDao.queryById(bikeId);

				if (bike == null) {
					System.out.print("û�и�ID,����������ID��");
				} else if (bike.getStatus() == 0) {
					System.out.print("�˳��������޸ģ�����������ID��");

				} else {
					break;
				}
			} else {
				System.out.print("���벻�Ϸ�,���������룺");
			}
		}

		System.out.print("�����뵥������ [ �ŵų���1 | �������� 2 ] ��");
		String type;
		double price;
		while (true) {
			type = input.next();
			if (type.equals("1")) {
				// price = as.queryAdminSettings().getaBikePrice();
				price = Double.parseDouble(as.queryValue("�ŵų�"));
				break;
			} else if (type.equals("2")) {
				// price = as.queryAdminSettings().getbBikePrice();
				price = Double.parseDouble(as.queryValue("������"));
				break;
			} else {
				System.out.print("û�иó��ͣ����������룺");
			}
		}

		System.out.print("������λ��ID��");
		int locationId = 1;
		while (true) {

			String str = input.next();
			if (iiv.isNumber(str)) {
				locationId = Integer.parseInt(str);
				if (locationDao.queryLocation(locationId) == null) {
					System.out.print("û�и�λ�ã�");
				} else {
					break;
				}

			} else {
				System.out.print("���벻�Ϸ���");
			}
			System.out.print("������ѡ��λ��ID��");
		}

		System.out.print("�����뵥��״̬[ �ɽ裺1 | �����0 | �� ��-1 ] ��");
		int status = 1;
		while (true) {
			String str = input.next();
			if (iiv.isInt(str)) {
				status = Integer.parseInt(str);
				if (status != 1 && status != 0 && status != -1) {
					System.out.print("û�д�״̬�����������룺");
				} else {
					break;
				}
			} else {
				System.out.print("״̬���벻�Ϸ������������룺");
			}
		}
		System.out.print("�����뱻��������");
		int amount = 0;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				amount = Integer.parseInt(str);
				break;
			} else {
				System.out.print("���벻�Ϸ������������룺");
			}
		}

		// Bike bike2 = new Bike(bikeId, type, price, locationId, status, amount);
		String types = (type.equals("1") ? "�ŵų�" : "������");
		// locationId = status == 0 ? -1 : locationId; // ����������޸�Ϊ���״̬�����³���λ��
		bike.setType(types);
		bike.setPrice(price);
		bike.setLastLocationId(bike.getLocationId());
		bike.setLocationId(locationId);
		bike.setStatus(status);
		bike.setAmount(amount);

		int doUpdate = bikeDao.doUpdate(bike);
		if (doUpdate == 1) {
			if (status == 0) {
				// locationDao.updateLocationBikes(bike.getLastLocationId());
			} else if (status == 1) {
				// locationDao.updateLocationBikes(bike.getLastLocationId());
				// locationDao.updateLocationBikes(locationId);
			} else {
				// locationDao.updateLocationBikes(bike.getLocationId());

			}
			System.out.print("�޸ĳɹ���");
		} else {
			System.out.print("�޸�ʧ�ܣ�");

		}
		System.out.print("�Ƿ�����޸ģ�[ ���� ��y | ���أ�r ]  : ");
		String againEdit = input.next();
		if (againEdit.equalsIgnoreCase("y")) {
			editBike();

		} else {
			adminMenu();
		}

	}

	/**
	 * ��ӵ���
	 */
	private void saveBike() {
		printBoundary();
		System.out.print("�����뵥������[ �ŵų���1 | �������� 2 ] ��");
		String type = "";
		while (true) {
			type = input.next();
			if (type.equals("1")) {
				System.out.println(as.queryValue("�ŵų�"));
				break;
			} else if (type.equals("2")) {
				break;
			} else {
				System.out.print("û�иó��ͣ����������룺");
			}
		}

		addBikequeryLocation();
		System.out.print("��Ҫ���ó���ӵ��ĸ�λ�ã�(ID)��");
		int locationId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				locationId = Integer.parseInt(str);
				if (locationDao.queryLocation(locationId) == null) {
					System.out.print("��λ�ò�����");
				} else {
					break;
				}

			} else {
				System.out.print("���벻�Ϸ���");
			}
			System.out.print("������ѡ��λ��ID��");
		}

		// ��������ѡ����
		String types = (type.equals("1") ? "�ŵų�" : "������");
		// ����Ҫ��ӵ�������Ϣ��Ĭ����ʼλ����ͬ��״̬Ϊ1���ɽ裩������Ϊ0����ά����ӵ�ʱ�����ɣ�����������㴫��һ���ַ���
		Bike bike = new Bike(types, locationId, locationId, 1, 0, "");
		int doInsert = bikeDao.doInsert(bike);
		if (doInsert == 1) {

			System.out.println("��ӳɹ�");
		} else {
			System.out.println("���ʧ��");
		}
		System.out.print("�Ƿ������ӣ�[ ���� ��Y | ���أ�R ] :");
		String againAdd = input.next();
		if (againAdd.equalsIgnoreCase("y")) {
			saveBike(); // TODO ����������
		} else {
			adminMenu();
		}
	}

	/**
	 * ��ѯ���еĵ���
	 */
	private void queryBikes() {
		System.out.println("---------------------������ϵͳ�����еĵ��������Ϣ-----------------------");
		List<Bike> bike = bikeDao.queryAll();
		System.out.println("���\t����\t�۸�\tλ��\t״̬\t����\t��ά��");
		for (Bike bike2 : bike) {
			System.out.println(bike2);
		}
		returnMenu();
	}

	private void queryUserBikes() {
		System.out.println("---------------------������ϵͳ�����еĵ��������Ϣ-----------------------");
		List<Bike> bike = bikeDao.queryAll();
		System.out.println("���\t����\t�۸�\tλ��\t״̬\t����\t��ά��");
		for (Bike bike2 : bike) {
			if (bike2.getStatus() != 1) {
				continue;
			}
			System.out.println(bike2);
		}
		returnMenu();
	}

	/**
	 * ��ͨ�û�������
	 */
	private void userMenu() {
		printBoundary();
		System.out.println("�𾴵�" + user.getUsername() + "�û������ã�");
		advertising(user);
		System.out.println("\t1.��ѯ����");
		System.out.println("\t2.��赥��");
		System.out.println("\t3.�黹����");
		System.out.println("\t4.������Ϣ");
		System.out.println("\t5.����Ǯ��");
		System.out.println("\t6.���޼�¼");
		System.out.println("\t7.���ϱ���");
		System.out.println("\t8.��ֵ���");
		System.out.println("\t9.���Ѽ�¼");
		System.out.println("\t10.��ͨ��Ա");
		System.out.println("\t11.��������");
		System.out.println("\t12.�˳���¼");
		System.out.println("\t13.�˳�ϵͳ");

		System.out.print("��ѡ�����������Ĳ���:");
		int index = 1;
		while (true) {
			String nextInt = input.next();

			if (iiv.isNumber(nextInt)) {
				index = Integer.parseInt(nextInt);
				if (index > 13) {
					System.out.print("û�и�ѡ�����������:");
				} else {
					break;
				}

			} else {
				System.out.print("���벻�Ϸ������������룺");
			}
		}

		switch (index) {
		case 1:
			queryUserBikes();
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
			// personWallet();
			usersWallet();
			break;
		case 6:
			leaseRecord();
			break;
		case 7:
			awardByRepairs();// ���޽���
			break;
		case 8:
			recharge(true);
			break;
		case 9:
			usersBill();
			break;
		case 10:
			becomeVIPMenu();
			break;
		case 11:
			userSettings();// �˳�
			break;
		case 12:
			logout();// �˳���¼
			break;
		case 13:
			exit();// �˳�
			break;
		default:
			System.out.print("û�и�ѡ����������룺");
		}

	}

	private void userBikes() {
		printBoundary();
		Location lo = locationDao.queryLocation(user.getLocationID());
		System.out.print("���ĵ�ǰλ����[ " + lo.getLocationName() + " ]");
		System.out.println("�������Ǹ�λ���µ����еĵ�����Ϣ");
		List<Bike> bike = locationDao.queryBikesByLocation(lo.getId());
		System.out.println("���\t����\t�۸�\tλ��\t״̬\t����\t��ά��");
		for (Bike bike2 : bike) {
			System.out.println(bike2);
		}

	}

	private void awardByRepairs() {
		printBoundary();
		System.out.print("�������𻵵ĳ���:");
		String id = input.next();
		while (true) {
			if (iiv.isNumber(id)) {
				int bikeId = Integer.parseInt(id);
				int status = bikeDao.bikeStatus(bikeId);
				if (status == -1) {
					System.out.print("�ó��Ѿ���,");
					returnMenu();
				}

				int walletId = walletDao.queryByUserId(bikeDao.setDamage(user, bikeId)).getId();

				// billDao.awardByBike(user.getId(), walletId);
				walletDao.awardByBike(user.getId(), walletId);

				break;
			} else {
				System.out.print("���벻�Ϸ������������룺");
			}
		}
		System.out.print("�ó����Ѿ����ޣ���л��Ϊ���еĻ����������ף�");
		returnMenu();

	}

	private void userSettings() {
		// UserSettings ps = us.queryUserSetting(user.getId());
		;
		System.out.println("-----------��������������-----------");
		System.out.println("1.����֧��");
		System.out.println("2.�޸�����");
		System.out.print("��ѡ�����ã�");
		int index = -1;
		while (true) {
			String s = input.next();
			if (iiv.isNumber(s)) {
				index = Integer.parseInt(s);
				if (index > 2) {
					System.out.print("�����ڴ�ѡ��,���������룺");
				} else {
					break;
				}

			} else {
				System.out.print("���벻�Ϸ���������ѡ��");
			}

		}

		switch (index) {
		case 1:
			System.out.println("----------����֧������----------");
			String s = us.queryUserSetting(user.getId(), "����֧��");
			System.out.println(s);
			System.out.println("��Ŀǰ����֧������Ϊ��" + (s.equals("1") ? "��" : "��"));
			System.out.print("������������� �� [ �򿪣�t | �ر� f | ���� r ] ��");
			// System.out.println("�򿪣�t,�رգ�f,���������");
			String auto = input.next();
			if (auto.equalsIgnoreCase("t")) {
				// ps.setActp(true);
				// us.sestValues(new UserOptions(user.getId(), "����֧��", "1"));
				us.doUpdate(new UserOptions(user.getId(), "����֧��", "1"));
				System.out.print("���óɹ���");
				returnMenu();

			} else if (auto.equalsIgnoreCase("f")) {
				System.out.println("���óɹ���");
				// ps.setActp(false);
				// us.sestValues(user.getId(), "����֧��", "0");
				us.doUpdate(new UserOptions(user.getId(), "����֧��", "0"));
				returnMenu();
			} else {
				userMenu();
			}
			break;
		case 2:
			retrievePassword(user.getId());
			System.out.println("�޸ĳɹ��������µ�¼��");
			userLogin();
			break;
		}

	}

	private void recharge(boolean b) {
		// UserSettings ps = us.queryUserSetting(user.getId());
		double m = 0;
		System.out.print("�������ֵ��");
		while (true) {
			String money = input.next();
			if (iiv.isDouble(money)) {
				m = Double.parseDouble(money);
				break;

			} else {
				System.out.print("���벻�Ϸ������������룺");
			}
		}

		boolean openPayPassword = (us.queryUserSetting(user.getId(), "����֧��").equals("1") ? true : false);
		// System.out.println("shifou" + openPayPassword);
		while (!openPayPassword) {
			System.out.print("����������֧�����룺");
			String payPassword = input.next();
			if (isTruePayPw(user, payPassword)) {
				break;
			} else {
				System.out.print("֧�����벻��ȷ,���������룺");
			}
		}

		if (walletDao.recharge(user.getId(), m) == 1) {
			// if (walletDao.recharge(walletDao.queryByUserId(user.getId()).getId(),m) == 1)
			// {
			System.out.println("��ֵ�ɹ� ��\t");
		} else {
			System.out.println("��ֵʧ��  ��\t");

		}

		if (b) {
			returnMenu();
		}
	}

	// private void personWallet() {
	// System.out.println("����Ǯ����ʾ����");
	// System.out.println(user.getId());
	// Wallet wallet = walletDao.queryByUserId(user.getId());
	// System.out.println(wallet.getId());
	// System.out.println("���\t�û���\t�û����\t�Ż�ȯ���\t�û��ȼ�\tVIPʱ��");
	// System.out.println(wallet);
	// returnMenu();
	// }

	private void becomeVIPMenu() {
		// double vipPrice = as.queryAdminSettings().getVipPrice();
		double vipPrice = Double.parseDouble(as.queryValue("��Ա�۸�"));
		// double zc = as.queryAdminSettings().getDiscount();
		double zc = Double.parseDouble(as.queryValue("�ۿ�"));
		System.out.println("���ڿ�ͨ��Աֻ��Ҫ" + vipPrice + "Ԫ/�£�����������������ⵥ��" + (int) (zc * 10) + "���Ż�");
		System.out.print("��������Ҫ��ͨ���·ݣ�");

		int month = 0;
		while (true) {
			String m = input.next();
			if (iiv.isNumber(m)) {
				month = Integer.parseInt(m);
				break;
			} else {
				System.out.print("���벻�Ϸ������������룺");
			}
		}

		// UserSettings ps = us.queryUserSetting(user.getId());
		boolean openPayPassword = (us.queryUserSetting(user.getId(), "����֧��").equals("1") ? true : false);

		while (!openPayPassword) {
			System.out.print("����������֧�����룺");
			String payPassword = input.next();
			if (isTruePayPw(user, payPassword)) {
				break;
			} else {
				System.out.print("֧�����벻��ȷ��");

			}
		}
		while (true) {
			int result = walletDao.becomeVIP(user.getId(), month);
			if (result == -5) {
				System.out.print("���㣬");
				recharge(false);
			} else {
				System.out.print("��ϲ����ͨ�ɹ���");
				Wallet w = walletDao.queryByUserId(user.getId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String time = sdf.format(w.getVipDate());
				System.out.println("��Ա����ʱ�䣺" + time);
				break;
			}
		}
		returnMenu();

	}

	private boolean isTruePayPw(User user, String payPassword) {
		List<User> users = userDao.queryAll();

		for (User user2 : users) {
			if (user2.getPayPassword().equals(payPassword) && user2.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}

	private void returnBike() {
		List<LeaseRecord> usr = leaseRecordDao.queryNotReturnByUserId(user.getId());
		printBoundary();
		if (usr.isEmpty()) {
			System.out.println("����û����������~");
			returnMenu();
		} else {
			List<LeaseRecord> ld = leaseRecordDao.queryNotReturnByUserId(user.getId());
			System.out.println("------------------------------�������³���δ�黹---------------------------------");
			System.out.println("���\t���г�ID\t�����û�\t���ʱ��\t\t�黹ʱ��\t\t��ʼλ��\t\t\t����ʱ��\t  ���ѽ��");
			for (LeaseRecord leaseRecord : ld) {
				System.out.println(leaseRecord);
			}
		}
		System.out.print("��������Ҫ�黹�ĵ���Id��");
		int bikeId = -1;
		// while (true) {
		// String str = input.next();
		// if (iiv.isNumber(str)) {
		// bikeId = Integer.parseInt(str);
		// if (bikeDao.bikeStatus(bikeId) != 10 ||
		// (!leaseRecordDao.isCurrentUserLease(user.getId(), bikeId))) {
		// System.out.print("��û�����õ���,���������룺");
		// } else {
		// while (true) {
		// // UserSettings ps = us.queryUserSetting(user.getId());
		// boolean openPayPassword = (us.queryUserSetting(user.getId(),
		// "����֧��").equals("1") ? true
		// : false);
		//
		// while (!openPayPassword) {
		// System.out.print("����������֧�����룺");
		// String payPassword = input.next();
		// if (isTruePayPw(user, payPassword)) {
		// break;
		// } else {
		// System.out.print("֧�����벻��ȷ��");
		//
		// }
		// }
		// int i = leaseRecordDao.returnBike(bikeId, user.getId());
		// if (i == -5) {
		// System.out.print("���㣬");
		// recharge(false);
		// } else {
		// System.out.println("�黹�ɹ�!");
		// break;
		// }
		// }
		// break;
		// }
		// } else {
		// System.out.print("���벻�Ϸ�,���������룺");
		// }
		// }
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				Bike bike = bikeDao.queryById(bikeId);
				if (bike == null) {
					System.out.print("�ó��������ڣ����������룺");
					
				} else if (bike.getStatus() != 0 || leaseRecordDao.queryNotReturnRecordId(bikeId).getUserId() != user.getId()) {
					System.out.print("��û�����õ���,���������룺");
				} else {
					//����˲�˵���ҵ��˸��û����賵��������¼
					while (true) {
						boolean openPayPassword = (us.queryUserSetting(user.getId(), "����֧��").equals("1") ? true : false);
						while (!openPayPassword) {
							System.out.print("����������֧�����룺");
							String payPassword = input.next();
							if (isTruePayPw(user, payPassword)) {
								break;
							} else {
								System.out.print("֧�����벻��ȷ��");
							}
						}
						int i = leaseRecordDao.returnBike(bikeId, user.getId());
						if (i == -5) {
							System.out.print("���㣬");
							recharge(false);
						} else {
							System.out.println("�黹�ɹ�!");
							break;
						}
					}
					break;
				}
			} else {
				System.out.print("���벻�Ϸ�,���������룺");
			}
		}
		returnMenu();

	}

	private void leaseBike() {
		userBikes();
		System.out.print("��������Ҫ���ĵ���ID\t[ ��裺ID | ���� ��r ] ��");
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (str.equalsIgnoreCase("r")) {
				userMenu();
				break;
			} else {
				if (iiv.isNumber(str)) {
					bikeId = Integer.parseInt(str);
					Bike bike = bikeDao.queryById(bikeId);
					if (bike == null) {
						System.out.print("��ID�����ڣ����������뵥��Id��");
						// ���
					} else if (bike.getLocationId() != user.getLocationID()) {
						System.out.print("�ó������ڵ�ǰλ���У����������뵥��Id��");
					} else if (bike.getStatus() == 0) {
						System.out.println("�ó����Ѿ�����������������뵥��Id��");
					} else if (bike.getStatus() == -1) {
						System.out.println("�ó����Ѿ��𻵣����������뵥��Id��");
					} else if (bike.getStatus() == 1) {
						int result = leaseRecordDao.doInsert(user.getId(), bikeId);
						if (result == 1) {
							System.out.print("����ɹ���");
							break;
						}
					}
				} else {
					System.out.print("���벻�Ϸ�,���������룺");
				}
			}
		}
		returnMenu();
	}

	/**
	 * ע�ά��
	 * @param user1Id ע���û�id
	 * @param wallet1Id ע���û�Ǯ��
	 */
	private void awardByRegist(int user1Id, int wallet1Id) {
		int user2Id;
		System.out.print("�Ƿ����Ƽ��ˣ�[ �У�y | û�� n ] :");
		String y = input.next();
		if (y.equalsIgnoreCase("y")) {
			System.out.print("�������Ƽ���ID:");
			while (true) {
				String id = input.next();
				if (iiv.isNumber(id)) {
					user2Id = Integer.parseInt(id);
					break;
				} else {
					System.out.print("���벻�Ϸ�,���������룺");
				}
			}
			int x = walletDao.awardByregister(user1Id, wallet1Id, user2Id);
			if (x != 1) {
				System.out.println("���Ƽ�����Ч��"); // TOOD ��Ч��������
			}
		}
	}

	/**
	 * �û�ע��
	 */
	private void userRegister() {
		System.out.print("�����������û�����");
		String username;
		while (true) {
			username = input.next();
			if (userDao.isUserNameExist(username)) {
				System.out.println("���û����Ѿ�����,������ʹ�ã�" + userDao.adviseUsername(username));
				printBoundary();
				System.out.print("�����������û�����");
			} else {
				break;
			}
		}

		String password, password2;
		while (true) {
			System.out.print("�������������룺");
			password = input.next();
			System.out.print("�ٴ������������룺");
			password2 = input.next();
			if (!password.equals(password2)) {
				System.out.println("�������벻һ�£����ٴ����룺");
				printBoundary();
			} else {
				break;
			}
		}
		String payPassword = "";
		System.out.print("����������֧�����룺");
		while (true) {
			payPassword = input.next();
			if (payPassword.equals(password)) {
				System.out.print("֧�����벻�ܺ͵�¼����һ�£����������룺");
			} else {
				break;
			}
		}

		String tel;
		System.out.print("�����������ֻ��ţ�");
		while (true) {
			tel = input.next();
			if (iiv.isTrueTel(tel)) {
				// �ֻ��Ŵ��ڷ���true
				if (userDao.isTelExist(tel)) {
					System.out.print("���ֻ����Ѿ����ڣ������������ֻ��ţ�");
				} else {
					break;
				}
			} else {
				System.out.print("�ֻ��Ų��Ϸ������������ֻ��Ű��������������ֻ��ţ�");
			}

		}

		int register = userDao.register(username, password, tel, payPassword);

		if (register != 0) {

			awardByRegist(register, walletDao.queryByUserId(register).getId());
			System.out.print("ע��ɹ����Ƿ��¼��[ �ǣ�y | �� n ] ��");
			String s = input.next();
			if (s.equalsIgnoreCase("y")) {
				userLogin();
			} else {
				mainMenu();
			}
		} else {
			System.out.println("ע��ʧ��");
		}
	}

	/**
	 * �û��˳�ϵͳ
	 * 
	 * @Title exit
	 */
	private void exit() {
		printBoundary();
		System.out.println("ȷ���˳��������ֶ���[ ȷ�ϣ�y | �ֶ���n ] ");
		String s = input.next();
		if (!s.equalsIgnoreCase("y")) {
			if (user == null) {
				mainMenu();
				return;
			}
			if (user.getIsAdmin()) {
				adminMenu();// ����Ա�˵�
			} else {
				userMenu();// ��ͨ�û��˵�
			}
		} else {
			printBoundary();
			System.out.println("���Ѿ��˳�ϵͳ��û�к�ڵ������,�ڴ����ٴ�ʹ�ã�");
			input.close();
			System.exit(0);
		}
	}

	/**
	 * ���ص�mainMenu
	 * 
	 * @Title returnMenu
	 */
	private void returnMenu() {

		System.out.print(" [ ���أ�y | �˳� ��e ] :");
		String next = input.next();

		if (next.equalsIgnoreCase("y")) {
			if (user == null) {
				mainMenu();
				return;
			}
			if (user.getIsAdmin()) {
				adminMenu();// ����Ա�˵�
			} else {
				userMenu();// ��ͨ�û��˵�
			}
		} else {
			exit();
		}
	}

	/**
	 * ����ѡ�������������̨����Ĺ���
	 */
	private void printBoundary() {
		System.out.println("---------------------------------------");
	}

}
