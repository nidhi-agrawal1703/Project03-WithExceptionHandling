package in.co.rays.proj3.dto;

import java.sql.Timestamp;

public class AlertDTO extends BaseDTO {
	
	private String alertCode;
	private String alertType;
	protected Timestamp alertTime;
	private String alertStatus;
	
	public String getAlertCode() {
		return alertCode;
	}
	public void setAlertCode(String alertCode) {
		this.alertCode = alertCode;
	}
	public String getAlertType() {
		return alertType;
	}
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	public Timestamp getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(Timestamp alertTime) {
		this.alertTime = alertTime;
	}
	public String getAlertStatus() {
		return alertStatus;
	}
	public void setAlertStatus(String alertStatus) {
		this.alertStatus = alertStatus;
	}
	@Override
	public int compareTo(BaseDTO o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return alertCode;
	}
	
	
}
