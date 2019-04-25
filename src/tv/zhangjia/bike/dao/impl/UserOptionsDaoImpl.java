package tv.zhangjia.bike.dao.impl;

import tv.zhangjia.bike.dao.UserOptionsDao;
import tv.zhangjia.bike.entity.UserOptions;
import tv.zhangjia.bike.util.CommonDao;

public class UserOptionsDaoImpl extends CommonDao implements UserOptionsDao {
	/**
	 * ����û�����
	 * @param us �û����ö���
	 * @return ��ӳɹ�����1����Ӳ��ɹ�����0
	 */
	@Override
	public int doInsert(UserOptions us) {
		String sql = "INSERT INTO user_options VALUES(seq_ups.nextval,?,?,?)";
		return executeUpdate(sql, us.getUserId(), us.getName(), us.getValue());
	}

	/**
	 * ��ѯ�û�����
	 * @param id Ҫ��ѯ���û�id
	 * @param name Ҫ��ѯ����������
	 * @return Ҫ��ѯ����������Ӧ��ֵ
	 */
	@Override
	public String queryUserSetting(int id, String name) {
		String sql = "SELECT * FROM user_options WHERE user_id = ? AND name=?";
		return query4Bean(sql, UserOptions.class, id, name).getValue();
	}
	
	/**
	 * �����û�����
	 * @param us �û����ö���
	 * @return ���³ɹ�����1������ʧ�ܷ���0
	 */
	@Override
	public int doUpdate(UserOptions us) {
		String sql = "UPDATE user_options SET value=? WHERE user_id=? AND name=?";
		return executeUpdate(sql, us.getValue(), us.getUserId(), us.getName());
	}

}
