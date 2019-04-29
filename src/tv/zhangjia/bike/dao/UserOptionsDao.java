package tv.zhangjia.bike.dao;

import tv.zhangjia.bike.entity.UserOptions;

public interface UserOptionsDao {
	/**
	 * ����û�����
	 * @param us �û����ö���
	 * @return ��ӳɹ�����1����Ӳ��ɹ�����0
	 */
	int doInsert(UserOptions us);
	/**
	 * �����û�����
	 * @param us �û����ö���
	 * @return ���³ɹ�����1������ʧ�ܷ���0
	 */
	int doUpdate(UserOptions us);
	/**
	 * ��ѯ�û�����
	 * @param id Ҫ��ѯ���û�id
	 * @param name Ҫ��ѯ����������
	 * @return Ҫ��ѯ����������Ӧ��ֵ
	 */
	String queryUserSetting(int id,String name);
}