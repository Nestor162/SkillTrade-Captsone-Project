package nestorcicardini.skilltrade.profiles.exceptions;

@SuppressWarnings("serial")
public class ProfileNotFoundException extends RuntimeException {
	public ProfileNotFoundException(String message) {
		super(message);
	}
}
