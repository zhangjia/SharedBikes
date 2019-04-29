package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bike;

/**
 * Bike�ӿ�
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.dao	
 * @ClassName	BikeDao	
 * @author	ZhangJia
 * @Version	
 * @date	2019��3��26�� ����5:37:20
 */
public interface BikeDao {

	/**
	 * ��ӵ���
	 * @param bike ��������
	 * @return ��ӳɹ�����1�����ʧ�ܷ���0
	 */
	int doInsert(Bike bike);

	/**
	 * ɾ������
	 * @param id Ҫɾ���ĵ���ID
	 * @return ɾ���ɹ�����1��ɾ��ʧ�ܷ���0
	 */
	int doDelete(int id);

	/**
	 * ���µ���
	 * @param id Ҫ���µĵ���ID
	 * @return ���³ɹ�����1������ʧ�ܷ���0
	 */
	int doUpdate(Bike bike);

	/**
	 * ��ѯ���е���
	 * @return ���е���
	 */
	List<Bike> queryAll();

	/**
	 * ���ݵ���Id���ص�����Ϣ
	 * @param id ����ID
	 * @return ������Ϣ
	 */
	Bike queryById(int id);

	/**
	 * ���ݵ���ID��ѯ��۸�
	 * @param bikeId Ҫ��ѯ�ĵ���
	 * @return �õ����ļ۸�
	 */
	double queryBikePrice(int bikeId);

	/**
	 * ��ѯδɾ�������е���
	 * @return ���е���
	 */
	List<Bike> queryAllByNotDelete();
}
