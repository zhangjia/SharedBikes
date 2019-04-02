package tv.zhangjia.bike.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;

public class BillDaoImpl implements BillDao {
	private List<Bill> bills = Database.BILLS;
	private List<Wallet> wallets = Database.WALLETS;
	private List<User> users = Database.USERS;

	@Override
	public boolean doInsert(int userId, String event, double money) {
		Bill bill = new Bill(Database.nextBillId(), event, userId, new Date(), money);
		return bills.add(bill);
	}

	@Override
	public List<Bill> queryAll() {
		return bills;
	}

	@Override
	public Bill queryByBillId(int billId) {
		for (Bill bill : bills) {
			if (bill.getId() == billId) {
				return bill;
			}
		}
		return null;
	}

	@Override
	public List<Bill> queryUserBill(int userId) {
		List<Bill> newBill = new ArrayList<>();
		for (Bill bill : bills) {
			if (bill.getUserId() == userId) {
				newBill.add(bill);
			}
		}
		return newBill;
	}

	@Override
	public int awardByregister(int user1Id, int wallet1Id, int user2Id) {
		UserDao userDao = new UserDaoImpl();
		WalletDao walletDao = new WalletDaoImpl();
		User user2 = userDao.queryByUserId(user2Id);
		if (user2 == null) {
			// G: if(queryUser(user2Id) == null) {
			return -1; // �����ڸ��û�
		}
		// G: Wallet wallet1 = queryWallet(wallet1Id);
		// G: Wallet wallet2 = queryWallet(user2.getWalletID());
		Wallet wallet1 = walletDao.queryByUserId(user1Id);
		Wallet wallet2 = walletDao.queryByUserId(user2Id);

		wallet1.setCoupon(wallet1.getCoupon() + 100);
		wallet2.setCoupon(wallet2.getCoupon() + 100);

		doInsert(user1Id, "ע�ά��", 100);
		doInsert(user2Id, "�ƹ㽱��", 100);

		return 0;
	}

	@Override
	public int awardByBike(int userId, int walletId) {
		UserDao userDao = new UserDaoImpl();
		if (userDao.queryByUserId(userId) == null) {
			// G: if(queryUser(userId) == null) {
			return -1; // �����ڸ��û�
		}
		WalletDao walletDao = new WalletDaoImpl();
		Wallet wallet = walletDao.queryByUserId(userId);
		// G: Wallet wallet = queryWallet(walletId);

		wallet.setCoupon(100);
		doInsert(userId, "���޽���", 100);

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

		File file = new File(
				"E:" + File.separator + "bike" + File.separator + "Bill" + File.separator + "bill.txt");
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
}
