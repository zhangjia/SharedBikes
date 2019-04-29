package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Repair;

public interface RepairDao {

	/**
	 * ��ӱ��޼�¼
	 * @param repair ���޶���
	 * @return ��ӳɹ�����1�����ʧ�ܷ���0
	 */
	int doInsert(Repair repair);

	/**
	 * �����¼
	 * @param repair
	 * @return ����ɹ�����1������ʧ�ܷ���0
	 */

	int doUpdate(Repair repair);

	/**
	 * ��ѯ���еı��޼�¼
	 * @return ���еı��޼�¼
	 */
	List<Repair> queryAll();

	/**
	 * ���ݵ���iD��ѯ������¼
	 * @return  ������¼
	 */
	Repair queryByBikeId(int bikeId);

	/**
	 * �жϵ����Ƿ��Ѿ�����
	 * @param bikeId ����ID
	 * @return �Ѿ����޷���true�����򷵻�false
	 */
	boolean isRepair(int bikeId);
}
