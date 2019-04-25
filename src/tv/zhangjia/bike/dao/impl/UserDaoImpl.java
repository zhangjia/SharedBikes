package tv.zhangjia.bike.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.UserOptionsDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.UserOptions;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.CommonDao;

public class UserDaoImpl extends CommonDao<User> implements UserDao {
//	private List<User> users = Database.USERS;
//	private List<Location> locations = Database.LOCATIONS;
//	private WalletDao walletDao = new WalletDaoImpl();
//	private UserSettingsDao usd = new UserSettingsDaoImpl();

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
//	@Override
//	public User login(String username, String password) {
//		for (User user : users) {
//			// ����û���������ƥ�䣬�򷵻ظ��û�
//			if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
//				return user;
//			}
//		}
//		// ����û��������벻ƥ�䣬�򷵻�null
//		return null;
//	}
	@Override
	public User login(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		return query4Bean(sql, username,password);
		
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
//	@Override
//	public int register(String username, String password, String tel,String payPassword) {
//		WalletDao walletDao = new WalletDaoImpl();
//		UserOptionsDao usd2 = new UserOptionsDaoImpl();
//		LocationDao locationDao = new LocationDaoImpl();
//		for (User user : users) {
//			if (user.getUsername().equalsIgnoreCase(username)) {
//				return -1; // �û����Ѿ�����
//			}
//		}
//		int locationId = locationDao.randomUserLocation().getId();
//		// �ȴ���Usre����
//		User user = new User(Database.nextUserId(), username, password, tel, false, 0, new Date(), locationId,payPassword);
//		users.add(user);
//		// ����Ǯ��������user��Id��ӽ�ȥ
//		Wallet wallet = new Wallet(user.getId(), 0, 0, false, null);
//		walletDao.doInsert(wallet);
//		Location lo = locationDao.randomUserLocation();
//		UserOptions us = new UserOptions(user.getId(),"����֧��","0");
//		usd2.doInsert(us);
//		return 1;
//	}
	@Override
	public int register(String username, String password, String tel,String payPassword) {
		WalletDao walletDao = new WalletDaoImpl();
		UserOptionsDao usp = new UserOptionsDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();
		//���ô˷���ǰ�����ж��û����������ֻ����Ƿ��Ƿ��ô���
		String sqlid = "SELECT seq_users.nextval id FROM dual";
		//��ȡ���ɵ�id
		int userId = queryCurrentId(sqlid);
		//��ȡ�����λ��
		Location lo = locationDao.randomUserLocation();
		int locaionId = lo.getId();
		System.out.println(userId + "lo = " + locaionId);
		String sql = "INSERT INTO users VALUES(?,?,?,?,?,?,?,?,?)";
		
		//�����û�
		int i = executeUpdate(sql, userId,username,password,payPassword,tel,0,0,new Date(System.currentTimeMillis()),locaionId);
		//����Ǯ��
		int j = walletDao.doInsert(new Wallet(userId,0,0,false,null));
		//��������
		int x = usp.doInsert(new UserOptions(userId,"����֧��","0"));
		return i * j * x;
	}

//	@Override
//	public User queryByUserId(int userId) {
//		// ������foeach��û�����ݽ��и�д
//		for (User user : users) {
//			if (user.getId() == userId) {
//				return user;
//			}
//		}
//		return null;
//	}
	@Override
	public User queryByUserId(int userId) {
		String sql = "SELECT * FROM users WHERE id = ?";
		String sql2 = "SELECT username FROm users ";
		return query4Bean(sql, userId);
	}

	@Override
	public List<User> queryAll() {
		String sql = "SELECT * FROM users";
		return query4BeanList(sql);
	}

	//TO ɾ���÷�����doInsert�еĲ�ѯ������User.getUserName����
	@Override
	public String queryUserName(int id) {
//		for (User user : users) {
//			if (user.getId() == id) {
//				return user.getUsername();
//			}
//		}
		return null;
	}



//	@Override
//	public int editPassword(int userId, String editPassword) {
//		User user = queryByUserId(userId);
//		user.setPassword(editPassword);
//		return 1;
//	}
//	
//	@Override
//	public User retrievePassword(int userId, String password) {
//		User user = queryByUserId(userId);
//		user.setPassword(password);
//		return user;
//	}


	

	@Override
	public boolean isExistUserName(String username) {
		List<User> users = queryAll(); 
//		System.out.println(users);
		for (User user : users) {
			if (user.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String adviseUsername(String username) {
		int i = 0;
		String newUsername = username + (++i);
		if (isExistUserName(newUsername)) {
			newUsername = adviseUsername(newUsername);
		}
		return newUsername;
	}

	@Override
	public int queryUserId(String username) {
		List<User> users  = queryAll();
		for (User user : users) {
			// System.out.println("yon" + username);
			if (user.getUsername().equalsIgnoreCase(username)) {
				return user.getId();
			}
		}
		return -1;
	}

	@Override
	public boolean isTelExist(String tel) {
		List<User> users  = queryAll();
		for (User user : users) {
			if (user.getTel().equals(tel)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isTrueTel(String tel,int userId) {
		User user  = queryByUserId(userId);
		return user.getTel().equals(tel) ;
	}


	@Override
	public boolean isTruePayPassword(int userId, String payPassword) {
		User user  = queryByUserId(userId);
		if(user.getPassword().equals(payPassword)) {
			return true;
		} else {
		return false;
		}
	}

	@Override
	public User getBeanFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(1));
		user.setUsername(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setPayPassword(rs.getString(4));
		user.setTel(rs.getString(5));
		user.setAdmin(rs.getInt(6) == 1 ? true : false);
		user.setCyclingTime(rs.getLong(7));
		user.setRegisterTime(rs.getDate(8));
		user.setLocationID(rs.getInt(9));
		return user;
	}

	@Override
	public int doUpdate(User user) {
		String sql = "UPDATE users SET password = ? WHERE id = ?";
		
		return executeUpdate(sql, user.getPassword(),user.getId());
	}

}
