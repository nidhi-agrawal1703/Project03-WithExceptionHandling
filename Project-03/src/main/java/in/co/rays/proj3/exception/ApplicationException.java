package in.co.rays.proj3.exception;


/**
 * Application Exception that occurs
 * when there is exception in application like nullpointer exception
 * 
 * @author Nidhi
 * @version 1.0
 *
 */
public class ApplicationException extends Exception {
	
	public ApplicationException(String msg) {
		super(msg);
	}
	public ApplicationException(String msg, Throwable cause) {
	        super(msg, cause);
	    }
}
