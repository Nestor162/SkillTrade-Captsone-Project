package nestorcicardini.skilltrade.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthExceptionHandler {
	@ExceptionHandler(InvalidTokenException.class)

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void handleExceptions() {
	}

}