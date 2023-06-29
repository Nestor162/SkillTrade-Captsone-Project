package nestorcicardini.skilltrade.auth;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nestorcicardini.skilltrade.auth.exceptions.InvalidTokenException;
import nestorcicardini.skilltrade.globalExceptions.ErrorPayload;
import nestorcicardini.skilltrade.users.User;
import nestorcicardini.skilltrade.users.UserService;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	UserService usersService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, InvalidTokenException {

		try {

			// This method will be invoked for each request.
			// First, I need to extract the token from the Authorization Header.
			String authHeader = request.getHeader("Authorization");

			if (authHeader == null || !authHeader.startsWith("Bearer "))
				throw new InvalidTokenException();

			String accessToken = authHeader.substring(7);

			// Verify that the token has not been tampered with or expired.
			JWTUtils.tokenValidation(accessToken);

			// If OK, extract the user ID from the token and find the user.
			String userId = JWTUtils.extractSubject(accessToken);

			User user = usersService.getUserById(userId);

			// Add the user to the SecurityContextHolder.
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					user, null, user.getAuthorities());
			authToken.setDetails(
					new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authToken);

			// Proceed to the next filterChain block.
			filterChain.doFilter(request, response);

			// Managing the error within the filter was quite challenging.
			// I couldn't simply throw the InvalidTokenException because it was
			// being caught by the servlet.service(), not allowing me to handle
			// it properly.
		} catch (InvalidTokenException e) {
			//
			ErrorPayload payload = new ErrorPayload(
					"Please add the token to the authorization header.",
					new Date(), 401);
			ResponseEntity<ErrorPayload> responseEntity = new ResponseEntity<>(
					payload, HttpStatus.UNAUTHORIZED);
			response.setContentType("application/json");
			response.setStatus(401);
			response.getWriter().write(new ObjectMapper()
					.writeValueAsString(responseEntity.getBody()));

		}

	}

	// To prevent the filter from being executed during login/register.
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}