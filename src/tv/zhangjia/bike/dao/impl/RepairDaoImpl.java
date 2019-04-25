package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.RepairDao;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Repair;
import tv.zhangjia.bike.util.CommonDao;

public class RepairDaoImpl extends CommonDao implements RepairDao {

	/**
	 * ��ӱ��޼�¼
	 * @param repair ���޶���
	 * @return ��ӳɹ�����1�����ʧ�ܷ���0
	 */
	@Override
	public int doInsert(Repair repair) {
		String sql = "INSERT INTO repair VALUES(seq_repair.nextval,?,?,sysdate,?,?,null)";
		return executeUpdate(sql, repair.getBikeId(),repair.getUserId(),repair.getResult(),repair.getAdmin_Id());
	}

	/**
	 * �����¼
	 * @param repair
	 * @return
	 */
	@Override
	public int doUpdate(Repair repair) {
		BikeDao bikeDao = new BikeDaoImpl();
		Bike bike = bikeDao.queryById(repair.getBikeId());
		String sql = "UPDATE repair SET result = ? ,admin_Id = ?,disposeDate= sysdate WHERE id = ?";
		int x = executeUpdate(sql, repair.getResult(),repair.getAdmin_Id(),repair.getId());
		int y = 1;
		if(repair.getResult().equals("��")) {
			bike.setStatus(-1);
			y = bikeDao.doUpdate(bike);
		} 
		return x * y;
	}

	/**
	 * ��ѯ���еı��޼�¼
	 * @return ���еı��޼�¼
	 */
	@Override
	public List<Repair> queryAll() {
		String sql = "SElECT * FROM repair";
		return query4BeanList(sql, Repair.class);
	}

	@Override
	public int doDispose() {
		return 0;
	}

	@Override
	public int doDelete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
