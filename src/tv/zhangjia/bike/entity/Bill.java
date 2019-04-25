package tv.zhangjia.bike.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill {
	private Integer id; 		// �˵���¼ID
	private String billName; 	// �˵���¼����
	private Integer userId; 	// �������˵����û�
	private String userName; 	// �������˵����û���
	private Date billDate; 		// �˵���¼����ʱ��
	private Double money; 		// �˵���¼�����Ľ��

	/**
	 * �޲ι��췽��
	 */
	public Bill() {
		super();
	}

	/**
	 * ���췽��
	 * 
	 * @param id
	 * @param billName
	 * @param userId
	 * @param billDate
	 * @param money
	 */
	public Bill(Integer id, String billName, Integer userId, Date billDate, Double money) {
		super();
		this.id = id;
		this.billName = billName;
		this.billDate = billDate;
		this.money = money;
	}

	/**
	 * ����ID�Ĺ��췽��
	 * 
	 * @param billName
	 * @param userId
	 * @param billDate
	 * @param money
	 */
	public Bill(String billName, Integer userId, Date billDate, Double money) {
		super();
		this.billName = billName;
		this.userId = userId;
		this.billDate = billDate;
		this.money = money;
	}
	/**
	 * ������¼��ʱ�����ɶ���ʱ���Id����ͨ��sql�������
	 * @param billName
	 * @param userId
	 * @param money
	 */
	public Bill(String billName, Integer userId, Double money) {
		super();
		this.billName = billName;
		this.userId = userId;
		this.money = money;
	}

	public Integer getId() {
		return id;
	}

	

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billDate == null) ? 0 : billDate.hashCode());
		result = prime * result + ((billName == null) ? 0 : billName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
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
		Bill other = (Bill) obj;
		if (billDate == null) {
			if (other.billDate != null)
				return false;
		} else if (!billDate.equals(other.billDate))
			return false;
		if (billName == null) {
			if (other.billName != null)
				return false;
		} else if (!billName.equals(other.billName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
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
		// ��ʽ���˵���ʾʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp tp = new Timestamp(billDate.getTime());
		String sm = "";
		// ��ʾ�����˻��ǳ��ˣ���Ϊ֧�������ʱ����Ǹ��������Բ��ü�-
		if (money >= 0) {
			sm = "+" + money;
		} else {
			sm = "" + money;
		}

		return +id + "\t" + userName + "\t" + billName + "\t" + sm + "\t\t" + sdf.format(tp) + "\n";
	}

}
