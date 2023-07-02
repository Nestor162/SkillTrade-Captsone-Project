package nestorcicardini.skilltrade.languages.exceptions;

@SuppressWarnings("serial")
public class LanguageNotFoundException extends RuntimeException {
	public LanguageNotFoundException(String message) {
		super(message);
	}
}
