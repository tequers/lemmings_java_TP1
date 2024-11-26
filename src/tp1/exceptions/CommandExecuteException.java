package tp1.exceptions;

public class CommandExecuteException extends CommandException{
	public CommandExecuteException() { super(); }
    public CommandExecuteException(String message) { super(message); }
    public CommandExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
    public CommandExecuteException(Throwable cause) { super(cause); }

    public CommandExecuteException(String message,
    		Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
