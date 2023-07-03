package nestorcicardini.skilltrade.reviews.exceptions;

@SuppressWarnings("serial")
public class SelfReviewNotAllowedException extends RuntimeException {
	public SelfReviewNotAllowedException(String message) {
		super(message);
	}
}
