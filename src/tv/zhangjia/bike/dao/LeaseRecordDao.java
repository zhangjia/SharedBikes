package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.LeaseRecord;

public interface LeaseRecordDao {

	/**
	 * ��Ӽ�¼
	 * @param lr
	 * @return
	 */
	public int doInsert(int userId, int bikeId);

	/**
	 * ��ѯ�����⳵��¼
	 * @return
	 */
	public List<LeaseRecord> queryAll();

	/**
	 * �����û�ID��ѯ���û���ȫ���⳵��¼
	 * @param id
	 * @return
	 */
	public List<LeaseRecord> queryByUserId(int id);

	/**
	 * ���ݼ�¼ID����ѯĳ����¼
	 * @param id
	 * @return
	 */
	public LeaseRecord queryById(int id);

	/**
	 * �����û���ID��ѯ�û�����δ�黹���⳵��¼
	 * @param userid
	 * @return
	 */
	public List<LeaseRecord> queryNotReturnByUserId(int userid);
	
	/**
	 * �黹����
	 * @param bikeId
	 * @param userId
	 * @return
	 */
	public int returnBike(int bikeId, int userId);
	
	/**
	 * ���ݵ�����ID���ظó���δ�黹�ļ�¼ID
	 * @param bikeId
	 * @return
	 */
	public int queryNotReturnRecordId(int bikeId);
	
	
}
