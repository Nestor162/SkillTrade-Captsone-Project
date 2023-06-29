package nestorcicardini.skilltrade.users.exceptions;

@SuppressWarnings("serial")
public class InvalidEmailFormatException extends RuntimeException {
	public InvalidEmailFormatException(String message) {
		super(message);
	}

}
