package tv.zhangjia.bike.entity;

import java.util.Date;

public class Wallet {
	private int id;
	private int userId; //�û�ID
	private double balance; //�û����
	private double coupon; // �û��Ż�ȯ���
	private boolean isVIP;//�û��Ƿ���VIP���û�����
	private Date vipDate; //VIPʱ��
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getCoupon() {
		return coupon;
	}
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	public boolean isVIP() {
		return isVIP;
	}
	public void setVIP(boolean isVIP) {
		this.isVIP = isVIP;
	}
	public Date getVipDate() {
		return vipDate;
	}
	public void setVipDate(Date vipDate) {
		this.vipDate = vipDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(coupon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + (isVIP ? 1231 : 1237);
		result = prime * result + userId;
		result = prime * result + ((vipDate == null) ? 0 : vipDate.hashCode());
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
		Wallet other = (Wallet) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (Double.doubleToLongBits(coupon) != Double.doubleToLongBits(other.coupon))
			return false;
		if (id != other.id)
			return false;
		if (isVIP != other.isVIP)
			return false;
		if (userId != other.userId)
			return false;
		if (vipDate == null) {
			if (other.vipDate != null)
				return false;
		} else if (!vipDate.equals(other.vipDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return  userId + "\t" + balance + "\t" + coupon + "\t"
				+ isVIP + "\t" + vipDate ;
	}
	public Wallet() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Wallet(int id, int userId, double balance, double coupon, boolean isVIP, Date vipDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.balance = balance;
		this.coupon = coupon;
		this.isVIP = isVIP;
		this.vipDate = vipDate;
	}
	public Wallet(int userId, double balance, double coupon, boolean isVIP, Date vipDate) {
		super();
		this.userId = userId;
		this.balance = balance;
		this.coupon = coupon;
		this.isVIP = isVIP;
		this.vipDate = vipDate;
	}

	
}
