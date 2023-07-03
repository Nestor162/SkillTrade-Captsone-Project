package nestorcicardini.skilltrade.interests.exceptions;

@SuppressWarnings("serial")
public class InterestNotFoundException extends RuntimeException {
	public InterestNotFoundException(String message) {
		super(message);
	}
}
