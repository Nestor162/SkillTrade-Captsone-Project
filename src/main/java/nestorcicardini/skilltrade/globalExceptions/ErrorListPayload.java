package nestorcicardini.skilltrade.globalExceptions;

import java.util.Date;
import java.util.List;

import lombok.Getter;

@Getter
public class ErrorListPayload extends ErrorPayload {
	private List<String> errors;

	public ErrorListPayload(String message, Date timestamp, int internalCode,
			List<String> errors) {
		super(message, timestamp, internalCode);
		this.errors = errors;
	}

}