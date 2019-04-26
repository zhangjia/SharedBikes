package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.User;
import tv.zhangjia.bike.util.CommonDao;
import tv.zhangjia.bike.util.Zxing;

/**
 * BikeDao�ӿڵ�ʵ����
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.dao.impl
 * @ClassName BikeDaoImpl
 * @author ZhangJia
 * @Version
 * @date 2019��3��26�� ����4:47:51
 */
public class BikeDaoImpl extends CommonDao implements BikeDao {

	/**
	 * ��ӵ���
	 * @param bike ��������
	 * @return ��ӳɹ�����1�����ʧ�ܷ���0
	 */
	@Override
	public int doInsert(Bike bike) {
		// ���ɶ�ά��
		try {
			Zxing.generateQR(bike);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO bike VALUES(seq_bike.nextval,?,?,?,?,?,?)";
		return executeUpdate(sql, bike.getType(), bike.getLocationId(), bike.getLocationId(), bike.getStatus(),
				bike.getAmount(), bike.getQr());

	}

	/**
	 * ɾ������
	 * @param id Ҫɾ���ĵ���ID
	 * @return ɾ���ɹ�����1��ɾ��ʧ�ܷ���0
	 */
	@Override
	public int doDelete(int id) {
		String sql = "DELETE FROM bike WHERE id = ?";
		return executeUpdate(sql, id);
	}

	/**
	 * ���µ���
	 * @param id Ҫ���µĵ���ID
	 * @return ���³ɹ�����1������ʧ�ܷ���0
	 */
	@Override
	public int doUpdate(Bike bike) {
		String sql = "UPDATE bike SET type = ?,location_id = ?,lastlocation_id = ?,status = ?,amount = ?,qr = ? WHERE id = ?";
		return executeUpdate(sql, bike.getType(), bike.getLocationId(), bike.getLastLocationId(), bike.getStatus(),
				bike.getAmount(), bike.getQr(), bike.getId());

	}

	/**
	 * ��ѯ���е���
	 * @return ���е���
	 */
	@Override
	public List<Bike> queryAll() {
		// ͨ����Option�����ӣ���ȡ��ǰ���͵ļ۸����price�ֶ���
		String sql = "SELECT bike.*,options.value price ,location.location locaion_name FROM bike,options,location WHERE bike.type = options.name AND bike.location_id = location.id";
		return query4BeanList(sql, Bike.class);
	}

	/**
	 * ���ݵ���Id���ص�����Ϣ
	 * @param id ����ID
	 * @return ������Ϣ
	 */
	@Override
	public Bike queryById(int id) {
		String sql = "SELECT bike.*,options.value price ,location.location locaion_name FROM bike,options,location WHERE bike.type = options.name AND bike.location_id = location.id AND bike.id = ?";
		return query4Bean(sql, Bike.class,id);
	}

	/**
	 * ��ѯ����״̬
	 * @param bikeId
	 * @return
	 */
	@Override
	public int bikeStatus(int bikeId) {
		String sql = "SELECT status FROM bike WHERE id = ?";
		return query4IntData(sql, Bike.class, bikeId);

	}

	// TODO ɾ�����ø��µķ����滻
	@Override
	public int setDamage(User user, int bikeId) {
		String sql2 = "UPDATE bike SET status = -1 WHERE id = ?";
		executeUpdate(sql2, bikeId);
		return user.getId();
	}

	// TODO ɾ�����Ѿ����˱�����
	@Override
	public List<Bike> queryByDamage() {
		String sql = "SELECT * FROM bike WHERE status = -1";
		return null;
	}

	// TODO ɾ����ֱ���ò�ѯ�����
	@Override
	public double queryBikePrice(int bikeId) {
		String sql = "SELECT options.value FROM bike,options WHERE bike.type = options.name AND bike.id = ?";
		return 0.0;
	}

	// TODO ɾ����ֱ���ò�ѯ�����
	@Override
	public void updatePrice() {
		// TODO Auto-generated method stub

	}

}
