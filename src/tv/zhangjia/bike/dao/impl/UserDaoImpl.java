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
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return	注册的用户
	 */
	@Override
	public User login(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		return query4Bean(sql, User.class, username, password);

	}

	/**
	 * 用户注册
	 * @param username	用户名
	 * @param password	密码
	 * @param tel	手机号
	 * @param payPassword 支付密码
	 * @return   注册是否成功，成功返回注册的ID，不成功返回0
	 */
	@Override
	public int register(String username, String password, String tel, String payPassword) {
		WalletDao walletDao = new WalletDaoImpl();
		UserOptionsDao usp = new UserOptionsDaoImpl();
		LocationDao locationDao = new LocationDaoImpl();
		String sqlid = "SELECT seq_users.nextval id FROM dual";
		// 获取要添加的用户的ID
		int userId = query4IntData(sqlid);
		// 获取随机的位置
		Location lo = locationDao.randomUserLocation();
		int locaionId = lo.getId();
		String sql = "INSERT INTO users VALUES(?,?,?,?,?,?,?,sysdate,?)";

		// 生成用户
		int i = executeUpdate(sql, userId, username, password, payPassword, tel, 0, 0, locaionId);
		// 生成用户的个人钱包,默认余额和优惠码都是0，且不是会员，null意为没有会员到期时间
		int j = walletDao.doInsert(new Wallet(userId, 0.0, 0.0, false, null));
		// 生用户的个人设置，默认免密支付是关闭的
		int x = usp.doInsert(new UserOptions(userId, "免密支付", "0"));

		// 必须三者都成功，才能算注册成功
		// TODO ：添加事物控制

		if (i * j * x == 1) {
			return userId;
		} else {
			return 0;
		}
	}

	/**
	 * 根据用户ID查询该用户
	 * @param userId 用户ID
	 * @return   该用户ID对应的用户
	 */
	@Override
	public User queryByUserId(int userId) {
		String sql = "SELECT * FROM users WHERE id = ?";
		return query4Bean(sql, User.class, userId);
	}

	/**
	 * 查询所有的用户
	 * @return   所有的用户
	 */
	@Override
	public List<User> queryAll() {
		String sql = "SELECT * FROM users ORDER BY users.id";
		return query4BeanList(sql, User.class);
	}

	/**
	 * 用户名是否已经存在
	 * @param username 要检测的用户名
	 * @return   存在返回true，不存在返回flase
	 */
	@Override
	public boolean isUserNameExist(String username) {
		String sql = "SELECT * FROM users WHERE username = ?";
		// 如果找不到该用户，query4Bean = null，则返回false
		return query4Bean(sql, User.class, username) != null;
	}

	/**
	 * 给出建议的用户名
	 * @param username 用户注册时输入的已存在的用户名
	 * @return   系统建议的用户名
	 */
	@Override
	public String adviseUsername(String username) {
		int i = 0;
		String newUsername = username + (++i);
		while (isUserNameExist(newUsername)) {
			newUsername = username + (++i);
		}
		return newUsername;
	}

	/**
	 * 手机号是否已存在
	 * @param tel 要判断的手机号
	 * @return   存在返回true，不存在返回false
	 */
	@Override
	public boolean isTelExist(String tel) {
		String sql = "SELECT * FROM users WHERE tel = ?";
		// 如果找不到该用户，则返回null
		return query4Bean(sql, User.class, tel) != null;
	}

	/**
	 * 用户的支付密码是否正确
	 * @param userId
	 * @param payPassword
	 * @return   
	 */
	@Override
	public boolean isTruePayPassword(int userId, String payPassword) {
		String sql = "SELECT * FROM users WHERE user_id = ? AND pay_password = ?";
		// 如果找不到该用户，则返回null
		return query4Bean(sql, User.class, userId, payPassword) != null;
	}

	/**
	 * 更新用户信息
	 * @param user 该用户
	 * @return   更新成功返回1，更新失败返回0
	 */
	@Override
	public int doUpdate(User user) {
		// 可以更改用户的名，用户密码，用户支付密码，用户手机号，用户位置
		String sql = "UPDATE users SET username = ?, password = ?,pay_password =?,tel = ?,is_admin = ?,cycling_time = ?,register_time = ?,location_id = ? WHERE id = ?";
		return executeUpdate(sql, user.getUsername(), user.getPassword(), user.getPayPassword(), user.getTel(),
				user.getIsAdmin(), user.getCyclingTime(), user.getRegisterTime(), user.getLocationID(), user.getId());
	}
	/**
	 * 根据用手机号查询该用户
	 * @param tel 用户的手机号
	 * @return  该手机号所对应的用户
	 */
	@Override
	public User queryByUserTel(String tel) {
		String sql = "SELECT * FROM users WHERE tel = ?";
		return query4Bean(sql, User.class, tel);
	}

}
