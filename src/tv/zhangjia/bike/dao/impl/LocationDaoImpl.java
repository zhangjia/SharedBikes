package tv.zhangjia.bike.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.util.CommonDao;

public class LocationDaoImpl extends CommonDao implements LocationDao {
//	private List<Location> locations = Database.LOCATIONS;
//	private List<Bike> bikes = Database.BIKES;

	@Override
	public List<Location> queryAll() {
		String sql = "SELECT * FROM location";
		return query4BeanList(sql);
		
	}

	@Override
	public Location queryLocation(int id) {
		String sql = "SELECT * FROM location WHERE id = ?";
		return query4Bean(sql,id);
	}



	@Override
	public Location randomLocation(int locationId, int bikeId, int leaseRecordId) {
		List<Location> locations = queryAll();
		Random ran = new Random();
		int index = ran.nextInt(locations.size());
		// ���ѡ��һ��λ��
		int endId = locations.get(index).getId();
		// ������λ�ú͵�ǰλ����ͬ����ô����ѡ��

		while (locationId == endId) {
			index = ran.nextInt(locations.size());
			endId = locations.get(index).getId();
		}

		return queryLocation(endId);

	}
	
	@Override
	public Location randomUserLocation() {
		List<Location> locations = queryAll();
		Random ran = new Random();
		int index = ran.nextInt(locations.size());
		// ���ѡ��һ��λ��
		Location lo = locations.get(index);
		// ������λ�ú͵�ǰλ����ͬ����ô����ѡ��

		return lo;

	}
	

	@Override
	public String queryLocationName(int locationId) {
		Location l = queryLocation(locationId);
		return l.getLocation();
	}

	@Override
	public List<String> dispatch() {
		BikeDao bikeDao = new BikeDaoImpl();
		List<Bike> bikes = bikeDao.queryAll();
		List<Location> locations = queryAll();
		List<String> arr = new ArrayList<>();
	
		double size = 0;
		for (Bike bike : bikes) {
			if(bike.getStatus() != 0) {
				size++;
			}
		}
	
		double  a = size / queryAll().size(); //���г���������λ������
		int  average = (int) (Math.ceil(a));
		
		Map<Integer, Integer> small = new TreeMap<>();
		Map<Integer, Integer> big = new TreeMap<>();
		for (Location lo : locations) {
			List<Bike> bikesByLo = queryBikesByLocation(lo.getId());
			
			if(bikesByLo.size() - average > 0) {
				big.put(lo.getId(), bikesByLo.size());
			} else if(bikesByLo.size() - average < 0){
				small.put(lo.getId(),bikesByLo.size());
			}
		}

		List<Map.Entry<Integer, Integer>> smalls = new ArrayList<Map.Entry<Integer, Integer>>(small.entrySet());
		// Ȼ��ͨ���Ƚ�����ʵ������
		Collections.sort(smalls, new Comparator<Map.Entry<Integer, Integer>>() {
			// ��������
			@Override
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}

		});
		
		
		List<Map.Entry<Integer, Integer>> bigs = new ArrayList<Map.Entry<Integer, Integer>>(big.entrySet());
		// Ȼ��ͨ���Ƚ�����ʵ������
		Collections.sort(bigs, new Comparator<Map.Entry<Integer, Integer>>() {
			// ��������
			@Override
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}

		});
		
		
		
		//���λ�ó���������A  
		//�ٵ�λ�ó���������B
		int x  = 0;
		for (int i = bigs.size() - 1; i >= 0; i--,x++) {
			
			for (int j = x; j < smalls.size(); j++) {
				int more = bigs.get(i).getValue() - average; //A��ƽ���������������
//				System.out.println("more = " + more);
//				int more = big.get(i) - average;
				int need = average - smalls.get(j).getValue(); // B��ƽ�����ٶ�����
//				System.out.println("need = " + need);
				int cost = more - need; // A ��Ҫ��B ������
//				System.out.println("cost = " + cost);
				
				if(more == 0) {
					break;
				}
				
				//���B��Ҫ�ĳ��� < A
				if(cost >= 0) {
					bigs.get(i).setValue(bigs.get(i).getValue() - need);  //A��B����Ҫ�����г���
					smalls.get(j).setValue(smalls.get(j).getValue() + need); //B����A���ĵĳ���
					String bigname = queryLocationName(bigs.get(i).getKey());
					String smallname = queryLocationName(smalls.get(j).getKey());
					arr.add("��" + bigname + "�ó�" + need + "����������" + smallname);
					
				//���A�л���ʣ�೵���������޷�����B����������
				} else {
	
					bigs.get(i).setValue(bigs.get(i).getValue() - more); //A���ܸ��Ķ���B
					smalls.get(j).setValue(smalls.get(j).getValue() + more); //B����A���ĳ���
					String bigname = queryLocationName(bigs.get(i).getKey());
					String smallname = queryLocationName(smalls.get(j).getKey());
					arr.add("��" + bigname + "�ó�" + more + "����������" + smallname);
					break;
				}
				
			}
			
			
		}		
		
//
		
		
		
		return arr;
	}


//	@Override
//	public boolean updateLocationBikes(int locationId) {
//		List<Bike> b = new ArrayList<>();
//		for (Bike bike : bikes) {
//			if(bike.getLocationId() == locationId) {
//				b.add(bike);
//			} 
//		}
//		
//		queryLocation(locationId).setBikes(b);
//		return true;
//	}
	
	
//	@Override
//	public boolean deleteLocationBikes(int locationId,int bikeID) {
//		BikeDao bikeDao = new BikeDaoImpl();
//		//��ȡ��λ�õ���Ϣ
//		Location lo = queryLocation(locationId);
//		Bike bike = bikeDao.queryById(bikeID);
//		
//		lo.getBikes().remove(bike);
//		return true;
//	}

	@Override
	public List<Bike> queryBikesByLocation(int locationId) {
		BikeDao bikeDao = new BikeDaoImpl();
		List<Bike> bikes = bikeDao.queryAll();
		List<Bike> bl = new ArrayList<>();
		for (Bike bike : bikes) {
			if(bike.getLocationId() == locationId) {
				bl.add(bike);
			}
			
		}
		return bl;
	}

	@Override
	public Location getBeanFromResultSet(ResultSet rs) throws SQLException {
		Location lo = new Location();
		lo.setId(rs.getInt(1));
		lo.setLocation(rs.getString(2));
		return lo;
	}

}
