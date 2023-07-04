package nestorcicardini.skilltrade.posts.exceptions;

@SuppressWarnings("serial")
public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(String message) {
		super(message);
	}
}
