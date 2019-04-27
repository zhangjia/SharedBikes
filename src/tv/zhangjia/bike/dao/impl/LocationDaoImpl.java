package tv.zhangjia.bike.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.LocationDao;
import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.Location;
import tv.zhangjia.bike.util.CommonDao;

public class LocationDaoImpl extends CommonDao implements LocationDao {
	/**
	 * ��ѯ���е�λ����Ϣ
	 * @return
	 */
	@Override
	public List<Location> queryAll() {
		// ��sql�������ÿ��λ�õĳ����������location������
		String sql = "SELECT location.*,s.amount FROM location,(SELECT count(*) amount, location_id FROM bike GROUP BY location_id HAVING location_id != -1) s WHERE location.id = s.location_id;";
		return query4BeanList(sql, Location.class);

	}

	/**
	 * ����λ��id��ѯ����λ����Ϣ
	 * @param id
	 * @return
	 */
	@Override
	public Location queryLocation(int id) {
		String sql = "SELECT location.*,s.amount FROM location,(SELECT count(*) amount, location_id FROM bike GROUP BY location_id HAVING location_id != -1) s WHERE location.id = s.location_id AND location.id =?";
		return query4Bean(sql, Location.class, id);
	}

	/**
	 * ����һ�����λ��
	 * @param locationId
	 * @param bikeId
	 * @param leaseRecordId
	 * @return
	 */
	@Override
	public Location randomLocation(int locationId) {
		String sql = "SELECT * FROM (SELECT * FROM location order by dbms_random.value) WHERE rownum =1 AND id != ?";
		return query4Bean(sql, Location.class, locationId);
	}

	@Override
	public Location randomUserLocation() {
		String sql = "SELECT * FROM (SELECT * FROM location order by dbms_random.value) WHERE rownum =1";
		return query4Bean(sql, Location.class);

	}

	// TODO ɾ�����ò�ѯ����
	@Override
	public String queryLocationName(int locationId) {
		Location l = queryLocation(locationId);
		return l.getLocationName();
	}

	/**
	 * ���Ƚ���
	 * @return ���еĽ�����Ϣ
	 */
	@Override
	public List<String> dispatch() {
		BikeDao bikeDao = new BikeDaoImpl();
		List<Bike> bikes = bikeDao.queryAll();
		List<Location> locations = queryAll();
		List<String> arr = new ArrayList<>();

		double size = 0;
		for (Bike bike : bikes) {
			if (bike.getStatus() != 0) {
				size++;
			}
		}

		double a = size / queryAll().size(); // ���г���������λ������
		int average = (int) (Math.ceil(a));

		Map<Integer, Integer> small = new TreeMap<>();
		Map<Integer, Integer> big = new TreeMap<>();
		for (Location lo : locations) {
			List<Bike> bikesByLo = queryBikesByLocation(lo.getId());

			if (bikesByLo.size() - average > 0) {
				big.put(lo.getId(), bikesByLo.size());
			} else if (bikesByLo.size() - average < 0) {
				small.put(lo.getId(), bikesByLo.size());
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

		// ���λ�ó���������A
		// �ٵ�λ�ó���������B
		int x = 0;
		for (int i = bigs.size() - 1; i >= 0; i--, x++) {

			for (int j = x; j < smalls.size(); j++) {
				int more = bigs.get(i).getValue() - average; // A��ƽ���������������
				// System.out.println("more = " + more);
				// int more = big.get(i) - average;
				int need = average - smalls.get(j).getValue(); // B��ƽ�����ٶ�����
				// System.out.println("need = " + need);
				int cost = more - need; // A ��Ҫ��B ������
				// System.out.println("cost = " + cost);

				if (more == 0) {
					break;
				}

				// ���B��Ҫ�ĳ��� < A
				if (cost >= 0) {
					bigs.get(i).setValue(bigs.get(i).getValue() - need); // A��B����Ҫ�����г���
					smalls.get(j).setValue(smalls.get(j).getValue() + need); // B����A���ĵĳ���
					String bigname = queryLocationName(bigs.get(i).getKey());
					String smallname = queryLocationName(smalls.get(j).getKey());
					arr.add("��" + bigname + "�ó�" + need + "����������" + smallname);

					// ���A�л���ʣ�೵���������޷�����B����������
				} else {

					bigs.get(i).setValue(bigs.get(i).getValue() - more); // A���ܸ��Ķ���B
					smalls.get(j).setValue(smalls.get(j).getValue() + more); // B����A���ĳ���
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
	
	/**
	 * ����λ��ID��ѯ��ǰλ���µ����г���
	 * @param locationId λ��ID
	 * @return ��ǰλ���µ����г���
	 */
	@Override
	public List<Bike> queryBikesByLocation(int locationId) {
		String sql = "SELECT * FROM bike WHERE location_id = ?";
		return query4BeanList(sql, Bike.class, locationId);
	}

}
