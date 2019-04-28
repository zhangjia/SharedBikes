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
	 int doInsert(Bike bike);
	 
	 /**
	  * ���ݵ���IDɾ������
	  * @param bikeId
	  * @return
	  */
	 int doDelete(int id);
	 
	 /**
	  * �޸ĵ���
	  * @param bike
	  * @return
	  */
	 int doUpdate(Bike bike);
	 /**
	  * ��ѯ���е���
	  * @return
	  */
	 List<Bike> queryAll();
	 /**
	  * ���ݵ���ID��ѯ����
	  * @param bikeid
	  * @return
	  */
	 Bike queryById(int id); 

	 /**
	  * ���ݵ���id��ѯ������״̬
	  * @param bikeId
	  * @return
	  */
	 int bikeStatus(int bikeId);
	 
	 /**
	  * ���ݵ���ID�޸ĵ�����״̬Ϊ��
	  * @param user
	  * @param bikeId
	  * @return
	  */
	 int setDamage(User user, int bikeId);
	 
	 /**
	  * ��ѯ�Ѿ��𻵵ĵ���
	  * @return
	  */
	 List<Bike> queryByDamage();
	 
	 /**
	  * ���µ����۸�
	  */
	 void updatePrice();
	 
	 double queryBikePrice(int bikeId);
	 
	 List<Bike> queryAllByNotDelete();
}
