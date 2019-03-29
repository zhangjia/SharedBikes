package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Wallet;

public interface WalletDao {
	/**
	 * �½�Ǯ��
	 * @param wt
	 * @return
	 */
	boolean doInsert(Wallet wt);
	/**
	 * ��ѯȫ��Ǯ��״̬
	 * @return
	 */
	List<Wallet> queryAll();
	
	/**
	 * �����û�Id��ѯ���û���Ǯ��״̬
	 * @param id
	 * @return
	 */
	Wallet queryByUserId(int id);
	
	/**
	 * ����Ǯ��ID��ѯ�û�ID
	 * @param walletId
	 * @return
	 */
	int queryUserId(int walletId);
	
	/**
	 * ��ֵ����
	 * @param walletId
	 * @param money
	 * @return
	 */
	int recharge(int walletId, double money);
	
	/**
	 * ��ͨVIP����
	 * @param userId
	 * @return
	 */
	int becomeVIP(int userId, int month);
	
	/**
	 * �۷�
	 * @param money
	 * @return
	 */
	int pay(int userId,double money,String type);
	
	
}
