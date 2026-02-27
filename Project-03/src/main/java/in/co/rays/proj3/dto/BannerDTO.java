package in.co.rays.proj3.dto;

public class BannerDTO extends BaseDTO {
	
	private String bannerCode;
	private String bannerTitle;
	private String imagePath;
	private String bannerStatus;
		
	public String getBannerCode() {
		return bannerCode;
	}
	public void setBannerCode(String bannerCode) {
		this.bannerCode = bannerCode;
	}
	public String getBannerTitle() {
		return bannerTitle;
	}
	public void setBannerTitle(String bannerTitle) {
		this.bannerTitle = bannerTitle;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getBannerStatus() {
		return bannerStatus;
	}
	public void setBannerStatus(String bannerStatus) {
		this.bannerStatus = bannerStatus;
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
		return bannerTitle;
	}
	
	

}
