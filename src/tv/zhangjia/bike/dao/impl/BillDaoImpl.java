package tv.zhangjia.bike.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import tv.zhangjia.bike.dao.BillDao;
import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.dao.WalletDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bill;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.entity.Wallet;
import tv.zhangjia.bike.util.CommonDao;

public class BillDaoImpl extends CommonDao implements BillDao {
	/**
	 * �����¼
	 * @param bill
	 * @return
	 */
	@Override
	public int doInsert(Bill bill) {
		String sql = "INSERT INTO bill VALUES(seq_bill.nextval,?,?,sysdate,?)";
		return executeUpdate(sql, bill.getUserId(),bill.getBillName(),bill.getMoney());
	}

	@Override
	public List<Bill> queryAll() {
		String sql = "SELECT * FROM bill";
		return query4BeanList(sql);
	}


	@Override
	public Bill queryByBillId(int billId) {
		String sql = "SELECT * FROM bill WHERE id = ?";
		return query4Bean(sql, billId);
	}


	@Override
	public List<Bill> queryUserBill(int userId) {
		String sql = "SELECT * FROM bill WHERE user_id = ?";
		return query4BeanList(sql, userId);
	}
	
	@Override
	public boolean export() throws IOException {
		List<Bill> record = queryAll();

		File file = new File("E:" + File.separator + "bike" + File.separator + "Bill" + File.separator + "bill.txt");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		Writer writer = new FileWriter(file, true);
		String str = "���\t" + "�û���\t" + "�˵�����\t\t" + "���仯\t" + "����ʱ��\t" + "\r\n";
		String str1 = "";

		for (Bill bill : record) {
			str1 += bill.toString();
		}
		// 3.2��ʼд��
		writer.write(str);
		writer.write(str1);

		// 4�ͷ���Դ
		writer.close();

		return true;


	}


}
