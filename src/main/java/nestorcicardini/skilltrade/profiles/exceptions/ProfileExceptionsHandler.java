package nestorcicardini.skilltrade.profiles.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import nestorcicardini.skilltrade.globalExceptions.ErrorPayload;

@ControllerAdvice
public class ProfileExceptionsHandler {

	@ExceptionHandler(ProfileNotFoundException.class)
	public ResponseEntity<ErrorPayload> handleUserNotFoundException(
			ProfileNotFoundException ex) {
		ErrorPayload payload = new ErrorPayload(ex.getMessage(), new Date(),
				404);
		return new ResponseEntity<ErrorPayload>(payload, HttpStatus.NOT_FOUND);
	}

}
