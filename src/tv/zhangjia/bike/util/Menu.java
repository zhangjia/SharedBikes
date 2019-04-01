package tv.zhangjia.bike.util;

import java.util.List;
import java.util.Scanner;

import tv.zhangjia.bike.dao.AdminSettingsDao;
import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.UserSettingsDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.dao.impl.AdminSettinsDaoImpl;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.dao.impl.BillDaoImpl;
import tv.zhangjia.bike.dao.impl.LeaseRecordDaoImpl;
import tv.zhangjia.bike.dao.impl.LocationDaoImpl;
import tv.zhangjia.bike.dao.impl.UserDaoImpl;
import tv.zhangjia.bike.dao.impl.UserSettingsDaoImpl;
import tv.zhangjia.bike.dao.impl.WalletDaoImpl;
import tv.zhangjia.bike.entity.AdminSettings;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.UserSettings;
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
	private AdminSettingsDao as = new AdminSettinsDaoImpl();
	private UserSettingsDao us = new UserSettingsDaoImpl();

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
		System.out.println();
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
			// û���ֻ����㷢��֤�룬ֻ��ί�����Լ������
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
					damage();
					break;
				case 6:
					queryLocation();//
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
					queryUsersWallet();//
					break;
				case 11:
					queryUsersBill();//
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
			} else {
				System.out.println("���벻�Ϸ������������룺");
			}
		}

	}

	private void queryUsersBill() {
		System.out.println("�������û���Ǯ����Ϣ");
		List<Bill> bills = billDao.queryAll();
		for (Bill bill : bills) {
			System.out.println(bill);
		}

	}

	private void queryUsersWallet() {
		System.out.println("�������û���Ǯ����Ϣ");
		List<Wallet> wallets = walletDao.queryAll();
		for (Wallet wallet : wallets) {
			System.out.println(wallet);
		}

	}

	private void damage() {
		List<Bike> bikes = bikeDao.queryByDamage();
		System.out.println("�������𻵵ĳ���");
		for (Bike bike : bikes) {

			System.out.println(bike);
		}

	}

	private void dispatch() {
		List<String> arr = locationDao.dispatch();
		for (String string : arr) {
			System.out.println(string);
		}
		locationDao.dispatch();

	}

	private void advertising(User user) {
		Wallet wt = walletDao.queryByUserId(user.getId());
		if (wt.isVIP()) {

		} else {
			AdminSettings ass = as.queryAdminSettings();
			System.out.println(ass.getAdvertising());
		}
	}

	private void systemSettings() {
		AdminSettings ass = as.queryAdminSettings();
		System.out.println("�����ǹ���Ա����");
		System.out.println("1. ���ýŵų��۸�");
		System.out.println("2. �����������۸�");
		System.out.println("3. ���ÿ���Ա�۸�");
		System.out.println("4. ���û�Ա���ۿ�");
		System.out.println("5. ����վ�ڵĹ��");
		System.out.println("��ѡ�����ã�");
		while (true) {
			String index = input.next();
			if (iiv.isNumber(index)) {
				switch (Integer.parseInt(index)) {
				case 1:
					System.out.println("������ŵų��۸�");
					while (true) {
						String price = input.next();
						if (iiv.isDouble(price)) {
							double dprice = Double.parseDouble(price);
							ass.setaBikePrice(dprice);
							System.out.println("���óɹ�");
							break;
						} else {
							System.out.println("���벻�Ϸ������������룺");

						}
					}
					break;
				case 2:
					System.out.println("�������������۸�");
					while (true) {
						String price = input.next();
						if (iiv.isDouble(price)) {
							double dprice = Double.parseDouble(price);
							ass.setbBikePrice(dprice);
							System.out.println("���óɹ�");
							break;
						} else {
							System.out.println("���벻�Ϸ������������룺");

						}
					}
					break;
				case 3:
					System.out.println("�������Ա/�¼۸�");
					while (true) {
						String price = input.next();
						if (iiv.isDouble(price)) {
							double dprice = Double.parseDouble(price);
							ass.setVipPrice(dprice);
							System.out.println("���óɹ�");
							break;
						} else {
							System.out.println("���벻�Ϸ������������룺");

						}
					}
					break;
				case 4:
					while (true) {
						String discount = input.next();
						if (iiv.isDouble(discount)) {
							double ddiscount = Double.parseDouble(discount);
							ass.setDiscount(ddiscount);
							System.out.println("���óɹ�");
							break;
						} else {
							System.out.println("���벻�Ϸ������������룺");

						}
					}
					break;
				case 5:
					System.out.println("�����������ݣ�");
					String advertising = input.next();
					ass.setAdvertising(advertising);
					System.out.println("���óɹ�");
					break;
				default:
					System.out.println("û�и�ѡ�������ѡ��");
				}
			} else {
				System.out.println("���벻�Ϸ���������ѡ��");
			}

			adminMenu();
		}

	}

	private void addBikequeryLocation() {
		System.out.println("���������е�λ����Ϣ��");
		List<Location> locations = locationDao.queryAll();
		System.out.println("���\tλ������\t��������");
		for (Location location : locations) {
			System.out.println(location);
		}
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
			List<Bike> bs = lo.getBikes();
			for (Bike bike : bs) {

				System.out.println(bike);
			}

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
			for (User user2 : user) {
				System.out.println(user2);
			}
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
		Bike bike = null;
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				bike = bikeDao.queryById(bikeId);
				if (bike == null) {
					System.out.println("�����ڴ�id");
				} else if (bike.getStatus() == 0) {
					System.out.println("�˵������ܱ�ɾ��");
				} else {
					break;
				}
			} else {
				System.out.print("���벻�Ϸ���");
			}

			System.out.println("����������Ҫɾ����ID");
		}

		int locationId = bike.getLocationId();
		if (bikeDao.doDelete(bikeId)) {
//			locationDao.deleteLocationBikes(locationId,bikeId);
			locationDao.updateLocationBikes(locationId);
			System.out.println("ɾ���ɹ�");
		} else {
			System.out.println("ɾ��ʧ��");
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
		printBoundary();
		Bike bike = null;
		System.out.println("��������Ҫ�޸ĵĵ���ID");
		int bikeId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				bikeId = Integer.parseInt(str);
				bike = bikeDao.queryById(bikeId);

				if (bike == null) {
					System.out.println("û�и�ID");
				} else if (bike.getStatus() != 1) {
					System.out.println("�˳��������޸ģ�����������ID��");

				} else {
					break;
				}
			} else {
				System.out.println("���벻�Ϸ�");
			}
		}

		System.out.println("�����뵥�����ͣ�");
		String type;
		double price;
		while (true) {
			type = input.next();
			if (type.equals("�ŵų�")) {
				price = as.queryAdminSettings().getaBikePrice();
				break;
			} else if (type.equals("������")) {
				price = as.queryAdminSettings().getbBikePrice();
				break;
			} else {
				System.out.println("û�иó��ͣ����������룺");
			}
		}

		System.out.println("������λ��ID��");
		int locationId = 1;
		while (true) {

			String str = input.next();
			if (iiv.isNumber(str)) {
				locationId = Integer.parseInt(str);
				if (locationDao.queryLocation(locationId) == null) {
					System.out.println("û�и�λ��");
				} else {
					break;
				}

			} else {
				System.out.print("���벻�Ϸ���");
			}
			System.out.println("������ѡ��λ��ID��");
		}

		System.out.println("������״̬��");
		int status = 1;
		while (true) {
			String str = input.next();
			if (iiv.isInt(str)) {
				status = Integer.parseInt(str);
				if (status != 1 && status != 0 && status != -1) {
					System.out.println("û�д�״̬�����������룺");
				} else {
					break;
				}
			} else {
				System.out.println("״̬���벻�Ϸ������������룺");
			}
		}
		System.out.println("�����������");
		int amount = 0;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				amount = Integer.parseInt(str);
				break;
			} else {
				System.out.println("���벻�Ϸ������������룺");
			}
		}
		String qr = "y";

		Bike bike2 = new Bike(bikeId, type, price, locationId, status, amount, qr);
		boolean doUpdate = bikeDao.doUpdate(bike2);
		if (doUpdate) {
			System.out.println("�޸ĳɹ�");
		} else {
			System.out.println("�޸�ʧ��");

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
		printBoundary();
		// System.out.println("��ӵ���");
		System.out.print("�����뵥�����ͣ��ŵų�/����������");
		String type;
		double price;
		while (true) {
			type = input.next();
			if (type.equals("�ŵų�")) {
				price = as.queryAdminSettings().getaBikePrice();
				break;
			} else if (type.equals("������")) {
				price = as.queryAdminSettings().getbBikePrice();
				break;
			} else {
				System.out.println("û�иó��ͣ����������룺");
			}
		}

		addBikequeryLocation();
		System.out.println("��Ҫ���ó���ӵ��ĸ�λ�ã�(ID)��");

		int locationId = -1;
		while (true) {
			String str = input.next();
			if (iiv.isNumber(str)) {
				locationId = Integer.parseInt(str);
				if (locationDao.queryLocation(locationId) == null) {
					System.out.println("û�и�λ��");
				} else {
					break;
				}

			} else {
				System.out.print("���벻�Ϸ���");
			}
			System.out.println("������ѡ��λ��ID��");
		}
		String qr = "��֤��";
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
		printBoundary();
		System.out.println("������ϵͳ�����еĵ��������Ϣ");
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
		advertising(user);
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
		System.out.println("\t10.��������");
		System.out.println("\t11.���ϱ���");

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
				case 10:
					personSettings();// �˳�
					break;
				case 11:
					awardByRepairs();// ���޽���
					break;
				default:
					System.out.print("û�и�ѡ����������룺");
				}
			} else {
				System.out.println("���벻�Ϸ������������룺");
			}
		}

	}

	private void awardByRepairs() {

		System.out.println("�������𻵵ĳ���");
		String id = input.next();
		while (true) {
			if (iiv.isNumber(id)) {
				int bikeId = Integer.parseInt(id);
				int status = bikeDao.bikeStatus(bikeId);
				if (status != 11) {
					System.out.println("�ó��޷�����");
					break;
				}

				int walletId = bikeDao.setDamage(user, bikeId);

				billDao.awardByBike(user.getId(), walletId);

				break;
			} else {
				System.out.println("���벻�Ϸ������������룺");
			}
		}

	}

	private void personSettings() {
		System.out.println("�������������ã�");
		UserSettings ps = us.queryUserSetting(user.getId());
		String s = ps.isActp() ? "��" : "��";
		System.out.println("�Զ�֧����" + s);
		System.out.println("�򿪣�t,�رգ�f,���������");
		String auto = input.next();
		if (auto.equals("t")) {
			ps.setActp(true);
		} else if (auto.equals("f")) {
			ps.setActp(false);
		} else {
			userMenu();

		}

	}

	private void recharge() {
		UserSettings ps = us.queryUserSetting(user.getId());
		while (true) {
			System.out.println("�������ֵ��");
			String money = input.next();
			if (iiv.isDouble(money)) {
				double m = Double.parseDouble(money);

				boolean openPayPassword = ps.isActp() ? true : false;
				System.out.println("shifou" + openPayPassword);
				while (!openPayPassword) {
					System.out.println("����������֧�����룺");
					String payPassword = input.next();
					if (isTruePayPw(user, payPassword)) {
						break;
					} else {
						System.out.println("֧�����벻��ȷ");

					}
				}

				if (walletDao.recharge(user.getWalletID(), m) == 1) {
					System.out.println("��ֵ�ɹ�");
					// personWallet();
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
		System.out.println(user.getId());
		Wallet wallet = walletDao.queryByUserId(user.getId());
		System.out.println(wallet.getId());
		System.out.println("���\t�û���\t�û����\t�Ż�ȯ���\t�û��ȼ�\tVIPʱ��");
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

		UserSettings ps = us.queryUserSetting(user.getId());
		boolean openPayPassword = ps.isActp() ? true : false;

		while (!openPayPassword) {
			System.out.println("����������֧�����룺");
			String payPassword = input.next();
			if (isTruePayPw(user, payPassword)) {
				break;
			} else {
				System.out.println("֧�����벻��ȷ");

			}
		}
		while (true) {
			int result = walletDao.becomeVIP(user.getId(), month);
			if (result == -5) {
				recharge();
			} else {
				System.out.println("��ϲ����ͨ�ɹ�");

				System.out.println("hahahahahahhah");
				break;
			}
		}
		userMenu();

	}

	private boolean isTruePayPw(User user, String payPassword) {
		List<User> users = userDao.queryAll();

		for (User user2 : users) {
			if (user2.getPayPassword().equals(payPassword) && user.getId() == user.getId()) {
				return true;
			}
		}
		return false;
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
		System.out.println("��������Ҫ�黹�ĵ���Id");
		int bikeId = input.nextInt();
		UserSettings ps = us.queryUserSetting(user.getId());
		boolean openPayPassword = ps.isActp() ? true : false;

		while (!openPayPassword) {
			System.out.println("����������֧�����룺");
			String payPassword = input.next();
			if (isTruePayPw(user, payPassword)) {
				break;
			} else {
				System.out.println("֧�����벻��ȷ");

			}
		}

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
		System.out.println("��������Ҫ���ĵ���ID��");
		int bikeId = input.nextInt();
		int result = leaseRecordDao.doInsert(user.getId(), bikeId);
		if (result == 1) {
			System.out.println("����ɹ���");
			userMenu();
		} else if (result == 10) {
			System.out.println("�ó����Ѿ������");
			userMenu();
		} else if (result == 5) {
			System.out.println("�ó���ID������");
			userMenu();
		} else if (result == -1) {
			System.out.println("�ó����Ѿ���");
			userMenu();
		}
	}

	private void awardRe(int user1Id, int wallet1Id) {
		int user2Id;
		System.out.println("�Ƿ����Ƽ��ˣ�y");
		String y = input.next();
		if (y.equals("y")) {
			System.out.println("�������Ƽ���ID");
			while (true) {
				String id = input.next();
				if (iiv.isNumber(id)) {
					user2Id = Integer.parseInt(id);
					break;
				} else {
					System.out.println("���벻�Ϸ�");
				}
			}
			int x = billDao.awardByregister(user1Id, wallet1Id, user2Id);
			if (x == -1) {
				System.out.println("�����ڸ��û�");
			} else {
			}

		}
	}

	/**
	 * �û�ע��
	 * 
	 * @Title userRegister
	 */
	private void userRegister() {
		System.out.print("�����������û�����");
		String username;
		while (true) {
			username = input.next();
			if (userDao.isTrueUserName(username) == 1) {
				System.out.println("���û����Ѿ�����,������ʹ�ã�" + userDao.adviseUsername(username));

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
				System.out.println("�������벻һ�£����ٴ�����");
			} else {
				break;
			}
		}

		System.out.println("����������֧�����룺");
		String payPassword = input.next();

		String tel;
		while (true) {
			System.out.print("�����������ֻ��ţ�");
			tel = input.next();
			if (userDao.isTelExist(tel)) {
				break;
			} else {
				System.out.println("���ֻ����Ѿ�����");
			}
		}

		int register = userDao.register(username, password, tel, payPassword);

		if (register == 1) {
			int uid = userDao.queryUserId(username);
			User u = userDao.queryByUserId(uid);
			awardRe(uid, u.getWalletID());
			System.out.println("ע��ɹ����Ƿ��¼��");
			String s = input.next();
			if (s.equals("y")) {
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

	private void printBoundary() {
		System.out.println("----------------------------------");
	}

}
