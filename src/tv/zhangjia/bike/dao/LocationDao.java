package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Location;

public interface LocationDao {
	/**
	 * ��ѯ���е�λ����Ϣ
	 * @return
	 */
	List<Location> queryAll();
	
	/**
	 * ��ѯ����λ����Ϣ
	 * @param id
	 * @return
	 */
	Location queryLocation(int id);
	
	/**
	 * ���λ��
	 * @param id
	 * @return
	 */
	int doInsert(int id);
	
	/**
	 * ����λ��
	 * @param id
	 * @return
	 */
	int doUpdate(int id);
	
	
	/**
	 * ɾ��λ��
	 * @param id
	 * @return
	 */
	int doDelete(int id);
	
	/**
	 * �黹����ʱ��ģ���������λ��
	 * @return
	 */
	Location randomLocation(int loctionId, int bikeId,int leaseRecordId);
	
	/**
	 * ����λ��ID����ѯId����
	 * @param locationId
	 * @return
	 */
	String queryLocationName(int locationId);
	
	
	/**
	 * ����
	 * @return
	 */
	List<String> dispatch();
	
	/*
	int changeBikeLocation(int bikeId,int locationId, int oldLocationId);
	
	
	int addBikeLocation(int bikeId, int locationId);*/
	
	boolean updateLocationBikes(int locationId);
	
	
	public boolean deleteLocationBikes(int locationId,int bikeID);
}
