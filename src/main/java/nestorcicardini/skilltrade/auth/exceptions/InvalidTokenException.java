package nestorcicardini.skilltrade.auth.exceptions;

@SuppressWarnings("serial")
public class InvalidTokenException extends RuntimeException {
	public InvalidTokenException(String message) {
		super(message);
	}
}
