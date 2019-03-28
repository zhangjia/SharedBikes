package tv.zhangjia.bike.data;

import java.util.ArrayList;
import java.util.List;

import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.User;
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
	static {
		//�����ݿ���Ĭ�����һ������Ա
			USERS.add(new User(1,"a","a","15628",true,10,0,0,"2019-03-25"));
		//�����ݿ���Ĭ�����һ�����ݿ�
			USERS.add(new User(2,"b","b","15629",false,10,0,0,"2019-03-25"));
			
			BIKES.add((new Bike(1,"�ŵų�",0.1,"�������",1,0,"��ά��")));
			BIKES.add((new Bike(2,"������",0.2,"��ɫ��԰",1,0,"��ά��")));
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
	
}
