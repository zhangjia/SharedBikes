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
	 * @param username �û���
	 * @param password ����
	 * @return	ע����û�
	 */
	User login(String username, String password);

	/**
	 * �û�ע��
	 * @param username	�û���
	 * @param password	����
	 * @param tel	�ֻ���
	 * @param payPassword ֧������
	 * @return   ע���Ƿ�ɹ����ɹ�����ע���ID�����ɹ�����0
	 */
	int register(String username, String password, String tel, String payPassword);

	/**
	 * �����û�ID��ѯ���û�
	 * @param userId �û�ID
	 * @return   ���û�ID��Ӧ���û�
	 */
	User queryByUserId(int userId);

	/**
	 * �������ֻ��Ų�ѯ���û�
	 * @param tel �û����ֻ���
	 * @return   ���ֻ�������Ӧ���û�
	 */
	User queryByUserTel(String tel);

	/**
	 * ��ѯ���е��û�
	 * @return   ���е��û�
	 */
	List<User> queryAll();

	/**
	 * �û����Ƿ��Ѿ�����
	 * @param username Ҫ�����û���
	 * @return   ���ڷ���true�������ڷ���flase
	 */
	boolean isUserNameExist(String username);

	/**
	 * �����û���Ϣ
	 * @param user ���û�
	 * @return   ���³ɹ�����1������ʧ�ܷ���0
	 */
	int doUpdate(User user);

	/**
	 * ����������û���
	 * @param username �û�ע��ʱ������Ѵ��ڵ��û���
	 * @return   ϵͳ������û���
	 */
	String adviseUsername(String username);

	/**
	 * �ֻ����Ƿ��Ѵ���
	 * @param tel Ҫ�жϵ��ֻ���
	 * @return   ���ڷ���true�������ڷ���false
	 */
	boolean isTelExist(String tel);

	/**
	 * �û���֧�������Ƿ���ȷ
	 * @param userId
	 * @param payPassword
	 * @return   
	 */
	boolean isTruePayPassword(int userId, String payPassword);

}
