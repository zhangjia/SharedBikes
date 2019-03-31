package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Location;

/**
 * BikeDao�ӿڵ�ʵ����
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.dao.impl	
 * @ClassName	BikeDaoImpl	
 * @author	ZhangJia
 * @Version	
 * @date	2019��3��26�� ����4:47:51
 */
public class BikeDaoImpl implements BikeDao{
	private List<Bike> bikes = Database.BIKES;
	
	//������ӳ�����ʱ��λ�õĳ�������+1
	//TODO ����queryLocation����
	private LocationDao locationDao = new LocationDaoImpl();
	@Override
	public boolean doInsert(Bike bike) {
		bike.setId(Database.nextBikeId());
//		locationDao.addBikeLocation(bike.getId(), bike.getLocationId());
		bikes.add(bike);
		
		
		return locationDao.updateLocationBikes(bike.getLocationId());
	}

	@Override
	public boolean doDelete(int id) {
		Bike d = new Bike();
		for (Bike bike : bikes) {
			if(bike.getId() == id) {
				d = bike;
				bikes.remove(d);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean doUpdate(Bike bike) {
		for (int i = 0; i < bikes.size(); i++) {
			if(bikes.get(i).getId() == bike.getId()) {
				bikes.set(i, bike);
				return true;
			}
			
		}
		
		return false;
	}

	@Override
	public List<Bike> queryAll() {
		return bikes;
	}

	@Override
	public Bike queryById(int id) {
		for (Bike bike : bikes) {
			if(bike.getId() == id) {
				return bike;
			}
		}
		return null;
	}
//
//	@Override
//	public int doLease(int id,User user) {
//		for (Bike bike : bikes) {
//		
//			if(bike.getId() == id ) {
//				if (bike.getStatus() == 1) {
//					
//					bike.setStatus(0); //����Ϊ���״̬
//					bike.setAmount(bike.getAmount() + 1); //�������+1
//					
//					leaseRecordDao.addRecord(new LeaseRecord(id,user.getId(),user.getUsername(),"0","0","1"));
//					
// 					return 1; 	//����и�ID������״̬�ǿɽ�,����1����ɽ�
//				}
//				return -1; //-1 �����ɽ�
//			} 
//		}
//		return 0; //0����ID������
//	}
//
//	@Override
//	public int doReturn(int bikeId, int userId, int recordId) {
//		for (Bike bike : bikes) {
//			if(bike.getId() == id ) {
//				if (bike.getStatus() == 0) {  //����ǽ��״̬
//					bike.setStatus(1); //����Ϊ�黹״̬
//					leaseRecordDao.queryById(bike.getId()).setReturnTime("9");
// 					return 1; 	//����и�ID������״̬�ǲ��ɽ�,����1����黹
//				}
//				return -1; //-1 �����ɻ�
//			} 
//		}
//		return 0; //0����ID������
//	}

	@Override
	public int bikeStatus(int bikeId) {
		
//		11���ɽ�
//		10�����ɽ�
//		5��û�и�Id
//		-1����
		
		for (Bike bike : bikes) {
			if(bike.getId() == bikeId) {
				//���״̬Ϊ�ɽ裬����11
				if(bike.getStatus() == 1) {
					return 11;
					//���״̬Ϊ���ɽ裬����10
				} else if(bike.getStatus() == 0) {
					return 10;
				} else if(bike.getStatus() == -1) {
					return -1; 
				}
			}
		}
		return 5; //û�д�ID
	}

}
