package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.User;

/**
 * User�ӿ�
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.dao
 * @ClassName UserDao
 * @author ZhangJia
 * @Version v1.0
 * @date 2019��3��25�� ����6:55:45
 */
public interface UserDao {
	/**
	 * �û���¼
	 * 
	 * @Title login
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return �û�
	 */
	User login(String username, String password);

	/**
	 * �û�ע��
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            �û�����
	 * @return
	 */
	int register(String username, String password, String tel,String payPassword);

	/**
	 * �����û�ID����ָ���û�
	 * 
	 * @param userID
	 * @return
	 */
	User queryByUserId(int userId);

	/**
	 * ��ѯ�����û�����Ϣ
	 */

	List<User> queryAll();

	/**
	 * �����û�ID�����û���
	 * 
	 * @param id
	 * @return
	 */
	String queryUserName(int id);

	/**
	 * �����û��������û�ID
	 * @param username
	 * @return
	 */
	int queryUserId(String username);


//	/**
//	 * �޸�����
//	 * @param userId
//	 * @param editPassword
//	 * @return
//	 */
//	int editPassword(int userId, String editPassword);
//
//	/**
//	 * �һ�����
//	 * @param userId
//	 * @param password
//	 * @return
//	 */
//	User retrievePassword(int userId, String password);

	/**
	 * �Ƿ���ڸ��û���
	 * @param username
	 * @return
	 */
	boolean isExistUserName(String username);

	int doUpdate(User user);

	String adviseUsername(String username);
	
	boolean isTelExist(String tel);
	
	boolean isTruePayPassword(int userId, String payPassword);
	boolean isTrueTel(String tel,int userId);
}
