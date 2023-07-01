package nestorcicardini.skilltrade.globalExceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccessExeptionHandler {
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorPayload> handleAccessDeniedException(
			AccessDeniedException ex) {
		ErrorPayload payload = new ErrorPayload(
				"Access denied: You are not allowed to access this resource",
				new Date(), 403);
		return new ResponseEntity<ErrorPayload>(payload, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorPayload> handleAuthenticationException(
			AuthenticationException ex) {
		ErrorPayload payload = new ErrorPayload(
				"Unauthorized: You need to authenticate to access this resource",
				new Date(), 401);

		return new ResponseEntity<ErrorPayload>(payload,
				HttpStatus.UNAUTHORIZED);
	}

}
