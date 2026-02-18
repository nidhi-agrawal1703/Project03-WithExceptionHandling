package in.co.rays.proj3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

public class ModelFactory {

	private static ResourceBundle rb=ResourceBundle.getBundle("in.co.rays.proj3.bundle.system");
	private static final String DATABASE=rb.getString("DATABASE");
	private static ModelFactory mFactory=null;
	private static HashMap modelCache=new HashMap();
	
	private ModelFactory() {}
	
	public static ModelFactory getInstance() {
		if(mFactory==null) {
			mFactory=new ModelFactory();
		}
		return mFactory;
	}
	
	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel=(MarksheetModelInt)modelCache.get("marksheetModel");
		if(marksheetModel==null) {
			if("Hibernate".equals(DATABASE)) {
				marksheetModel =new MarksheetModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				marksheetModel=new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		
		return marksheetModel;
		
	}
	
	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel=(CollegeModelInt)modelCache.get("collegeModel");
		if(collegeModel==null) {
			if("Hibernate".equals(DATABASE)) {
				collegeModel=new CollegeModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				collegeModel=new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}
	
	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel=(CourseModelInt)modelCache.get("courseModel");
		if(courseModel==null) {
			if("Hibernate".equals(DATABASE)) {
				courseModel=new CourseModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				courseModel=new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}
		return courseModel;
	}
	
	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel=(FacultyModelInt)modelCache.get("facultyModel");
		if(facultyModel==null) {
			if("Hibernate".equals(DATABASE)) {
				facultyModel=new FacultyModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				facultyModel=new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}
		return facultyModel;
	}
	
	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel=(RoleModelInt)modelCache.get("roleModel");
		if(roleModel==null) {
			if("Hibernate".equals(DATABASE)) {
				roleModel=new RoleModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				roleModel=new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}
	
	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel=(StudentModelInt)modelCache.get("studentModel");
		if(studentModel==null) {
			if("Hibernate".equals(DATABASE)) {
				studentModel=new StudentModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				studentModel=new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}
		return studentModel;
	}
	
	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel=(SubjectModelInt)modelCache.get("subjectModel");
		if(subjectModel==null) {
			if("Hibernate".equals(DATABASE)) {
				subjectModel=new SubjectModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				subjectModel=new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}
	
	public TimetableModelInt getTimetableModel() {
		TimetableModelInt timetableModel=(TimetableModelInt)modelCache.get("timetableModel");
		if(timetableModel==null) {
			if("Hibernate".equals(DATABASE)) {
				timetableModel=new TimetableModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				timetableModel=new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}
	
	public UserModelInt getUserModel() {
		UserModelInt userModel=(UserModelInt)modelCache.get("userModel");
		if(userModel==null) {
			if("Hibernate".equals(DATABASE)) {
				userModel=new UserModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				userModel=new UserModelJDBCImpl();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}
	
	public CustomerModelInt getCustomerModel() {
		CustomerModelInt customerModel=(CustomerModelInt)modelCache.get("customerModel");
		if(customerModel==null) {
			if("Hibernate".equals(DATABASE)) {
				customerModel=new CustomerModelHibImpl();
			}
			
			modelCache.put("customerModel", customerModel);
		}

		return customerModel;
	}

	public StaffModelInt getStaffModel() {
		StaffModelInt staffModel=(StaffModelInt)modelCache.get("staffModel");
		if(staffModel==null) {
			if("Hibernate".equals(DATABASE)) {
				staffModel=new StaffModelHibImpl();
			}
			
			modelCache.put("staffModel", staffModel);
		}

		return staffModel;
	}
	
	public InquiryModelInt getInquiryModel() {
		InquiryModelInt inquiryModel=(InquiryModelInt)modelCache.get("inquiryModel");
		if(inquiryModel==null) {
			if("Hibernate".equals(DATABASE)) {
				inquiryModel=new InquiryModelHibImpl();
			}
			
			modelCache.put("inquiryModel", inquiryModel);
		}

		return inquiryModel;
	}

	public CouponModelInt getCouponModel() {
		CouponModelInt couponModel=(CouponModelInt)modelCache.get("couponModel");
		if(couponModel==null) {
			if("Hibernate".equals(DATABASE)) {
				couponModel=new CouponModelHibImpl();
			}
			modelCache.put("couponModel", couponModel);
		}
		return couponModel;
	}
	
	public CompanyModelInt getCompanyModel() {
		CompanyModelInt companyModel=(CompanyModelInt)modelCache.get("companyModel");
		if(companyModel==null) {
			if("Hibernate".equals(DATABASE)) {
				companyModel=new CompanyModelHibImpl();
			}
			modelCache.put("companyModel", companyModel);
		}
		return companyModel;
	}
	
	public ProductModelInt getProductModel() {
		ProductModelInt productModel=(ProductModelInt)modelCache.get("productModel");
		if(productModel==null) {
			if("Hibernate".equals(DATABASE)) {
				productModel=new ProductModelHibImpl();
			}
			if("JDBC".equals(DATABASE)) {
				//productModel=new ProductModelJDBCImpl();
			}
			modelCache.put("productModel", productModel);
		}

		return productModel;
	}

}
