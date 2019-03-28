package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.User;
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
	 * @param bike
	 * @return 
	 */
	 boolean doInsert(Bike bike);
	 
	 /**
	  * ���ݵ���IDɾ��ID
	  * @param bikeId
	  * @return
	  */
	 boolean doDelete(int id);
	 
	 /**
	  * �޸ĵ���
	  * @param bike
	  * @return
	  */
	 boolean doUpdate(Bike bike);
	 /**
	  * ��ѯ���е���
	  * @return
	  */
	 List<Bike> queryAll();
	 /**
	  * ����ID��ѯ����
	  * @param bikeid
	  * @return
	  */
	 Bike queryById(int id); 
	 
	 /**
	  * ����ID�赥��
	  * @param id
	  * @return
	  */
	 int doLease(int id,User user);
	 
	 /**
	  * ����ID������
	  * @param id
	  * @return
	  */
	 int doReturn(int id);
	 
	 
}
