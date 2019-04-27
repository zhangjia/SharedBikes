package tv.zhangjia.bike.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * �û���
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.entity
 * @ClassName User
 * @author ZhangJia
 * @Version v1.0
 * @date 2019��3��25�� ����6:10:17new ArrayList<Bike>().add(BIKES.get(0))
 */
public class User {
	private Integer id; // �û�id
	private String username; // �û���,Ψһ
	private String password; // ����
	private String tel; // �û��ֻ��ţ�Ψһ
	private Boolean isAdmin; // �û��Ƿ��ǹ���Ա
	private Long cyclingTime; // �û�������ʱ��
	private Date registerTime; // �û�ע��ʱ��
	private Integer locationID; // ���û����ڵ�λ��ID
	private String locationName; // �û���λ������
	private String payPassword; // �û�֧������

	/*
	 * �޲ι��췽��
	 */
	public User() {
		super();
	}

	/**
	 * ���췽��
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @param tel
	 * @param isAdmin
	 * @param cyclingTime
	 * @param registerTime
	 * @param locationID
	 * @param payPassword
	 */
	public User(Integer id, String username, String password, String tel, Boolean isAdmin, Long cyclingTime,
			Date registerTime, Integer locationID, String payPassword) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.tel = tel;
		this.isAdmin = isAdmin;
		this.cyclingTime = cyclingTime;
		this.registerTime = registerTime;
		this.locationID = locationID;
		this.payPassword = payPassword;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Long getCyclingTime() {
		return cyclingTime;
	}

	public void setCyclingTime(Long cyclingTime) {
		this.cyclingTime = cyclingTime;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getLocationID() {
		return locationID;
	}

	public void setLocationID(Integer locationID) {
		this.locationID = locationID;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cyclingTime == null) ? 0 : cyclingTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isAdmin == null) ? 0 : isAdmin.hashCode());
		result = prime * result + ((locationID == null) ? 0 : locationID.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((payPassword == null) ? 0 : payPassword.hashCode());
		result = prime * result + ((registerTime == null) ? 0 : registerTime.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (cyclingTime == null) {
			if (other.cyclingTime != null)
				return false;
		} else if (!cyclingTime.equals(other.cyclingTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAdmin == null) {
			if (other.isAdmin != null)
				return false;
		} else if (!isAdmin.equals(other.isAdmin))
			return false;
		if (locationID == null) {
			if (other.locationID != null)
				return false;
		} else if (!locationID.equals(other.locationID))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (payPassword == null) {
			if (other.payPassword != null)
				return false;
		} else if (!payPassword.equals(other.payPassword))
			return false;
		if (registerTime == null) {
			if (other.registerTime != null)
				return false;
		} else if (!registerTime.equals(other.registerTime))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		// ��ʽ����ʾʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return id + "\t" + username + "\t" + tel + "\t" + cyclingTime + "��" + "\t" + sdf.format(registerTime);
	
	}

}
