package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.Options;

public interface OptionDao {
	/**
	 * ��ѯ���е�����
	 * @return ���е�����
	 */
	List<Options> queryAlloptions();

	/**
	 * �����������Ʋ��Ҷ�Ӧ��ֵ
	 * @param name ������
	 * @return ����������Ӧ��ֵ
	 */
	String queryValue(String name);

	/**
	 * ����ĳ������
	 * @param value ������
	 * @param name ����ֵ
	 * @return ���³ɹ�����1������ʧ�ܷ���0
	 */
	int doUpdate(String value, String name);
}
