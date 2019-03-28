package tv.zhangjia.bike.dao;

import java.util.List;

public interface RecordDao<T> {
	/**
	 * ��Ӽ�¼
	 * @param t
	 * @return
	 */
	boolean addRecord(T t);
	
	/**
	 * ��ѯ���м�¼,������Ա�鿴
	 * @return ���е����޼�¼
	 */
	List<T> queryAll();
	
	/**
	 * ��ѯ�û���¼�����û��鿴
	 * @param id �û�id
	 * @return ���û������м�¼
	 */
	List<T> queryByUserId(int id);
	
	/**
	 * ��ѯ������¼ID
	 * @param id �û�id
	 * @return ���û������м�¼
	 */
	T queryById(int id);
	
	
}
