package nestorcicardini.skilltrade.users.exceptions;

@SuppressWarnings("serial")
public class EmailAlreadyInUseException extends RuntimeException {
	public EmailAlreadyInUseException(String message) {
		super(message);
	}

}
