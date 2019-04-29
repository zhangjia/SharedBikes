package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Location;

public interface LocationDao {
	/**
	 * ��ѯ���е�λ����Ϣ
	 * @return ���е�λ����Ϣ
	 */
	List<Location> queryAll();

	/**
	 * ����λ��id��ѯ����λ����Ϣ
	 * @param id λ��id
	 * @return ��λ����Ϣ
	 */
	Location queryLocation(int id);

	/**
	 * �������һ����ͬ�ڵ�ǰλ�õ�λ��
	 * @param locationId ��ǰλ��id
	 * @return ������ɵ�λ��
	 */
	Location randomLocation(int loctionId);

	/**
	 * ����λ��Id��ѯλ������
	 * @param locationId λ��ID
	 * @return λ������
	 */
	String queryLocationName(int locationId);

	/**
	 * ���Ƚ���
	 * @return ���еĽ�����Ϣ
	 */
	List<String> dispatch();

	/**
	 * ����λ��ID��ѯ��ǰλ���µ����г���
	 * @param locationId λ��ID
	 * @return ��ǰλ���µ����г���
	 */
	List<Bike> queryBikesByLocation(int locationId);

	/**
	 * �������һ���û���λ��
	 * @return ���ɵ����λ��
	 */
	Location randomUserLocation();
}
