package nestorcicardini.skilltrade.users.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import nestorcicardini.skilltrade.globalExceptions.ErrorPayload;

@ControllerAdvice
public class UserExcepitonHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorPayload> handleUserNotFoundException(
			UserNotFoundException ex) {
		ErrorPayload payload = new ErrorPayload(ex.getMessage(), new Date(),
				404);
		return new ResponseEntity<ErrorPayload>(payload, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmailAlreadyInUseException.class)
	public ResponseEntity<ErrorPayload> handleEmailAlreadyInUseException(
			EmailAlreadyInUseException ex) {
		ErrorPayload payload = new ErrorPayload(ex.getMessage(), new Date(),
				400);
		return new ResponseEntity<ErrorPayload>(payload,
				HttpStatus.BAD_REQUEST);
	}
}
