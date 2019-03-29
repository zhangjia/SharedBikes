package tv.zhangjia.bike.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;
/**
 * 模拟数据库
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.data	
 * @ClassName	Database	
 * @author	ZhangJia
 * @Version	V1.0
 * @date	2019年3月25日 下午6:31:16
 */
public class Database {
	public static final List<User> USERS = new ArrayList<>();
	public static final List<Bike> BIKES = new ArrayList<>();
	public static final List<Location> LOCATIONS = new ArrayList<>();
	public static final List<LeaseRecord> LEASERECORDS = new ArrayList<>();
	public static final List<Wallet> WALLETS = new ArrayList<>();
	static {
		String str = "2019-03-28";
		//将String转换为Date
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//向数据库中默认添加一个管理员
			USERS.add(new User(1,"1","1","15628",true,0,date,1,1));
		//向数据库中默认添加二个用户
			USERS.add(new User(2,"2","2","15629",false,0,date,2,2));
			USERS.add(new User(3,"3","3","15620",false,0,date,3,3));
			
			
			WALLETS.add(new Wallet(1,1,1000,100,true,new Date()));
			WALLETS.add(new Wallet(2,2,1000,200,true,new Date()));
			WALLETS.add(new Wallet(3,3,2000,300,false,new Date()));
			
			BIKES.add((new Bike(1,"脚蹬车",0.1,1,1,0,"二维码")));
			BIKES.add((new Bike(2,"助力车",0.2,2,1,0,"二维码")));
			BIKES.add((new Bike(3,"助力车",0.2,3,1,0,"二维码")));
			BIKES.add((new Bike(4,"脚蹬车",0.1,4,1,0,"二维码")));
			
			
			//将四个位置的四辆车存在List中
			List<Bike> b1 = new ArrayList<Bike>(); 
			b1.add(BIKES.get(0));
			List<Bike> b2 = new ArrayList<Bike>(); 
			b1.add(BIKES.get(1));
			List<Bike> b3 = new ArrayList<Bike>(); 
			b1.add(BIKES.get(2));
			List<Bike> b4 = new ArrayList<Bike>(); 
			b1.add(BIKES.get(3));
			
			//将上面的四辆车分别加入到对应的位置中
			LOCATIONS.add((new Location(1,"创意大厦",b1,1)));
			LOCATIONS.add((new Location(2,"绿色家园",b2,1)));
			LOCATIONS.add((new Location(3,"青年公寓",b3,1)));
			LOCATIONS.add((new Location(4,"万达广场",b4,1)));
	}
	
	/**
	 * 生成下一个用户的ID
	 * @return 下一个用户的ID
	 */
	public static int nextUserId() {
		if(USERS.isEmpty()) {
			return 1;
		}
		return USERS.get(USERS.size() - 1).getId() + 1;
	}
	
	/**
	 * 生成下一个Bike的ID
	 * @return 下一个Bike的ID
	 */
	public static int nextBikeId() {
		if(BIKES.isEmpty()) {
			return 1;
		}
		return BIKES.get(BIKES.size() - 1).getId() + 1;
	}
	
	
	/**
	 * 生成下一个记录的ID
	 * @return 下一个Bike的ID
	 */
	public static int nextLeaseRecordId() {
		if(LEASERECORDS.isEmpty()) {
			return 1;
		}
		return LEASERECORDS.get(LEASERECORDS.size() - 1).getId() + 1;
	}
	
	
	/**
	 * 生成下一个位置的ID
	 * @return 下一个Bike的ID
	 */
	public static int nextLocationId() {
		if(LOCATIONS.isEmpty()) {
			return 1;
		}
		return LOCATIONS.get(LOCATIONS.size() - 1).getId() + 1;
	}
	
	/**
	 * 生成下一个钱包的ID
	 * @return 下一个Bike的ID
	 */
	public static int nextWalletId() {
		if(WALLETS.isEmpty()) {
			return 1;
		}
		return WALLETS.get(WALLETS.size() - 1).getId() + 1;
	}
	
	
}
