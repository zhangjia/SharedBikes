package tv.zhangjia.bike.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;
/**
 * ģ�����ݿ�
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.data	
 * @ClassName	Database	
 * @author	ZhangJia
 * @Version	V1.0
 * @date	2019��3��25�� ����6:31:16
 */
public class Database {
	public static final List<User> USERS = new ArrayList<>();
	public static final List<Bike> BIKES = new ArrayList<>();
	public static final List<Location> LOCATIONS = new ArrayList<>();
	public static final List<LeaseRecord> LEASERECORDS = new ArrayList<>();
	public static final List<Wallet> WALLETS = new ArrayList<>();
	static {
		//�����ݿ���Ĭ�����һ������Ա
			USERS.add(new User(1,"1","1","15628",true,new Date(),new Date(),1,new Wallet(1,100,10,true,new Date(),10,0.8)));
		//�����ݿ���Ĭ�����һ�����ݿ�
			USERS.add(new User(1,"2","2","15629",false,new Date(),new Date(),2,new Wallet(2,100,10,true,new Date(),10,0.8)));
			
			
			BIKES.add((new Bike(1,"�ŵų�",0.1,1,1,0,"��ά��")));
			BIKES.add((new Bike(2,"������",0.2,2,1,0,"��ά��")));
			BIKES.add((new Bike(3,"������",0.2,3,1,0,"��ά��")));
			BIKES.add((new Bike(4,"�ŵų�",0.1,4,1,0,"��ά��")));
			
			
			//���ĸ�λ�õ�����������List��
			List<Bike> b1 = new ArrayList<Bike>(); 
			b1.add(BIKES.get(0));
			List<Bike> b2 = new ArrayList<Bike>(); 
			b1.add(BIKES.get(1));
			List<Bike> b3 = new ArrayList<Bike>(); 
			b1.add(BIKES.get(2));
			List<Bike> b4 = new ArrayList<Bike>(); 
			b1.add(BIKES.get(3));
			
			//��������������ֱ���뵽��Ӧ��λ����
			LOCATIONS.add((new Location(1,"�������",b1,1)));
			LOCATIONS.add((new Location(2,"��ɫ��԰",b1,1)));
			LOCATIONS.add((new Location(3,"���깫Ԣ",b1,1)));
			LOCATIONS.add((new Location(4,"���㳡",b1,1)));
	}
	
	/**
	 * ������һ���û���ID
	 * @return ��һ���û���ID
	 */
	public static int nextUserId() {
		if(USERS.isEmpty()) {
			return 1;
		}
		return USERS.get(USERS.size() - 1).getId() + 1;
	}
	
	/**
	 * ������һ��Bike��ID
	 * @return ��һ��Bike��ID
	 */
	public static int nextBikeId() {
		if(BIKES.isEmpty()) {
			return 1;
		}
		return BIKES.get(BIKES.size() - 1).getId() + 1;
	}
	
	
	/**
	 * ������һ����¼��ID
	 * @return ��һ��Bike��ID
	 */
	public static int nextLeaseRecordId() {
		if(LEASERECORDS.isEmpty()) {
			return 1;
		}
		return LEASERECORDS.get(LEASERECORDS.size() - 1).getId() + 1;
	}
	
	
	/**
	 * ������һ��λ�õ�ID
	 * @return ��һ��Bike��ID
	 */
	public static int nextLocationId() {
		if(LOCATIONS.isEmpty()) {
			return 1;
		}
		return LOCATIONS.get(LOCATIONS.size() - 1).getId() + 1;
	}
	
	/**
	 * ������һ��Ǯ����ID
	 * @return ��һ��Bike��ID
	 */
	public static int nextWalletId() {
		if(WALLETS.isEmpty()) {
			return 1;
		}
		return WALLETS.get(WALLETS.size() - 1).getId() + 1;
	}
	
	
}
