package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.LeaseRecord;

public interface LeaseRecordDao {

	/**
	 * ��Ӽ�¼
	 * @param lr
	 * @return
	 */
	 int doInsert(int userId, int bikeId);

	/**
	 * ��ѯ�����⳵��¼
	 * @return
	 */
	 List<LeaseRecord> queryAll();

	/**
	 * �����û�ID��ѯ���û���ȫ���⳵��¼
	 * @param id
	 * @return
	 */
	 List<LeaseRecord> queryByUserId(int id);

	/**
	 * ���ݼ�¼ID����ѯĳ����¼
	 * @param id
	 * @return
	 */
	 LeaseRecord queryById(int id);

	/**
	 * �����û���ID��ѯ�û�����δ�黹���⳵��¼
	 * @param userid
	 * @return
	 */
	 List<LeaseRecord> queryNotReturnByUserId(int userid);
	
	/**
	 * �黹����
	 * @param bikeId
	 * @param userId
	 * @return
	 */
	 int returnBike(int bikeId, int userId);
	
	/**
	 * ���ݵ�����ID���ظó���δ�黹�ļ�¼ID
	 * @param bikeId
	 * @return
	 */
	 LeaseRecord queryNotReturnRecordId(int bikeId);
	
	 
	 boolean isCurrentUserLease(int userId,int bikeId);
	
	
	
}
