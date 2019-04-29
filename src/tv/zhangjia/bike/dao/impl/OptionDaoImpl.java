package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.entity.Options;
import tv.zhangjia.bike.util.CommonDao;

public class OptionDaoImpl extends CommonDao implements OptionDao {
	/**
	 * ��ѯ���е�����
	 * @return ���е�����
	 */
	@Override
	public List<Options> queryAlloptions() {
		String sql = "SELECT * FROM options";
		return query4BeanList(sql,Options.class);
	}
	
	/**
	 * ����ĳ������
	 * @param value ������
	 * @param name ����ֵ
	 * @return ���³ɹ�����1������ʧ�ܷ���0
	 */
	@Override
	public int doUpdate(String value,String name) {
		String sql = "UPDATE options SET value=? WHERE name=?";
		return executeUpdate(sql, value,name);
	}

	/**
	 * �����������Ʋ��Ҷ�Ӧ��ֵ
	 * @param name ������
	 * @return ����������Ӧ��ֵ
	 */
	@Override
	public String queryValue(String name) {
		String sql = "SELECT options.value FROM options WHERE options.name = ?";
		return query4StringData(sql, name);
	}


}
