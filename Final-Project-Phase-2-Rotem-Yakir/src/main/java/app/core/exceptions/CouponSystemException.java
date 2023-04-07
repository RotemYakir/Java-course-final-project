package app.core.exceptions;

/**
 * custom exceptions for the coupon system
 * 
 * @author RotemYakir
 *
 */
public class CouponSystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CouponSystemException() {
	}

	public CouponSystemException(String message) {
		super(message);
	}

	public CouponSystemException(Throwable cause) {
		super(cause);
	}

	public CouponSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouponSystemException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
