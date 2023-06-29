package nestorcicardini.skilltrade.globalExceptions;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorListPayload> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<String> errors = result.getAllErrors().stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());

		ErrorListPayload payload = new ErrorListPayload("Validation failed",
				new Date(), 400, errors);
		return new ResponseEntity<ErrorListPayload>(payload,
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorListPayload> handleBindExceptions(
			BindException ex) {
		BindingResult result = ex.getBindingResult();
		List<String> errors = result.getAllErrors().stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());

		ErrorListPayload payload = new ErrorListPayload("Validation failed",
				new Date(), 400, errors);

		return new ResponseEntity<ErrorListPayload>(payload,
				HttpStatus.BAD_REQUEST);
	}
}
