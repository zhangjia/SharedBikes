package tv.zhangjia.bike.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.User;

public class UserDaoImpl implements UserDao {
	private List<User> users = Database.USERS;

	/**
	 * ��д��¼����
	 * @param username	��¼���û���
	 * @param password	��¼������
	 * @return   �û�
	 * @see tv.zhangjia.bike.dao.UserDao#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String username, String password) {
		for(User user : users) {
			//����û���������ƥ�䣬�򷵻ظ��û�
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		// ����û��������벻ƥ�䣬�򷵻�null
		return null;
	}

	/**
	 * ��дע�᷽��
	 * @param username	ע����û���
	 * @param password	ע�������
	 * @return   
	 * @see tv.zhangjia.bike.dao.UserDao#register(java.lang.String, java.lang.String)
	 */
	@Override
	public int register(String username, String password) {
		
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return -1; //�û����Ѿ�����
			}
		}
		//TODO ����λ��ID��Ǯ��ID�����ɷ�ʽ
		
		users.add(new User(Database.nextUserId(),username,password,"��",false,0,new Date(),1,3));
		return 1;
	}

	@Override
	public User queryByUserId(int userId) {
		//������foeach��û�����ݽ��и�д
		for (User user : users) {
			if(user.getId() == userId) {
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> queryAll() {
		
		return users;
	}

}
