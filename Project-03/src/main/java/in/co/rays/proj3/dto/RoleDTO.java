package in.co.rays.proj3.dto;

public class RoleDTO extends BaseDTO {
	
	
	private static final long serialVersionUID = 1L;
	public static final int ADMIN = 1;
	public static final int STUDENT = 2;
	public static final int COLLEGE = 3;
	public static final int KIOSK = 4;
	public static final int FACULTY = 5;	
	
	private String name;
	private String description;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
