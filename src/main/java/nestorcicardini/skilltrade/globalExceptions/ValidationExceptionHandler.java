package nestorcicardini.skilltrade.globalExceptions;

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
	public ResponseEntity<List<String>> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<String> errors = result.getAllErrors().stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<List<String>> handleBindExceptions(BindException ex) {
		BindingResult result = ex.getBindingResult();
		List<String> errors = result.getAllErrors().stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
