package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.AdminSettings;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;

public class LeaseRecordDaoImpl implements LeaseRecordDao {
	private AdminSettings as = Database.as;
	private List<LeaseRecord> lrs = Database.LEASERECORDS;
	private List<User> users = Database.USERS;
	// �賵��ʱ�����ɽ賵��¼ʱ�����û���
	//TODO ���������û�ID�����û����ķ���
//	private UserDao userDao = new UserDaoImpl();
	
	// �賵��ʱ�򣬲鿴����״̬������Id���ظó�
//	private BikeDao bikeDao = new BikeDaoImpl();
	
	//����id����Ǯ��
//	private WalletDao walletDao = new WalletDaoImpl();
	
	//����locationId����location
//	private LocationDao locationDao = new LocationDaoImpl();

	@Override
	public int doInsert(int userId, int bikeId) {
		 UserDao userDao = new UserDaoImpl();
		 BikeDao bikeDao = new BikeDaoImpl();
		 LocationDao locationDao = new LocationDaoImpl();
		int s = bikeDao.bikeStatus(bikeId);
		//�����11,˵��״̬�ɽ�
		if (s == 11) {
			Bike bike = bikeDao.queryById(bikeId);
			
			bike.setStatus(0); // ����Ϊ���״̬
			bike.setAmount(bike.getAmount() + 1); // �������+1
			bike.setLastLocationId(bike.getLocationId());
			bike.setLocationId(-1); //-1Ϊ����״̬
			System.out.println(bike.getLocationId());
			locationDao.updateLocationBikes(bike.getLastLocationId());
			//���ɽ賵��¼
			LeaseRecord lr = new LeaseRecord(Database.nextLeaseRecordId(), bikeId, userId,
					userDao.queryUserName(userId), new Date(), null, "������",0,0);
			lrs.add(lr);
			
			
			return 1; // ����ɹ�
		}

		return s;
		// s = 10 �������Ѿ�����
		// s = 5������ID������
		// s = -1��������
	}

	@Override
	public List<LeaseRecord> queryAll() {
		return lrs;
	}

	@Override
	public List<LeaseRecord> queryByUserId(int id) {
		List<LeaseRecord> ulr = new ArrayList<>();
		for (LeaseRecord leaseRecord : lrs) {
			if (leaseRecord.getUserId() == id) {
				ulr.add(leaseRecord);
			}
		}
		return ulr;
	}

	@Override
	public LeaseRecord queryById(int id) {
		for (LeaseRecord leaseRecord : lrs) {
			if (leaseRecord.getId() == id) {
				return leaseRecord;
			}
		}
		return null;
	}

	@Override
	public List<LeaseRecord> queryNotReturnByUserId(int userId) {
		List<LeaseRecord> records = new ArrayList<>();
		for (LeaseRecord record : this.lrs) {
			if (record.getUserId() == userId && record.getReturnTime() == null) {
				records.add(record);
			}
		}
		return records;
	}

	@Override
	public int returnBike(int bikeId, int userId) {
		UserDao userDao = new UserDaoImpl();
		BikeDao bikeDao = new BikeDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		 LocationDao locationDao = new LocationDaoImpl();
		int s = bikeDao.bikeStatus(bikeId);
		Wallet w = walletDao.queryByUserId(userId);
		if (s == 10) {
			Bike bike = bikeDao.queryById(bikeId);
			// �ҵ���Ҫ����ļ�¼ID
			int recordId = queryNotReturnRecordId(bikeId);
			// ���ظü�¼
			LeaseRecord lr = queryById(recordId);
			if (lr.getUserId() == userId) {
				Date returnTime = new Date();
				//��������ʱ�����룩
				Date lendTime = lr.getLeaseTime();
				long second = (returnTime.getTime() - lendTime.getTime()) / 1000;
				//���ݵ������Ͳ����ۼ�
				double price = bike.getType().equals("������") ? as.getbBikePrice() : as.getaBikePrice();
				
				double discount = w.isVIP() ? as.getDiscount() : 1 ;
				
				
				double cost = second * price * discount;
				//�������ѽ�֧��
				int i = walletDao.pay(userId, cost,"�⳵");
				
				//���ûǮ������-5
				if(i != 1) {
					return -5;
				}
				
				//���ļ�¼���ѽ��
				lr.setCost(cost);
				//���Ĺ黹ʱ��
				lr.setReturnTime(returnTime);
				//�������ʱ��
				lr.setTime(second);
//		G:		User user  = queryUserByUserId(userId);
				User user  = userDao.queryByUserId(userId);
				user.setCyclingTime(user.getCyclingTime() + second);
				
				
				//���ĳ���״̬
				bike.setStatus(1);
				//��ȡ������ǰλ�õ�ID
				int startLocationId = bike.getLastLocationId();
				//��ȡ������ǰλ�õ�����
				String start = locationDao.queryLocationName(startLocationId);
				
				//��ȡ�µ�λ��
				Location nowLocation =locationDao.randomLocation(bike.getLastLocationId(), bikeId, lr.getId());
				//��ȡ�µ�λ������
				String end = nowLocation.getLocation();
				//1.  �޸ĵ�����λ��
				
						
				bike.setLocationId(nowLocation.getId());
				
				//2.  �޸����޼�¼��λ��
				
				lr.setLocations(start + " ---> " + end);
				
				
				locationDao.updateLocationBikes(bike.getLocationId());
//				locationDao.updateLocationBikes(bike.getLastLocationId());
				
				return 1;// �黹�ɹ�

			}
			return 0; //���Ǹ��û�
		} else {
			return s;
		}

		// 0�����Ǹ��û�
		// -5 : ���ûǮ��
		// s = 11��û�б����
		// s = 5 ��û�и�ID
		// s = -1���ó��Ѿ���
	
	}

	@Override
	public int queryNotReturnRecordId(int bikeId) {
		for (LeaseRecord lr : lrs) {
			// ��Ϊһ�����ӿ��ܲ���������¼������δ�黹�ļ�¼ֻ��һ��������ͨ���������������Է���Ψһ�ļ�¼ID
			if (lr.getBikeId() == bikeId && lr.getReturnTime() == null) {
				return lr.getId();
			}
		}
		return -1;// û�ҵ�����-1
	}

/*	private User queryUserByUserId(int userId) {
		for (User user : users) {
			if(user.getId() == userId) {
				return user;
			}
		}
		return null;
	}*/
	
}
