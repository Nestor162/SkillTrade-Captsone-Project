package nestorcicardini.skilltrade.users.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExcepitonHandler {
	@ExceptionHandler({ UserNotFoundException.class,
			EmailAlreadyInUseException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleExceptions() {
	}

}
