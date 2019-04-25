package tv.zhangjia.bike.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.CommonDao;

public class WalletDaoImpl extends CommonDao implements WalletDao {
//	private List<Wallet> wallets = Database.WALLETS;
//	private List<Bill> bills = Database.BILLS;
//	private BillDao billDao = new BillDaoImpl();
//	private AdminSettings as = Database.as;
//	private OptionDao as = new OptionDaoImpl();
//
//	@Override
//	public boolean doInsert(Wallet wt) {
//		wt.setId(Database.nextWalletId());
//		return wallets.add(wt);
//	}
	@Override
	public int doInsert(Wallet wt) {
		String sql = "INSERT INTO wallet VALUES(seq_wallet.nextval,?,?,?,?,?)";
		return executeUpdate(sql, wt.getUserId(),wt.getBalance(),wt.getCoupon(),wt.isVIP(),wt.getVipDate());
	}

	@Override
	public List<Wallet> queryAll() {
		String sql = "SELECT * FROM wallet";
		return query4BeanList(sql);
	}

//	@Override
//	public Wallet queryByUserId(int id) {
//		for (Wallet wallet : wallets) {
//			if (wallet.getUserId() == id) {
//				return wallet;
//			}
//		}
//
//		return null;
//	}
	@Override
	public Wallet queryByUserId(int id) {
			String sql = "SELECT * FROM wallet WHERE user_Id = ?";
			 return query4Bean(sql,id);
	}

//	@Override
//	public int recharge(int walletId, double money) {
//		BillDao billDao = new BillDaoImpl();
//		for (int i = 0; i < wallets.size(); i++) {
//			Wallet w = wallets.get(i);
//			// System.out.println("waID" + w.getId());
//			// System.out.println("wa2" + walletId);
//			if (w.getId() == walletId) {
//				// �����˻����
//				w.setBalance(w.getBalance() + money);
//				billDao.doInsert(queryUserId(walletId), "��ֵ���", money);
//				return 1;
//			}
//
//		}
//
//		return 0;
//	}
	@Override
	public int recharge(int userId, double money) {
			BillDao billDao = new BillDaoImpl();
			Wallet w = queryByUserId(userId);
			w.setBalance(w.getBalance()+money);
//			String sql = "UPDATE wallet SET balance = ? WHERE userId = ?";
//			billDao.doInsert(userId, "��ֵ���", money);
			doUpdate(w);
			return  billDao.doInsert(new Bill(userId, "��ֵ���", money,new Date(System.currentTimeMillis())));

	}

//	@Override
//	public int becomeVIP(int userId, int month) {
//		Wallet w = queryByUserId(userId);
//
//		Date date = w.getVipDate() == null ? new Date() : w.getVipDate();
//
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		c.add(Calendar.MONTH, month);
//
//		if(pay(userId, month * Double.parseDouble(as.queryValue("��Ա�۸�")),"��ͨ��Ա" )!= 1) {
//			return -5;
//		} else {
//			w.setVIP(true);
//			w.setVipDate(c.getTime());
//			
//		}
//		
//		return 0;
//	}
	@Override
	public int becomeVIP(int userId, int month) {
		Wallet wallet = queryByUserId(userId);
		Date currentDate = new Date(System.currentTimeMillis());
		Date beforDate = wallet.getVipDate() == null ? currentDate : new Date(wallet.getVipDate().getTime());
		OptionDao op = new OptionDaoImpl();
		Calendar c = Calendar.getInstance();
		c.setTime(beforDate);
		c.add(Calendar.MONTH, month);
		
		Date d = new Date(c.getTimeInMillis());
		if(pay(userId, month * Double.parseDouble(op.queryValue("��Ա�۸�")),"��ͨ��Ա" )!= 1) {
			return -5;
		} else {
			
			String sql = "UPDATE wallet SET is_vip = 1,vipDate=? WHERE user_Id = ?";
			
			return executeUpdate(sql, d,userId);
		}
	}

	@Override
	public int pay(int userId, double money, String type) {
		BillDao billDao = new BillDaoImpl();
		Wallet pw = queryByUserId(userId);
		double coupon = pw.getCoupon();
		double balance = pw.getBalance();
		double sum = pw.getBalance() + pw.getCoupon(); //��ȡ�˻��ܽ��
		//����ܽ���������ûǮ
		if (sum - money < 0) {
			return -5;
			//����������,��ôʹ�ú�������һ��֧��
		} else if(coupon < money){
			double h = money - coupon;
			pw.setCoupon(0);
			pw.setBalance(pw.getBalance() - h);
//			billDao.doInsert(queryUserId(pw.getId()), type, -money);
			billDao.doInsert(new Bill(userId,type,-money,new Date(System.currentTimeMillis())));
			return doUpdate(pw);
		} else {
			//����������ֻ�ۺ����Ǯ
			pw.setCoupon(coupon - money);
//			billDao.doInsert(queryUserId(pw.getId()), type, -money);
			billDao.doInsert(new Bill(userId,type,-money,new Date(System.currentTimeMillis())));
			return doUpdate(pw);
		}
	}

	@Override
	public int queryUserId(int walletId) {
//		for (Wallet ws : wallets) {
//			if (ws.getId() == walletId) {
//				return ws.getUserId();
//			}
//		}
		return -1;
	}

	@Override
	public Wallet getBeanFromResultSet(ResultSet rs) throws SQLException {
		Wallet w = new Wallet(rs.getInt(1),
				rs.getInt(2),
				rs.getDouble(3),
				rs.getDouble(4),
				rs.getInt(5) == 1 ? true : false,
				rs.getDate(6));
		
		return w;
	}

	@Override
	public int doUpdate(Wallet wallet) {
		String sql = "UPDATE wallet SET balance=?,coupon=?,is_vip = ?,vipDate=? WHERE id =?";
		return executeUpdate(sql, wallet.getBalance(),wallet.getCoupon(),wallet.isVIP() ? 1 : 0,
				wallet.getVipDate() == null ? null:new Date(wallet.getVipDate().getTime()) //��֤��������ʱ�䲻��Ϳ�ָ���쳣
				,wallet.getId());
	}
	
	

}
