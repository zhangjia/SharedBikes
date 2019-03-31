package tv.zhangjia.bike.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.impl.LocationDaoImpl;
import tv.zhangjia.bike.entity.AdminSettings;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.UserSettings;
import tv.zhangjia.bike.entity.Wallet;

/**
 * ģ�����ݿ�
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.data
 * @ClassName Database
 * @author ZhangJia
 * @Version V1.0
 * @date 2019��3��25�� ����6:31:16
 */
public class Database {
	public static final List<User> USERS = new ArrayList<>();
	public static final List<Bike> BIKES = new ArrayList<>();
	public static final List<Location> LOCATIONS = new ArrayList<>();
	public static final List<LeaseRecord> LEASERECORDS = new ArrayList<>();
	public static final List<Wallet> WALLETS = new ArrayList<>();
	public static final List<Bill> BILLS = new ArrayList<>();
	public static final List<UserSettings> USERSETTINGS = new ArrayList<>();
	public static AdminSettings as = new AdminSettings();
	private static LocationDao locationDao = new LocationDaoImpl();
	static {
		as.setDiscount(0.5);
		as.setVipPrice(10);
		as.setaBikePrice(100);
		as.setbBikePrice(200);
		as.setAdvertising("�׹��Ļ�ӭ��");
		
		
		
		
		String str = "2019-03-28";
		// ��Stringת��ΪDate
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// �����ݿ���Ĭ�����һ������Ա
		USERS.add(new User(1, "1", "1", "15628791997", true, 0, date, 1, 1,"zhangjia"));
		// �����ݿ���Ĭ����Ӷ����û�
		USERS.add(new User(2, "2", "2", "15666335517", false, 0, date, 2, 2,"zhangjia"));
		USERS.add(new User(3, "3", "3", "15620", false, 0, date, 3, 3,"zhangjia"));

		WALLETS.add(new Wallet(1, 1, 1000, 100, true, new Date()));
		WALLETS.add(new Wallet(2, 2, 1000, 200, true, new Date()));
		WALLETS.add(new Wallet(3, 3, 2000, 300, false, new Date()));
		
		
		BIKES.add((new Bike(1, "�ŵų�", as.getaBikePrice(), 1, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(2, "������", as.getbBikePrice(), 2, 2, 1, 0, "��ά��")));
		BIKES.add((new Bike(3, "�ŵų�", as.getaBikePrice(), 3, 3, 1, 0, "��ά��")));
		BIKES.add((new Bike(4, "������", as.getbBikePrice(), 4, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(5, "������", as.getbBikePrice(), 1, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(6, "�ŵų�", as.getaBikePrice(), 2, 2, 1, 0, "��ά��")));
		BIKES.add((new Bike(7, "������", as.getbBikePrice(), 4, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(8, "�ŵų�", as.getaBikePrice(), 3, 3, 1, 0, "��ά��")));
		BIKES.add((new Bike(9, "������", as.getbBikePrice(), 1, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(10, "�ŵų�", as.getaBikePrice(), 2, 2, 1, 0, "��ά��")));
		BIKES.add((new Bike(11, "������", as.getbBikePrice(), 3, 3, 1, 0, "��ά��")));
		BIKES.add((new Bike(12, "�ŵų�", as.getaBikePrice(), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(13, "������", as.getbBikePrice(), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(14, "�ŵų�", as.getaBikePrice(), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(15, "������", as.getbBikePrice(), 1, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(16, "�ŵų�", as.getaBikePrice(), 4, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(17, "������", as.getbBikePrice(), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(18, "�ŵų�", as.getaBikePrice(), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(19, "������", as.getbBikePrice(), 1, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(20, "�ŵų�", as.getaBikePrice(), 4, 4, 1, 0, "��ά��")));
		


		
		// ��������������ֱ���뵽��Ӧ��λ����
		LOCATIONS.add((new Location(1, "�������")));
		
		LOCATIONS.add((new Location(2, "��ɫ��԰")));
		LOCATIONS.add((new Location(3, "���깫Ԣ")));
		LOCATIONS.add((new Location(4, "���㳡")));
		
		locationDao.updateLocationBikes(1);
		locationDao.updateLocationBikes(2);
		locationDao.updateLocationBikes(3);
		locationDao.updateLocationBikes(4);

		
	}

	/**
	 * ������һ���û���ID
	 * 
	 * @return ��һ���û���ID
	 */
	public static int nextUserId() {
		if (USERS.isEmpty()) {
			return 1;
		}
		return USERS.get(USERS.size() - 1).getId() + 1;
	}

	/**
	 * ������һ��Bike��ID
	 * 
	 * @return ��һ��Bike��ID
	 */
	public static int nextBikeId() {
		if (BIKES.isEmpty()) {
			return 1;
		}
		return BIKES.get(BIKES.size() - 1).getId() + 1;
	}

	/**
	 * ������һ����¼��ID
	 * 
	 * @return ��һ��Bike��ID
	 */
	public static int nextLeaseRecordId() {
		if (LEASERECORDS.isEmpty()) {
			return 1;
		}
		return LEASERECORDS.get(LEASERECORDS.size() - 1).getId() + 1;
	}

	/**
	 * ������һ��λ�õ�ID
	 * 
	 * @return ��һ��Bike��ID
	 */
	public static int nextLocationId() {
		if (LOCATIONS.isEmpty()) {
			return 1;
		}
		return LOCATIONS.get(LOCATIONS.size() - 1).getId() + 1;
	}

	/**
	 * ������һ��Ǯ����ID
	 * 
	 * @return ��һ��Bike��ID
	 */
	public static int nextWalletId() {
		if (WALLETS.isEmpty()) {
			return 1;
		}
		return WALLETS.get(WALLETS.size() - 1).getId() + 1;
	}

	/**
	 * ������һ���˵���ID
	 * 
	 * @return ��һ��Bike��ID
	 */
	public static int nextBillId() {
		if (BILLS.isEmpty()) {
			return 1;
		}
		return BILLS.get(BILLS.size() - 1).getId() + 1;
	}

	
	/**
	 * ������һ���û����õ�Id
	 * 
	 * @return ��һ��Bike��ID
	 */
	public static int nextUserSettingsId() {
		if (USERSETTINGS.isEmpty()) {
			return 1;
		}
		return USERSETTINGS.get(USERSETTINGS.size() - 1).getId() + 1;
	}

}
