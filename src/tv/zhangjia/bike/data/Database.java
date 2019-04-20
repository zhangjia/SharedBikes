package tv.zhangjia.bike.data;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.zxing.WriterException;

import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.impl.LocationDaoImpl;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.Options;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.UserOptions;
import tv.zhangjia.bike.entity.UserSettings;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.Zxing;

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
	public static List<Options> OPTIONS = new ArrayList<>();
	public static List<UserOptions> USEROPTIONS = new ArrayList<>();
	private static LocationDao locationDao = new LocationDaoImpl();
	static {
		OPTIONS.add(new Options(nextOptionsId(), "�ۿ�", "0.5"));
		OPTIONS.add(new Options(nextOptionsId(), "��Ա�۸�", "10"));
		OPTIONS.add(new Options(nextOptionsId(), "�ŵų�", "10"));
		OPTIONS.add(new Options(nextOptionsId(), "������", "20"));
		OPTIONS.add(new Options(nextOptionsId(), "���", "�׹��Ļ�ӭ��"));

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
		USERS.add(new User(1, "admin", "1", "13863313959", true, 0, date, 1, "zhangjia"));
		// �����ݿ���Ĭ����Ӷ����û�
		USERS.add(new User(2, "Luffy", "3", "15666335517", false, 0, date, 2,  "3"));

		USEROPTIONS.add(new UserOptions(nextUserOptionsId(), 1,"����֧��", "0"));
		USEROPTIONS.add(new UserOptions(nextUserOptionsId(), 2,"����֧��", "0"));

		WALLETS.add(new Wallet(1, 1, 1000, 0, true, new Date()));
		WALLETS.add(new Wallet(2, 2, 0, 0, false, null));

		BIKES.add((new Bike(1, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 1, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(2, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 2, 2, 1, 0, "��ά��")));
		BIKES.add((new Bike(3, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 3, 1, 0, "��ά��")));
		BIKES.add((new Bike(4, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 4, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(5, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 1, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(6, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 2, 2, 1, 0, "��ά��")));
		BIKES.add((new Bike(7, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 2, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(8, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 3, 1, 0, "��ά��")));
		BIKES.add((new Bike(9, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 1, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(10, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 2, 2, 1, 0, "��ά��")));
		BIKES.add((new Bike(11, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 3, 3, 1, 0, "��ά��")));
		BIKES.add((new Bike(12, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(13, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(14, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(15, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 2, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(16, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 4, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(17, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(18, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 3, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(19, "������", Double.parseDouble(OPTIONS.get(3).getValue()), 2, 1, 1, 0, "��ά��")));
		BIKES.add((new Bike(20, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 2, 4, 1, 0, "��ά��")));
		BIKES.add((new Bike(21, "�ŵų�", Double.parseDouble(OPTIONS.get(2).getValue()), 2, 2, 1, 0, "��ά��")));

		for (Bike bike : BIKES) {
			try {
				Zxing.generateQR(bike);
			} catch (WriterException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
	public static int nextOptionsId() {
		if (OPTIONS.isEmpty()) {
			return 1;
		}
		return OPTIONS.get(OPTIONS.size() - 1).getId() + 1;
	}
	
	/**
	 * ������һ���û����õ�Id
	 * 
	 * @return ��һ��Bike��ID
	 */
	public static int nextUserOptionsId() {
		if (USEROPTIONS.isEmpty()) {
			return 1;
		}
		return USEROPTIONS.get(USEROPTIONS.size() - 1).getId() + 1;
	}

}
