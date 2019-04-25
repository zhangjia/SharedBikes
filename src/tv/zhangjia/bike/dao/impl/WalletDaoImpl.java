package tv.zhangjia.bike.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
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
	 * @see tv.zhangjia.bike.dao.WalletDao#queryAll()
	 */
	@Override
	public List<Wallet> queryAll() {
		String sql = "SELECT * FROM wallet";
		return query4BeanList(sql, Wallet.class);
	}

	/**
	 * �����û�ID��������Ǯ����Ϣ
	 * @param id �û�ID
	 * @return �û���Ǯ��
	 */
	@Override
	public Wallet queryByUserId(int id) {
		String sql = "SELECT * FROM wallet WHERE user_Id = ?";
		return query4Bean(sql, Wallet.class, id);
	}

	/**
	 * ����Ǯ��
	 * @param wallet Ҫ���µ�Ǯ��
	 * @return ���³ɹ�����1������ʧ�ܷ���0
	 */
	@Override
	public int doUpdate(Wallet wallet) {
		String sql = "UPDATE wallet SET balance=?,coupon=?,is_vip = ?,vipDate=? WHERE id =?";
		// return executeUpdate(sql, wallet.getBalance(), wallet.getCoupon(),
		// wallet.getIsVIP() ? 1 : 0,
		// wallet.getVipDate() == null ? null : new Date(wallet.getVipDate().getTime())
		// // ��֤��������ʱ�䲻��Ϳ�ָ���쳣
		// , wallet.getId());
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
	 * @see tv.zhangjia.bike.dao.WalletDao#awardByregister(int, int, int)
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

	//���޽���
	@Override
	public int awardByBike(int userId, int walletId) {
		UserDao userDao = new UserDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();

		if (userDao.queryByUserId(userId) == null) {
			// G: if(queryUser(userId) == null) {
			return -1; // �����ڸ��û�
		}
		Wallet wallet = walletDao.queryByUserId(userId);
		// G: Wallet wallet = queryWallet(walletId);

		wallet.setCoupon(100 + wallet.getCoupon());
		walletDao.doUpdate(wallet);
		doInsert(new Bill(userId, "���޽���", 100, new Date(System.currentTimeMillis())));

		return 0;
	}

	// private User queryUser(int userId) {
	// for (User user : users) {
	// if(user.getId() == userId) {
	// return user;
	// }
	// }
	// return null;
	// }
	//
	// private Wallet queryWallet(int walletId) {
	// for (Wallet wallet : wallets) {
	// if(wallet.getId() == walletId) {
	// return wallet;
	// }
	// }
	// return null;
	// }

	@Override
	public boolean export() throws IOException {
		List<Bill> record = Database.BILLS;

		File file = new File("E:" + File.separator + "bike" + File.separator + "Bill" + File.separator + "bill.txt");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		Writer writer = new FileWriter(file, true);
		String str = "���\t" + "�û���\t" + "�˵�����\t\t" + "���仯\t" + "����ʱ��\t" + "\r\n";
		String str1 = "";

		for (Bill bill : record) {
			str1 += bill.toString();
		}
		// 3.2��ʼд��
		writer.write(str);
		writer.write(str1);

		// 4�ͷ���Դ
		writer.close();

		return true;

		// 1.ʹ��FIleȷ��Ҫ�������ļ�

	}

	/**
	 * ��ֵ
	 * 
	 * @param userId
	 *            Ҫ��ֵ���û�ID
	 * @param money
	 *            Ҫ��ֵ�Ľ��
	 * @return ����1��ֵ�ɹ�������0��ֵʧ��
	 * @see tv.zhangjia.bike.dao.WalletDao#recharge(int, double)
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
	 * 
	 * @param userId
	 *            ��ͨVIP���û�ID
	 * @param month
	 *            Ҫ��ͨ���·�
	 * @return
	 * @see tv.zhangjia.bike.dao.WalletDao#becomeVIP(int, int)
	 */
	@Override
	public int becomeVIP(int userId, int month) {
		Wallet wallet = queryByUserId(userId);
		OptionDao op = new OptionDaoImpl();
		Calendar c = Calendar.getInstance();
		c.setTime(beforDate);
		c.add(Calendar.MONTH, month);

		Date d = new Date(c.getTimeInMillis());
		if (pay(userId, month * Double.parseDouble(op.queryValue("��Ա�۸�")), "��ͨ��Ա") != 1) {
			return -5;
		} else {

			String sql = "UPDATE wallet SET is_vip = 1,vipDate=? WHERE user_Id = ?";

			return executeUpdate(sql, d, userId);
		}
	}

	@Override
	public int pay(int userId, double money, String type) {
		BillDao billDao = new BillDaoImpl();
		Wallet pw = queryByUserId(userId);
		double coupon = pw.getCoupon();
		double balance = pw.getBalance();
		double sum = pw.getBalance() + pw.getCoupon(); // ��ȡ�˻��ܽ��
		// ����ܽ���������ûǮ
		if (sum - money < 0) {
			return -5;
			// ����������,��ôʹ�ú�������һ��֧��
		} else if (coupon < money) {
			double h = money - coupon;
			pw.setCoupon(0);
			pw.setBalance(pw.getBalance() - h);
			// billDao.doInsert(queryUserId(pw.getId()), type, -money);
			billDao.doInsert(new Bill(userId, type, -money, new Date(System.currentTimeMillis())));
			return doUpdate(pw);
		} else {
			// ����������ֻ�ۺ����Ǯ
			pw.setCoupon(coupon - money);
			// billDao.doInsert(queryUserId(pw.getId()), type, -money);
			billDao.doInsert(new Bill(userId, type, -money, new Date(System.currentTimeMillis())));
			return doUpdate(pw);
		}
	}

}
