package in.co.rays.proj3.dto;

import java.util.Date;

public class CompanyDTO extends BaseDTO {
	
	private String name;
	private String industryType;
	private Integer employeeCount;
	private Date openingDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public Integer getEmployeeCount() {
		return employeeCount;
	}
	public void setEmployeeCount(Integer employeeCount) {
		this.employeeCount = employeeCount;
	}
	public Date getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
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
