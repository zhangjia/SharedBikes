package tv.zhangjia.bike.dao;

import java.io.IOException;
import java.util.List;

import tv.zhangjia.bike.entity.Bill;

public interface BillDao {
	/**
	 * �����¼
	 * @param bill
	 * @return ��ӳɹ�����1�����ʧ�ܷ���0
	 */
	int doInsert(Bill bill);

	/**
	 * ��ѯ�����˵����������û�ɾ���ģ�
	 * @return ���е��˵�
	 */
	List<Bill> queryAll();

	/**
	 * �����˵�id��ѯ������¼
	 * @param billId �˵�ID
	 * @return ��Ӧ�ļ�¼
	 */
	Bill queryByBillId(int billId);

	/**
	 * �����û�id��ѯָ���û��������˵�
	 * @param userId �û�id
	 * @return ���û������������˵�
	 */
	List<Bill> queryUserBill(int userId);

	/**
	 * ������¼������
	 * @return �����ɹ�����true������ʧ�ܷ���false
	 * @throws IOException
	 */
	boolean export() throws IOException;

}
