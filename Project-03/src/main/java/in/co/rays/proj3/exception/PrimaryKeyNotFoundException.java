package in.co.rays.proj3.exception;


/**
 * PrimaryKeyNotFound Exception that occurs
 * when there is exception in finding primary key while fetching record
 * 
 * @author Nidhi
 * @version 1.0
 *
 */
public class PrimaryKeyNotFoundException extends Exception {
	
	public PrimaryKeyNotFoundException(String msg) {
		super(msg);
	}
	public PrimaryKeyNotFoundException(String msg, Throwable cause) {
	        super(msg, cause);
	    }
}
