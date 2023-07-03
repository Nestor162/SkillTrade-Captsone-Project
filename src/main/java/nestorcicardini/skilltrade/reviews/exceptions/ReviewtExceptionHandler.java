package nestorcicardini.skilltrade.reviews.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import nestorcicardini.skilltrade.globalExceptions.ErrorPayload;

@ControllerAdvice
public class ReviewtExceptionHandler {

	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<ErrorPayload> handleReviewNotFoundException(
			ReviewNotFoundException ex) {
		ErrorPayload payload = new ErrorPayload(ex.getMessage(), new Date(),
				404);
		return new ResponseEntity<ErrorPayload>(payload, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SelfReviewNotAllowedException.class)
	public ResponseEntity<ErrorPayload> handleSelfReviewNotAllowedException(
			SelfReviewNotAllowedException ex) {
		ErrorPayload payload = new ErrorPayload(ex.getMessage(), new Date(),
				403);
		return new ResponseEntity<ErrorPayload>(payload, HttpStatus.FORBIDDEN);
	}

}
