package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Bill;

public interface BillDao {

	/**
	 * �����˵���¼
	 * @param userId
	 * @return
	 */
	boolean doInsert(int userId, String event, double money);

	/**
	 * ��ѯ���е��˵���¼
	 * @return
	 */
	List<Bill> queryAll();

	/**
	 * �����˵�ID�����˵���¼
	 * @param billId
	 * @return
	 */
	Bill queryByBillId(int billId);

	
	/**
	 * ���ݸ��û���ID�����ظ��û���ȫ���˵���¼
	 * @param userId
	 * @return
	 */
	List<Bill> queryUserBill(int userId);

	
	
	

}
