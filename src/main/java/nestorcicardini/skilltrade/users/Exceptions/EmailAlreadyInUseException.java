package nestorcicardini.skilltrade.users.Exceptions;

@SuppressWarnings("serial")
public class EmailAlreadyInUseException extends RuntimeException {
	public EmailAlreadyInUseException(String message) {
		super(message);
	}

}
