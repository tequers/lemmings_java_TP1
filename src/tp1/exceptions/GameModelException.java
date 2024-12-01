package tp1.exceptions;

public class GameModelException extends Exception {
	public GameModelException() {
		super();
	}

	public GameModelException(String message) {
		super(message);
	}

	public GameModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameModelException(Throwable cause) {
		super(cause);
	}

	public GameModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
