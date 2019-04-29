package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.LeaseRecord;

public interface LeaseRecordDao {
	/**
	 * ��ӽ賵��¼���賵���ܣ�
	 * @param userId �賵���û�ID
	 * @param bikeId ��ĳ�
	 * @return �賵�ɹ�����1���賵ʧ�ܷ���0
	 */
	int doInsert(int userId, int bikeId);

	/**
	 * ��ѯ���е��⳵��¼�������û�ɾ����)
	 * @return ���е��⳵��¼
	 */
	List<LeaseRecord> queryAll();

	/**
	 * ��ѯ�����û��������⳵��¼
	 * @param id �û�id
	 * @return ���û��������⳵��¼
	 */
	List<LeaseRecord> queryByUserId(int id);

	/**
	 * �����˵�ID��ѯ������¼
	 * @param id Ҫ��ѯ���˵�id
	 * @return �����˵�����Ϣ
	 */
	LeaseRecord queryById(int id);

	/**
	 * ��ѯĳ���û�����δ�黹�ĳ���
	 * @param userId
	 * @return ���û���ǰ����δ�黹�ĳ���
	 */
	List<LeaseRecord> queryNotReturnByUserId(int userid);

	/**
	 * �黹����
	 * @param bikeId Ҫ�黹�ĵ���id
	 * @param userId �û�id
	 * @return �黹�ɹ�����1���黹ʧ�ܷ���0���˻����㷵��-5
	 */
	int returnBike(int bikeId, int userId);

	/**
	 * ���ݳ���id�ͳ���״̬��ȷ��Ψһ�Ķ�����¼
	 * @param bikeId
	 * @return ������¼
	 */
	LeaseRecord queryNotReturnRecordId(int bikeId);

	/**
	 * ɾ����¼����ɾ����
	 * @param id ��¼ID
	 * @return ɾ���ɹ�����1��ɾ��ʧ�ܷ���0
	 */
	int doUpdate(LeaseRecord lr);

	/**
	 * ɾ����¼����ɾ����
	 * @param id ��¼ID
	 * @return ɾ���ɹ�����1��ɾ��ʧ�ܷ���0
	 */
	int doDelele(int id);

}
