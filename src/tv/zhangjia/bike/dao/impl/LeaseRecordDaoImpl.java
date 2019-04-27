package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LeaseRecordDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.LeaseRecord;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.CommonDao;

public class LeaseRecordDaoImpl extends CommonDao implements LeaseRecordDao {

	/**
	 * ��ӽ賵��¼���賵���ܣ�
	 * @param userId �賵���û�ID
	 * @param bikeId ��ĳ�
	 * @return �賵�ɹ�����1���賵ʧ�ܷ���0
	 */
	// @Override
	// public int doInsert(int userId, int bikeId) {
	// UserDao userDao = new UserDaoImpl();
	// BikeDao bikeDao = new BikeDaoImpl();
	// LocationDao locationDao = new LocationDaoImpl();
	// int s = bikeDao.bikeStatus(bikeId);
	// // �����11,˵��״̬�ɽ�
	// if (s == 11) {
	// Bike bike = bikeDao.queryById(bikeId);
	//
	// bike.setStatus(0); // ����Ϊ���״̬
	// bike.setAmount(bike.getAmount() + 1); // �������+1
	// bike.setLastLocationId(bike.getLocationId());
	// bike.setLocationId(-1); // -1Ϊ����״̬
	// // System.out.println(bike.getLocationId());
	//
	// // ���µ�ǰλ�õ���Ϣ
	//// locationDao.updateLocationBikes(bike.getLastLocationId());
	//
	// // ��ȡ��ǰλ�õ�����
	// Location lo = locationDao.queryLocation(bike.getLastLocationId());
	//
	// // ����·����
	// String loName = lo.getLocation() + " ---> " + "������\t";
	// // ���ɽ賵��¼
	// LeaseRecord lr = new LeaseRecord(Database.nextLeaseRecordId(), bikeId,
	// userId,
	// userDao.queryUserName(userId), new Date(), null, loName, 0, 0);
	// lrs.add(lr);
	//
	// return 1; // ����ɹ�
	// }
	//
	// return s;
	// // s = 10 �������Ѿ�����
	// // s = 5������ID������
	// // s = -1��������
	// }
	@Override
	public int doInsert(int userId, int bikeId) {
		// UserDao userDao = new UserDaoImpl();
		BikeDao bikeDao = new BikeDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();

		Bike bike = bikeDao.queryById(bikeId);

		bike.setStatus(0); // ����Ϊ���״̬
		bike.setAmount(bike.getAmount() + 1); // �������+1
		bike.setLastLocationId(bike.getLocationId()); // �������
		bike.setLocationId(-1); // -1Ϊ����״̬

		// ����·����
		Location lo = locationDao.queryLocation(bike.getLastLocationId());
		String journey = lo.getLocationName() + " ---> " + "������\t";
		// ���ɽ賵��¼
		// LeaseRecord lr = new LeaseRecord(Database.nextLeaseRecordId(), bikeId,
		// userId, userDao.queryUserName(userId),
		// new Date(), null, loName, 0, 0);
		String sql = "INSERT INTO lease_record VALUES(seq_lease_record.nextval,?,?,sysdate,null,?,0.0,0L)";
		return executeUpdate(sql, bikeId, userId, journey);

	}

	/**
	 * ��ѯ���е��⳵��¼
	 * @return
	 */
	@Override
	public List<LeaseRecord> queryAll() {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id";
		return query4BeanList(sql, LeaseRecord.class);
	}

	/**
	 * ��ѯ�����û��������⳵��¼
	 * @param id �û�id
	 * @return ���û��������⳵��¼
	 */
	@Override
	public List<LeaseRecord> queryByUserId(int id) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id AND users.id = ?";
		return query4BeanList(sql, LeaseRecord.class, id);
	}

	/**
	 * �����˵�ID��ѯ������¼
	 * @param id Ҫ��ѯ���˵�id
	 * @return �����˵�����Ϣ
	 */
	@Override
	public LeaseRecord queryById(int id) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id AND lease_record.id = ?";
		return query4Bean(sql, LeaseRecord.class, id);
	}

	/**
	 * ��ѯĳ���û�����δ�黹�ĳ���
	 * @param userId
	 * @return
	 */
	@Override
	public List<LeaseRecord> queryNotReturnByUserId(int userId) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id AND users.id = ? AND lease_record.return_time IS NULL";
		return query4BeanList(sql, LeaseRecord.class, userId);
	}

	/**
	 * �黹����
	 * @param bikeId Ҫ�黹�ĵ���id
	 * @param userId �û�id
	 * @return �黹�ɹ�����1���黹ʧ�ܷ���0���˻����㷵��-5
	 */
	// @Override
	// public int returnBike(int bikeId, int userId) {
	// UserDao userDao = new UserDaoImpl();
	// BikeDao bikeDao = new BikeDaoImpl();
	// WalletDao walletDao = new WalletDaoImpl();
	// LocationDao locationDao = new LocationDaoImpl();
	// int s = bikeDao.bikeStatus(bikeId);
	// Wallet w = walletDao.queryByUserId(userId);
	// if (s == 10) {
	// // ��ȡҪ�黹�ĵ���
	// Bike bike = bikeDao.queryById(bikeId);
	// // ����BikeID�ҵ������������м�¼��ID
	//
	// // �������м�¼��ID����������¼��Ϣ
	// LeaseRecord lr = queryNotReturnRecordId(bikeId);
	// if (lr.getUserId() == userId) {
	// Date returnTime = new Date();
	// // ��������ʱ�䣨�룩
	// Date lendTime = lr.getLeaseTime();
	// long second = (returnTime.getTime() - lendTime.getTime()) / 1000;
	// // ���ݵ������Ͳ����ۼ�
	// // double price = bike.getType().equals("������") ? as.getbBikePrice() :
	// // as.getaBikePrice();
	// double price = bike.getType().equals("������") ?
	// Double.parseDouble(as.queryValue("������"))
	// : Double.parseDouble(as.queryValue("�ŵų�"));
	//
	// // double discount = w.isVIP() ? as.getDiscount() : 1;
	// double discount = w.isVIP() ? Double.parseDouble(as.queryValue("�ۿ�")) : 1;
	// // �������ѽ��
	// double cost = second * price * discount;
	// // ֧��
	// int i = walletDao.pay(userId, cost, "�⳵");
	//
	// // ���ûǮ������-5
	// if (i != 1) {
	// return -5;
	// }
	//
	// // ���ļ�¼���ѽ��
	// lr.setCost(cost);
	// // ���Ĺ黹ʱ��
	// lr.setReturnTime(returnTime);
	// // �������ʱ��
	// lr.setTime(second);
	// // G: User user = queryUserByUserId(userId);
	// User user = userDao.queryByUserId(userId);
	// user.setCyclingTime(user.getCyclingTime() + second);
	//
	// // ���ĳ���״̬
	// bike.setStatus(1);
	// // ��ȡ������ǰλ�õ�ID
	// int startLocationId = bike.getLastLocationId();
	// // ��ȡ������ǰλ�õ�����
	// String start = locationDao.queryLocationName(startLocationId);
	//
	// // ��ȡ�黹��ʱ���µ�λ��
	// Location nowLocation = locationDao.randomLocation(bike.getLastLocationId(),
	// bikeId, lr.getId());
	// // ��ȡ�µ�λ������
	// String end = nowLocation.getLocation();
	// // 1. �޸ĵ�����λ��
	//
	// bike.setLocationId(nowLocation.getId());
	//
	// // 2. �޸����м�¼�е��г�
	//
	// lr.setLocations(start + " ---> " + end);
	// // ����λ��
	// // locationDao.updateLocationBikes(bike.getLocationId());
	// // locationDao.updateLocationBikes(bike.getLastLocationId());
	//
	// return 1;// �黹�ɹ�
	//
	// }
	// return 0; // ���Ǹ��û�
	// } else {
	// return s;
	// }
	//
	// // 0�����Ǹ��û�
	// // -5 : ���ûǮ��
	// // s = 11��û�б����
	// // s = 5 ��û�и�ID
	// // s = -1���ó��Ѿ���
	//
	// }
	@Override
	public int returnBike(int bikeId, int userId) {
		UserDao userDao = new UserDaoImpl();
		BikeDao bikeDao = new BikeDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();
		OptionDao optionDao = new OptionDaoImpl();
		Wallet wt = walletDao.queryByUserId(userId);
		/**
		 * 1.��¼
		 * 		�޸Ĺ黹ʱ��
		 * 		�޸���ʼλ��
		 * 		�޸����ѽ��
		 * 		�޸�����ʱ��
		 * 2.Ǯ��
		 * 		�۷�
		 * 		3. �˵�
		 * 		   �����˵���¼
		 * 4. �û���Ϣ
		 * 		�޸��û�������ʱ��
		 * 5. ����
		 * 		�޸ĵ������յ�λ��
		 */

		// ���ĳ���״̬
		Bike bike = bikeDao.queryById(bikeId);
		bike.setStatus(1);
		//������ɹ黹λ��
		bike.setLastLocationId(locationDao.randomLocation(bikeId).getId()); 
		int w = bikeDao.doUpdate(bike);
		
		
		// Ӧ֧���۸�= �ۼ� * �ۿ� ����Ա�������ۿۣ�
		double price = bikeDao.queryBikePrice(bikeId) * (wt.getIsVIP() ? Double.valueOf(optionDao.queryValue("�ۿ�")) : 1);
		// ������˹黹ʱ�䣬��ʼλ�ã����ѽ�����ʱ��

		String sql = "SELECT id,bike_id,user_id,lease_time,sysdate return_time,(SELECT location_name FROM location,bike WHERE bike.location_id = location.id AND bike.id = ?)||' ---> '||(SELECT location_name FROM location,bike WHERE bike.lastlocation_id = location.id AND bike.id = ?) journey,((sysdate - lease_time) * 24 * 60 * 60) * ? cost,((sysdate - lease_time) * 24 * 60 * 60) time FROM lease_record  WHERE bike_id = ? AND return_time IS NULL;";
		LeaseRecord lr = query4Bean(sql, LeaseRecord.class, bikeId, bikeId, price, bikeId);

		// ����˻����㣬ֱ�ӷ���-5
		int x = walletDao.pay(userId, lr.getCost(), "�⳵");
		if (x != 1) {
			return -5;
		}
		// �޸��û�������ʱ��
		User user = userDao.queryByUserId(userId);
		user.setCyclingTime(user.getCyclingTime() + lr.getTime());
		int y = userDao.doUpdate(user);

		int z = doUpdate(lr);
		return w * x * y * z;

		// 0�����Ǹ��û�
		// -5 : ���ûǮ��
		// s = 11��û�б����
		// s = 5 ��û�и�ID
		// s = -1���ó��Ѿ���

	}

	/**
	 * ���ݳ���id�ͳ���״̬��ȷ��Ψһ�Ķ�����¼
	 * @param bikeId
	 * @return
	 */
	@Override
	public LeaseRecord queryNotReturnRecordId(int bikeId) {
		String sql = "SELECT lease_record.*,users.username FROM users,lease_record WHERE users.id = lease_record.id AND users.id = 1 AND lease_record.return_time IS NULL AND lease_record.bike_id = ?";
		return query4Bean(sql, LeaseRecord.class, bikeId);
	}

	@Override
	public boolean isCurrentUserLease(int userId, int bikeId) {
		// �������м�¼��ID����������¼��Ϣ
		LeaseRecord lr = queryNotReturnRecordId(bikeId);

		// ����ó��Ǹ��û���ģ����Ҳ���δ��״̬
		if (lr != null && lr.getUserId() == userId) {
			return true;
		}
		return false;
	}

	@Override
	public int doUpdate(LeaseRecord lr) {
		String sql = "UPDATE lease_record SET(return_time=?,journey=?,cost=?,time=?) WHERE id = ?";
		return executeUpdate(sql,lr.getBikeId(),lr.getUserId(),lr.getJourney(),lr.getCost(),lr.getTime(),lr.getId());
	}



}
