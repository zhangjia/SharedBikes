package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.CommonDao;

public class WalletDaoImpl extends CommonDao implements WalletDao {

	/**
	 * ���Ǯ��
	 * @param wt Ǯ������
	 * @return ����1����ɹ�������0����ʧ��
	 */
	@Override
	public int doInsert(Wallet wt) {
		String sql = "INSERT INTO wallet VALUES(seq_wallet.nextval,?,?,?,?,?)";
		return executeUpdate(sql, wt.getUserId(), wt.getBalance(), wt.getCoupon(), wt.getIsVIP(), wt.getVipDate());
	}

	/**
	 * ��ѯȫ��Ǯ��
	 * @return ���е�Ǯ����Ϣ
	 */
	@Override
	public List<Wallet> queryAll() {
		String sql = "SELECT wallet.*,username FROM wallet,users WHERE wallet.user_id = users.id ORDER BY wallet.id";
		return query4BeanList(sql, Wallet.class);
	}

	/**
	 * �����û�ID��������Ǯ����Ϣ
	 * @param id �û�ID
	 * @return �û���Ǯ��
	 */
	@Override
	public Wallet queryByUserId(int id) {
		String sql = "SELECT wallet.*,username FROM wallet,users WHERE wallet.user_id = users.id AND user_Id = ?";
		return query4Bean(sql, Wallet.class, id);
	}

	/**
	 * ����Ǯ��
	 * @param wallet Ҫ���µ�Ǯ��
	 * @return ���³ɹ�����1������ʧ�ܷ���0
	 */
	@Override
	public int doUpdate(Wallet wallet) {
		String sql = "UPDATE wallet SET balance=?,coupon=?,is_vip = ?,vip_date=? WHERE id =?";
		return executeUpdate(sql, wallet.getBalance(), wallet.getCoupon(), wallet.getIsVIP() ? 1 : 0,
				wallet.getVipDate() == null ? null : wallet.getVipDate() // ��֤��������ʱ�䲻��Ϳ�ָ���쳣
				, wallet.getId());
	}

	/**
	 * ע�ά��
	 * @param user1Id  ע����û�ID
	 * @param wallet1Id ע����û�Ǯ��ID
	 * @param user2Id �Ƽ��˵��û�ID
	 * @return ��ӳɹ�����1�����ʧ�ܷ���0
	 */
	@Override
	public int awardByregister(int user1Id, int wallet1Id, int user2Id) {
		UserDao userDao = new UserDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		BillDao billDao = new BillDaoImpl();
		// �ж��Ƽ����Ƿ����
		User user2 = userDao.queryByUserId(user2Id);
		if (user2 == null) {
			return -1; // �����ڸ��Ƽ���
		}
		// ��ȡע���û���Ǯ��
		Wallet wallet1 = walletDao.queryByUserId(user1Id);
		// ��ȡ�Ƽ��˵�Ǯ��
		Wallet wallet2 = walletDao.queryByUserId(user2Id);
		// ��ע���û���ӽ���
		wallet1.setCoupon(wallet1.getCoupon() + 100);
		// ���Ƽ�����ӽ���
		wallet2.setCoupon(wallet2.getCoupon() + 100);
		// ����ע���û����Ƽ��˵�Ǯ��
		int x = walletDao.doUpdate(wallet1);
		int y = walletDao.doUpdate(wallet2);

		Bill bill = new Bill("ע�ά��", user1Id, 100.0);
		Bill bill2 = new Bill("�ƹ㽱��", user2Id, 100.0);
		int x2 = billDao.doInsert(bill);
		int y2 = billDao.doInsert(bill2);
		return x * x2 * y * y2;
	}

	/**
	 * ���޽���
	 * @param userId ���޵��û�
	 * @param walletId �����û���Ǯ��
	 * @return �����ɹ�����1������ʧ�ܷ���0
	 */
	@Override
	public int awardByBike(int userId, int walletId) {
		BillDao billDao = new BillDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		// ��ȡ�����û���Ǯ��
		Wallet wallet = walletDao.queryByUserId(userId);
		// ����Ż�ȯ
		wallet.setCoupon(100 + wallet.getCoupon());
		// �����û�Ǯ��
		int x = walletDao.doUpdate(wallet);
		Bill bill = new Bill("���޽���", userId, 100.0);
		int y = billDao.doInsert(bill);

		return x * y;
	}

	/**
	 * ��ֵ
	 * @param userId Ҫ��ֵ���û�ID
	 * @param money Ҫ��ֵ�Ľ��
	 * @return ����1��ֵ�ɹ�������0��ֵʧ��
	 */
	@Override
	public int recharge(int userId, double money) {
		BillDao billDao = new BillDaoImpl();
		// ��ȡ��ǰ�û���Ǯ��
		Wallet w = queryByUserId(userId);
		// ����˻����
		w.setBalance(w.getBalance() + money);
		// ����Ǯ����Ϣ
		int x = doUpdate(w);
		// ����һ����¼����
		Bill bill = new Bill("��ֵ���", userId, money);
		int y = billDao.doInsert(bill);
		return x * y;
	}

	/**
	 * ��ͨVIP
	 * @param userId ��ͨVIP���û�ID
	 * @param month Ҫ��ͨ���·�
	 * @return
	 */
	@Override
	public int becomeVIP(int userId, int month) {
		OptionDao op = new OptionDaoImpl();
		// �������,����-5
		if (pay(userId, +(month * Double.parseDouble(op.queryValue("��Ա�۸�"))), "��ͨ��Ա") != 1) {
			return -5;
		} else {
			// ���û��ͨVIP,�ڵ�ǰʱ��Ļ����ϼ�month������Ѿ���ͨvipʱ�䣬�ڵ���ʱ������ϼ�month
			String sql = "UPDATE wallet SET is_vip = 1,vip_date= ADD_MONTHS(NVL(vip_date,sysdate),?) WHERE user_Id = ?";
			return executeUpdate(sql, month, userId);
		}
	}

	/**
	 * ֧��
	 * @param userId �û�ID
	 * @param money ֧�����
	 * @param type  ֧������
	 * @return ֧���ɹ�����1��֧��ʧ�ܷ���0,���㷵��-5
	 */
	@Override
	public int pay(int userId, double money, String type) {
		BillDao billDao = new BillDaoImpl();
		Wallet pw = queryByUserId(userId);
		// ��ȡ��ǰ�û����Ż�ȯ���
		double coupon = pw.getCoupon();
		// ��ȡ��ǰ�û����˻����
		double balance = pw.getBalance();
		// ��ȡ��ǰ�û����˻��ܶ�
		double sum = coupon + balance; // ��ȡ�˻��ܽ��

		// ����ܽ���������ûǮ
		if (sum - money < 0) {
			return -5;
			// ����������,��ôʹ�ú�������һ��֧��
		} else if (coupon < money) {
			// ������Ҫ������ó�����Ǯ֧��
			double h = money - coupon;
			// ���Ż�ȯ����
			pw.setCoupon(0.0);
			// �����۳�
			pw.setBalance(pw.getBalance() - h);

		} else {
			// ����������ֻ�ۺ����Ǯ
			pw.setCoupon(coupon - money);
		}

		// ���µ�ǰ�û�Ǯ��
		int x = doUpdate(pw);
		// ���ɼ�¼
		if (x == 1) {
			Bill bill = new Bill(type, userId, -money);
			int y = billDao.doInsert(bill);
			return x * y;
		} else {
			return 0;
		}
	}

}
