package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Wallet;

public interface WalletDao {
	/**
	 * ���Ǯ��
	 * @param wt Ǯ������
	 * @return ����1����ɹ�������0����ʧ��
	 */
	int doInsert(Wallet wt);

	/**
	 * ��ѯȫ��Ǯ��
	 * @return ���е�Ǯ����Ϣ
	 */
	List<Wallet> queryAll();

	/**
	 * ����Ǯ��
	 * @param wallet Ҫ���µ�Ǯ��
	 * @return ���³ɹ�����1������ʧ�ܷ���0
	 */
	int doUpdate(Wallet wallet);

	/**
	 * �����û�ID��������Ǯ����Ϣ
	 * @param id �û�ID
	 * @return �û���Ǯ��
	 */
	Wallet queryByUserId(int id);

	/**
	 * ע�ά��
	 * @param user1Id  ע����û�ID
	 * @param wallet1Id ע����û�Ǯ��ID
	 * @param user2Id �Ƽ��˵��û�ID
	 * @return ��ӳɹ�����1�����ʧ�ܷ���0
	 */
	int awardByregister(int user1Id, int wallet1Id, int user2Id);

	/**
	 * ���޽���
	 * @param userId ���޵��û�
	 * @param walletId �����û���Ǯ��
	 * @return �����ɹ�����1������ʧ�ܷ���0
	 */
	int awardByBike(int userId, int walletId);

	/**
	 * ��ֵ
	 * @param userId Ҫ��ֵ���û�ID
	 * @param money Ҫ��ֵ�Ľ��
	 * @return ����1��ֵ�ɹ�������0��ֵʧ��
	 */
	int recharge(int userId, double money);

	/**
	 * ��ͨVIP
	 * @param userId ��ͨVIP���û�ID
	 * @param month Ҫ��ͨ���·�
	 * @return
	 */
	int becomeVIP(int userId, int month);

	/**
	 * ֧��
	 * @param userId �û�ID
	 * @param money ֧�����
	 * @param type  ֧������
	 * @return ֧���ɹ�����1��֧��ʧ�ܷ���0,���㷵��-5
	 */
	int pay(int userId, double money, String type);

}
