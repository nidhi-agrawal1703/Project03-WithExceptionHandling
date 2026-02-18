package in.co.rays.proj3.dto;

import java.util.Date;

public class StaffDTO extends BaseDTO {
	
	private String name;
	private Date joiningDate;
	private String division;
	private String previousEmployer;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPreviousEmployer() {
		return previousEmployer;
	}
	public void setPreviousEmployer(String previousEmployer) {
		this.previousEmployer = previousEmployer;
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
		return name;
	}
	
	
}
