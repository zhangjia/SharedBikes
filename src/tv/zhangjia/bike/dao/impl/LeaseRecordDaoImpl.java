package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;

public class LeaseRecordDaoImpl implements LeaseRecordDao {
	private List<LeaseRecord> lrs = Database.LEASERECORDS;
	private List<Bike> bikes = Database.BIKES;
	private UserDao userDao = new UserDaoImpl();
	private BikeDao bikeDao = new BikeDaoImpl();

	@Override
	public int doInsert(int userId, int bikeId) {
		int s = bikeDao.bikeStatus(bikeId);
		if (s == 11) {
			Bike bike = bikeDao.queryById(bikeId);
			bike.setStatus(0); // ����Ϊ���״̬
			bike.setAmount(bike.getAmount() + 1); // �������+1
			LeaseRecord lr = new LeaseRecord(Database.nextLeaseRecordId(), bikeId, userId,
					userDao.queryUserName(userId), new Date(), null, 0);
			lrs.add(lr);
			return 1;   //����ɹ�
		}

		return s;    	//����10 �������Ѿ����裬����-1������ID������
//		1:����ɹ�
//		s��
//			10:�ó����Ѿ���������
//			-1��ID������
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
		

		if (s == 10) {
			Bike bike = bikeDao.queryById(bikeId);
			//�ҵ���Ҫ����ļ�¼ID
			int recordId = queryNotReturnRecordId(bikeId);
			//���ظü�¼
			LeaseRecord lr = queryById(recordId);
			if(lr.getUserId() == userId) {
				Date returnTime = new Date();
				lr.setReturnTime(returnTime);
				// //����֧���Ľ��
				// //1.��������ʱ�����룩
				// Date lendTime = lr.getLendTime();
				// long second = (returnTime.getTime() - lendTime.getTime()) / 1000;
				// double price = lr.getPrice();
				// lr.setPay(price * second);
				bike.setStatus(1);
				return 1;// �黹�ɹ�
				
			}
			return 0;
		} else {
			return s;
		}
		
//		1���黹�ɹ�
//		0�����Ǹ��û�
//		s��
//			-1��û�д�ID
//			11���˵���û�б���
	}

	@Override
	public int queryNotReturnRecordId(int bikeId) {
		for (LeaseRecord lr : lrs) {
			// ��Ϊһ�����ӿ��ܲ���������¼������δ�黹�ļ�¼ֻ��һ��������ͨ���������������Է���Ψһ�ļ�¼ID
			if (lr.getBikeId() == bikeId && lr.getReturnTime() == null ) {
				return lr.getId();
			}
		}
		return -1;// û�ҵ�����-1
	}

}
