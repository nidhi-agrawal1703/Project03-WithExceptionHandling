	package in.co.rays.proj3.dto;
	
	import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
	
	public class CouponDTO extends BaseDTO {
		
		private String offerCode;
		private BigDecimal discountAmount;
		private Date expiryDate;
		private String offerStatus;
		
		public String getOfferCode() {
			return offerCode;
		}
		public void setOfferCode(String offerCode) {
			this.offerCode = offerCode;
		}
		public BigDecimal getDiscountAmount() {
			return discountAmount;
		}
		public void setDiscountAmount(BigDecimal discountAmount) {
			this.discountAmount = discountAmount;
		}
		
		public Date getExpiryDate() {
			return expiryDate;
		}
		public void setExpiryDate(Date expiryDate) {
			this.expiryDate = expiryDate;
		}
		public String getOfferStatus() {
			return offerStatus;
		}
		public void setOfferStatus(String offerStatus) {
			this.offerStatus = offerStatus;
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
			return offerCode;
		}
		
		
	}
