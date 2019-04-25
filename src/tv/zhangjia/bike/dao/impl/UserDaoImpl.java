package tv.zhangjia.bike.dao.impl;

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

public class UserDaoImpl extends CommonDao implements UserDao {
	/**
	 * �û���¼
	 * @param username �û���
	 * @param password ����
	 * @return	ע����û�
	 */
	@Override
	public User login(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		return query4Bean(sql, User.class, username, password);

	}

	/**
	 * �û�ע��
	 * @param username	�û���
	 * @param password	����
	 * @param tel	�ֻ���
	 * @param payPassword ֧������
	 * @return   ע���Ƿ�ɹ����ɹ�����1�����ɹ�����0
	 */
	@Override
	public int register(String username, String password, String tel, String payPassword) {
		WalletDao walletDao = new WalletDaoImpl();
		UserOptionsDao usp = new UserOptionsDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();
		String sqlid = "SELECT seq_users.nextval id FROM dual";
		// ��ȡҪ��ӵ��û���ID
		int userId = query4IntData(sqlid);
		// ��ȡ�����λ��
		Location lo = locationDao.randomUserLocation();
		int locaionId = lo.getId();
		System.out.println(userId + "lo = " + locaionId);
		String sql = "INSERT INTO users VALUES(?,?,?,?,?,?,?,sysdate,?)";

		// �����û�
		int i = executeUpdate(sql, userId, username, password, payPassword, tel, 0, 0, locaionId);
		// �����û��ĸ���Ǯ��,Ĭ�������Ż��붼��0���Ҳ��ǻ�Ա��null��Ϊû�л�Ա����ʱ��
		int j = walletDao.doInsert(new Wallet(userId, 0.0, 0.0, false, null));
		// ���û��ĸ������ã�Ĭ������֧���ǹرյ�
		int x = usp.doInsert(new UserOptions(userId, "����֧��", "0"));

		// �������߶��ɹ���������ע��ɹ�
		// TODO ������������
		return i * j * x;
	}

	/**
	 * �����û�ID��ѯ���û�
	 * @param userId �û�ID
	 * @return   ���û�ID��Ӧ���û�
	 */
	@Override
	public User queryByUserId(int userId) {
		String sql = "SELECT * FROM users WHERE id = ?";
		return query4Bean(sql, User.class, userId);
	}

	/**
	 * ��ѯ���е��û�
	 * @return   ���е��û�
	 * @see tv.zhangjia.bike.dao.UserDao#queryAll()
	 */
	@Override
	public List<User> queryAll() {
		String sql = "SELECT * FROM users";
		return query4BeanList(sql,User.class);
	}

	// TODO ɾ���÷�����doInsert�еĲ�ѯ������User.getUserName����
	@Override
	public String queryUserName(int id) {
		// for (User user : users) {
		// if (user.getId() == id) {
		// return user.getUsername();
		// }
		// }
		return null;
	}

	/**
	 * �û����Ƿ��Ѿ�����
	 * @param username Ҫ�����û���
	 * @return   ���ڷ���true�������ڷ���flase
	 * @see tv.zhangjia.bike.dao.UserDao#isExistUserName(java.lang.String)
	 */
	@Override
	public boolean isUserNameExist(String username) {
		String sql = "SELECT * FROM users WHERE username = ?";
		//����Ҳ������û����򷵻�null
		return query4Bean(sql, User.class, username) != null;
	}

	/**
	 * ����������û���
	 * @param username �û�ע��ʱ������Ѵ��ڵ��û���
	 * @return   ϵͳ������û���
	 * @see tv.zhangjia.bike.dao.UserDao#adviseUsername(java.lang.String)
	 */
	@Override
	public String adviseUsername(String username) {
		int i = 0;
		String newUsername = username + (++i);
		while(isUserNameExist(newUsername)) {
			 newUsername = username + (++i);
		}
		return newUsername;
	}

	/**
	 * 
	 * @param username
	 * @return   
	 * @see tv.zhangjia.bike.dao.UserDao#queryUserId(java.lang.String)
	 */
	
	
	//TODO ɾ���˷��������ֻ������һ�����
	@Override
	public int queryUserId(String username) {
//		List<User> users = queryAll();
//		for (User user : users) {
//			// System.out.println("yon" + username);
//			if (user.getUsername().equalsIgnoreCase(username)) {
//				return user.getId();
//			}
//		}
		return -1;
	}

	/**
	 * �ֻ����Ƿ��Ѵ���
	 * @param tel Ҫ�жϵ��ֻ���
	 * @return   ���ڷ���true�������ڷ���false
	 * @see tv.zhangjia.bike.dao.UserDao#isTelExist(java.lang.String)
	 */
	@Override
	public boolean isTelExist(String tel) {
		String sql = "SELECT * FROM users WHERE tel = ?";
		//����Ҳ������û����򷵻�null
		return query4Bean(sql, User.class, tel) != null;
	}

	//TODO ȥ����ֱ������֤�����ж�
	@Override
	public boolean isTrueTel(String tel, int userId) {
		User user = queryByUserId(userId);
		return user.getTel().equals(tel);
	}

	/**
	 * �û���֧�������Ƿ���ȷ
	 * @param userId
	 * @param payPassword
	 * @return   
	 * @see tv.zhangjia.bike.dao.UserDao#isTruePayPassword(int, java.lang.String)
	 */
	@Override
	public boolean isTruePayPassword(int userId, String payPassword) {
		String sql = "SELECT * FROM users WHERE user_id = ? AND pay_password = ?";
		//����Ҳ������û����򷵻�null
		return query4Bean(sql, User.class, userId,payPassword) != null;
	}

	
	/**
	 * 
	 * @param user
	 * @return   
	 * @see tv.zhangjia.bike.dao.UserDao#doUpdate(tv.zhangjia.bike.entity.User)
	 */
	@Override
	public int doUpdate(User user) {
		//���Ը����û��������û����룬�û�֧�����룬�û��ֻ��ţ��û�λ��
		String sql = "UPDATE users SET username = ?, password = ? ,pay_password,tel = ?,"
				+ "location_id = ? WHERE id = ?";
		return executeUpdate(sql, user.getUsername(),user.getPassword(),user.getPayPassword(), 
				user.getTel(),user.getLocationID(),user.getId());
	}

}
