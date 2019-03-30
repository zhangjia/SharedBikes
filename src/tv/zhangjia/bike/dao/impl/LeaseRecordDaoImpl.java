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
import tv.zhangjia.bike.entity.Wallet;

public class LeaseRecordDaoImpl implements LeaseRecordDao {
	private AdminSettings as = Database.as;
	private List<LeaseRecord> lrs = Database.LEASERECORDS;
	// �賵��ʱ�����ɽ賵��¼ʱ�����û���
	//TODO ���������û�ID�����û����ķ���
	private UserDao userDao = new UserDaoImpl();
	
	// �賵��ʱ�򣬲鿴����״̬������Id���ظó�
	private BikeDao bikeDao = new BikeDaoImpl();
	
	//����id����Ǯ��
	private WalletDao walletDao = new WalletDaoImpl();
	
	//����locationId����location
	private LocationDao locationDao = new LocationDaoImpl();

	@Override
	public int doInsert(int userId, int bikeId) {
		int s = bikeDao.bikeStatus(bikeId);
		if (s == 11) {
			Bike bike = bikeDao.queryById(bikeId);
//			locationDao.changeBikeLocation(bikeId,-1, bike.getLocationId());
			
			bike.setStatus(0); // ����Ϊ���״̬
			bike.setAmount(bike.getAmount() + 1); // �������+1
			bike.setLastLocationId(bike.getLocationId());
			bike.setLocationId(-1); //-1Ϊ����״̬
			locationDao.updateLocationBikes(bike.getLastLocationId());
			//���ɽ賵��¼
			LeaseRecord lr = new LeaseRecord(Database.nextLeaseRecordId(), bikeId, userId,
					userDao.queryUserName(userId), new Date(), null, "������",0);
			lrs.add(lr);
			
			
			return 1; // ����ɹ�
		}

		return s; // ����10 �������Ѿ����裬����-1������ID������
		// 1:����ɹ�
		// s��
		// 10:�ó����Ѿ���������
		// -1��ID������
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
				System.out.println("���۸�" + as.getaBikePrice());
				
				double price = bike.getType().equals("������") ? as.getbBikePrice() : as.getaBikePrice();
				
				//�������ѽ��
				int i = walletDao.pay(userId, second * price,"�⳵");
				System.out.println("i" + i);
				if(i != 1) {
					return -5;
				}
				
//				locationDao.changeBikeLocation(bike.getId(), bike.getLocationId(), -1);
				//���ļ�¼���ѽ��
				lr.setCost(second * price);
				//���ļ�¼ʱ��
				lr.setReturnTime(returnTime);
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
			return 0;
		} else {
			return s;
		}

		// 1���黹�ɹ�
		// 0�����Ǹ��û�
		// s��
		// -1��û�д�ID
		// 11���˵���û�б���
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

}
