package tv.zhangjia.bike.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;

public class UserDaoImpl implements UserDao {
	private List<User> users = Database.USERS;
	private List<Location> locations = Database.LOCATIONS;
	private WalletDao walletDao = new WalletDaoImpl();

	/**
	 * ��д��¼����
	 * 
	 * @param username
	 *            ��¼���û���
	 * @param password
	 *            ��¼������
	 * @return �û�
	 * @see tv.zhangjia.bike.dao.UserDao#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String username, String password) {
		for (User user : users) {
			// ����û���������ƥ�䣬�򷵻ظ��û�
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		// ����û��������벻ƥ�䣬�򷵻�null
		return null;
	}

	/**
	 * ��дע�᷽��
	 * 
	 * @param username
	 *            ע����û���
	 * @param password
	 *            ע�������
	 * @return
	 * @see tv.zhangjia.bike.dao.UserDao#register(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public int register(String username, String password, String tel,String payPassword) {

		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return -1; // �û����Ѿ�����
			}
		}

		// System.out.println("+++" + queryUserId(username));
		// TODO ����λ��ID��Ǯ��ID�����ɷ�ʽ
		int locationId = randomLocation();
		// �ȴ���Usre����
		User user = new User(Database.nextUserId(), username, password, tel, false, 0, new Date(), locationId,payPassword);
		users.add(user);
		// ����Ǯ��������user��Id��ӽ�ȥ
		Wallet wallet = new Wallet(user.getId(), 0, 0, false, null);
		walletDao.doInsert(wallet);

		// ��User��Ǯ������
		user.setWalletID(wallet.getId());
		// System.out.println("wa" + wallet.getId());
		return 1;
	}

	@Override
	public User queryByUserId(int userId) {
		// ������foeach��û�����ݽ��и�д
		for (User user : users) {
			if (user.getId() == userId) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> queryAll() {

		return users;
	}

	@Override
	public String queryUserName(int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user.getUsername();
			}
		}
		return null;
	}

	@Override
	public int addPayPassword(int userId, String payPassword) {
		User user = queryByUserId(userId);
		user.setPayPassword(payPassword);
		return 1;
	}

	@Override
	public int editPassword(int userId, String editPassword) {
		User user = queryByUserId(userId);
		user.setPassword(editPassword);
		return 1;
	}

	@Override
	public int isTrueTel(int userId, String tel) {
		User user = queryByUserId(userId);
		if (user == null) {
			return -1;
		}

		if (user.getTel().equals(tel)) {
			return 1;
		}
		return 0;
	}

	@Override
	public User retrievePassword(int userId, String password) {
		User user = queryByUserId(userId);
		user.setPassword(password);
		return user;
	}

	@Override
	public int isTrueUserName(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public String adviseUsername(String username) {
		int i = 1;
		String newUsername = username + (++i);
		if (isTrueUserName(newUsername) == 1) {
			newUsername = adviseUsername(newUsername);
		}
		return newUsername;
	}

	@Override
	public int queryUserId(String username) {
		for (User user : users) {
			// System.out.println("yon" + username);
			if (user.getUsername().equals(username)) {
				return user.getId();
			}
		}
		return -1;
	}

	@Override
	public boolean isTelExist(String tel) {
		for (User user : users) {
			if (user.getTel().equals(tel)) {
				return false;
			}
		}
		return true;
	}

	public int randomLocation() {
		Random random = new Random();
		int index = random.nextInt(locations.size());
		return index;
	}

	@Override
	public boolean isTruePayPassword(int userId, String payPassword) {
		for (User user : users) {
			if(user.getPassword().equals(payPassword)) {
				return true;
			}
		}
		return false;
	}

}
