package nestorcicardini.skilltrade.reviews.exceptions;

@SuppressWarnings("serial")
public class ReviewNotFoundException extends RuntimeException {
	public ReviewNotFoundException(String message) {
		super(message);
	}
}
