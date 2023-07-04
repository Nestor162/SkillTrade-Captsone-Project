package nestorcicardini.skilltrade.replies.exceptions;

@SuppressWarnings("serial")
public class ReplyNotFoundException extends RuntimeException {
	public ReplyNotFoundException(String message) {
		super(message);
	}
}
