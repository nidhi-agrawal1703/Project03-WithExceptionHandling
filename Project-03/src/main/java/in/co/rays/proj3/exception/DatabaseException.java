package in.co.rays.proj3.exception;


/**
 * Database Exception that occurs when there is 
 * exception in SQL syntax and other exceptions.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class DatabaseException extends RuntimeException {
	
	public DatabaseException(String msg) {
		super(msg);
	}
	public DatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
