package tv.zhangjia.bike.entity;

/**
 * ������
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.entity
 * @ClassName Bike
 * @author ZhangJia
 * @Version v1.0
 * @date 2019��3��26�� ����5:36:44
 */
public class Bike {
	private Integer id; 				// ����ID
	private String type; 				// ��������
	private Double price; 				// �����۸�
	private Integer locationId; 		// ����λ��
	private String locaionName; 		// ����λ������
	private Integer lastLocationId; 	// �ϴε�λ��
	private Integer lastLocationName; 	// �ϴ�λ������
	private Integer status; 			// ����״̬
	private Integer amount; 			// �������д���
	private String qr;					 // ������ά��

	/**
	 * �޲εĹ��췽��
	 */
	public Bike() {
		super();
	}

	/**
	 * ���췽��
	 * 
	 * @param id
	 * @param type
	 * @param price
	 * @param locationId
	 * @param lastLocationId
	 * @param status
	 * @param amount
	 * @param qr
	 */
	public Bike(Integer id, String type, Double price, Integer locationId, Integer lastLocationId, Integer status,
			Integer amount, String qr) {
		super();
		this.id = id;
		this.type = type;
		this.price = price;
		this.locationId = locationId;
		this.lastLocationId = lastLocationId;
		this.status = status;
		this.amount = amount;
		this.qr = qr;
	}

	/**
	 * ����ID�Ĺ��췽��
	 * 
	 * @param type
	 * @param price
	 * @param locationId
	 * @param lastLocationId
	 * @param status
	 * @param amount
	 * @param qr
	 */
	public Bike(String type, Double price, Integer locationId, Integer lastLocationId, Integer status, Integer amount,
			String qr) {
		super();
		this.type = type;
		this.price = price;
		this.locationId = locationId;
		this.lastLocationId = lastLocationId;
		this.status = status;
		this.amount = amount;
		this.qr = qr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocaionName() {
		return locaionName;
	}

	public void setLocaionName(String locaionName) {
		this.locaionName = locaionName;
	}

	public Integer getLastLocationId() {
		return lastLocationId;
	}

	public void setLastLocationId(Integer lastLocationId) {
		this.lastLocationId = lastLocationId;
	}

	public Integer getLastLocationName() {
		return lastLocationName;
	}

	public void setLastLocationName(Integer lastLocationName) {
		this.lastLocationName = lastLocationName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getQr() {
		return qr;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastLocationId == null) ? 0 : lastLocationId.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((qr == null) ? 0 : qr.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Bike other = (Bike) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastLocationId == null) {
			if (other.lastLocationId != null)
				return false;
		} else if (!lastLocationId.equals(other.lastLocationId))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (qr == null) {
			if (other.qr != null)
				return false;
		} else if (!qr.equals(other.qr))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String locationName = "";
		// ���״̬��-1������ʾ��ǰλ�ã�������ʾ����
		if (locationId == -1) {
			locationName = "������";
		}
		String statusName = null;
		// ���״̬��1������ʾ�ɽ�
		if (status == 1) {
			statusName = "�ɽ�";
			// ���״̬��0������ʾδ�黹
		} else if (status == 0) {
			statusName = "δ�黹";
		} else {
			statusName = "��";
		}

		return id + "\t" + type + "\t" + price + "\t" + locationName + "\t" + statusName + "\t" + amount + "\t" + qr
				+ "\n";
	}

}
