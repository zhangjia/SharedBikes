package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bike;
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
	
	List<Bike> queryBikesByLocation(int locationId);
	
//	
//	boolean updateLocationBikes(int locationId);
//	
//	
//	 boolean deleteLocationBikes(int locationId,int bikeID);
	
	Location randomUserLocation();
}
