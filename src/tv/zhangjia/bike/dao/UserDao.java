package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.User;

/**
 * User�ӿ�
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.dao	
 * @ClassName	UserDao	
 * @author	ZhangJia
 * @Version	v1.0
 * @date	2019��3��25�� ����6:55:45
 */
public interface UserDao {
	/**
	 * �û���¼
	 * @Title	login	
	 * @param username	�û���
	 * @param password	����
	 * @return	�û�
	 */
	User login(String username, String password);
	/**
	 * �û�ע��
	 * @param username �û���
	 * @param password �û�����
	 * @return
	 */
	int register(String username, String password);
	
	/**
	 * ��ѯָ���û�����Ϣ
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
	 * @param id
	 * @return
	 */
	String queryUserName(int id);
	
}
