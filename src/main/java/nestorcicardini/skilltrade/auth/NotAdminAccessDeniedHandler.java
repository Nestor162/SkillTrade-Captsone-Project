package nestorcicardini.skilltrade.auth;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nestorcicardini.skilltrade.globalExceptions.ErrorPayload;

@Component
public class NotAdminAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException)
			throws IOException, ServletException {
		try {
			System.err.println(
					"Access denied. Only administrators are allowed to access this resource");

			ErrorPayload payload = new ErrorPayload(
					"Access denied. Only administrators are allowed to access this resource",
					new Date(), 403);

			ResponseEntity<ErrorPayload> responseEntity = new ResponseEntity<>(
					payload, HttpStatus.FORBIDDEN);

			response.setContentType("application/json");
			response.setStatus(payload.getInternalCode());
			response.getWriter().write(new ObjectMapper()
					.writeValueAsString(responseEntity.getBody()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}