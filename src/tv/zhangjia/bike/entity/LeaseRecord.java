package tv.zhangjia.bike.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaseRecord {
	private Integer id; 			// ���޼�¼��ID
	private Integer bikeId; 		// ���г�ID
	private Integer userId; 		// �����û�ID
	private String username;		// �����û���
	private Date leaseTime; 		// ���ʱ��
	private Date returnTime;		// �黹ʱ��
	private String locations;		// ��ʼλ��
	private Double cost; 			// �����������ѽ��
	private Long time;				// ��������ʱ��

	//�޲ι��췽��
	public LeaseRecord() {
		super();
	}

	/**
	 * ���췽��
	 * @param id
	 * @param bikeId
	 * @param userId
	 * @param leaseTime
	 * @param returnTime
	 * @param locations
	 * @param cost
	 * @param time
	 */
	public LeaseRecord(Integer id, Integer bikeId, Integer userId, Date leaseTime, Date returnTime, String locations,
			Double cost, Long time) {
		super();
		this.id = id;
		this.bikeId = bikeId;
		this.userId = userId;
		this.leaseTime = leaseTime;
		this.returnTime = returnTime;
		this.locations = locations;
		this.cost = cost;
		this.time = time;
	}

	/**
	 * ����ID�Ĺ��췽��
	 * @param bikeId
	 * @param userId
	 * @param leaseTime
	 * @param returnTime
	 * @param locations
	 * @param cost
	 * @param time
	 */
	public LeaseRecord(Integer bikeId, Integer userId, Date leaseTime, Date returnTime, String locations, Double cost,
			Long time) {
		super();
		this.bikeId = bikeId;
		this.userId = userId;
		this.leaseTime = leaseTime;
		this.returnTime = returnTime;
		this.locations = locations;
		this.cost = cost;
		this.time = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBikeId() {
		return bikeId;
	}

	public void setBikeId(Integer bikeId) {
		this.bikeId = bikeId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLeaseTime() {
		return leaseTime;
	}

	public void setLeaseTime(Date leaseTime) {
		this.leaseTime = leaseTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getLocations() {
		return locations;
	}

	public void setLocations(String locations) {
		this.locations = locations;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bikeId == null) ? 0 : bikeId.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((leaseTime == null) ? 0 : leaseTime.hashCode());
		result = prime * result + ((locations == null) ? 0 : locations.hashCode());
		result = prime * result + ((returnTime == null) ? 0 : returnTime.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaseRecord other = (LeaseRecord) obj;
		if (bikeId == null) {
			if (other.bikeId != null)
				return false;
		} else if (!bikeId.equals(other.bikeId))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (leaseTime == null) {
			if (other.leaseTime != null)
				return false;
		} else if (!leaseTime.equals(other.leaseTime))
			return false;
		if (locations == null) {
			if (other.locations != null)
				return false;
		} else if (!locations.equals(other.locations))
			return false;
		if (returnTime == null) {
			if (other.returnTime != null)
				return false;
		} else if (!returnTime.equals(other.returnTime))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String lt = (leaseTime == null ? "������\t" : sdf.format(leaseTime));
		String rt = (returnTime == null ? "������\t" : sdf.format(returnTime));
		return id + "\t" + bikeId + "\t" + username + "\t" + lt + "\t" + rt + "\t" + locations + "\t" + time + "��\t"
				+ cost + "Ԫ";
	}

}
