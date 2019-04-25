package tv.zhangjia.bike.dao;

import java.io.IOException;
import java.util.List;

import tv.zhangjia.bike.entity.Bill;

public interface BillDao {

	/**
	 * �����˵���¼
	 * @param userId
	 * @return
	 */
//	int doInsert(int userId, String event, double money);
	int doInsert(Bill bill);

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

	
	int awardByregister(int user1Id,int wallet1Id,int user2Id);
	
	int awardByBike(int userId, int walletId);
	boolean export() throws IOException;
	

}
