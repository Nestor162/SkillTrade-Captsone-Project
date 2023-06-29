package nestorcicardini.skilltrade.auth.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import nestorcicardini.skilltrade.globalExceptions.ErrorPayload;

@ControllerAdvice
public class AuthExceptionHandler {

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorPayload> handleInvalidTokenException(
			InvalidTokenException ex) {
		ErrorPayload payload = new ErrorPayload(ex.getMessage(), new Date(),
				401);
		return new ResponseEntity<ErrorPayload>(payload,
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorPayload> handleInvalidCredentialsException(
			InvalidCredentialsException ex) {
		ErrorPayload payload = new ErrorPayload(ex.getMessage(), new Date(),
				401);
		return new ResponseEntity<ErrorPayload>(payload,
				HttpStatus.UNAUTHORIZED);
	}

}