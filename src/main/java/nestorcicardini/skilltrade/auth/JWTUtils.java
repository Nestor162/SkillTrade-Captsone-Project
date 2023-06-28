package nestorcicardini.skilltrade.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import nestorcicardini.skilltrade.auth.exceptions.InvalidTokenException;
import nestorcicardini.skilltrade.users.User;

@Component
public class JWTUtils {

	private static String secret;
	private static int expiration;

	// Retrieves the 'secret' from the env file (not committed to
	// GitHub)
	@Value("${spring.application.jwt.secret}")
	public void setSecret(String secretKey) {
		secret = secretKey;
	}

	// Retrieves the expiration time (in days) from the env file
	@Value("${spring.application.jwt.expirationindays}")
	public void setExpiration(String expirationInDays) {
		// Converte il tempo in millisecondi
		expiration = Integer.parseInt(expirationInDays) * 24 * 60 * 60 * 1000;
	}

	// Method to create a valid token. It uses a builder that takes 4 values:
	// 1. The primary key (in this case, userId)
	// 2. The issuance time in milliseconds
	// 3. The expiration time in milliseconds (Current date + days until
	// expiration)
	// 4. The 'secret' is combined with other values and processed using the
	// 'HS256' algorithm to generate an authorized token.
	// Note: Anyone with access to the secret can generate valid tokens!

	public static String createToken(User u) {
		String token = Jwts.builder().setSubject(u.getId().toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(
						new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

		return token;
	}

	// Method to validate the token obtained from the Frontend
	public static void tokenValidation(String token) {
		try {
			// Executes the algorithm ('HS256') to verify the validity of the
			// token
			// For a better understanding of the parts of this token, visit:
			// https://jwt.io/
			Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
					.build().parse(token);

			// Two main exceptions can occur: Invalid token or expired token
		} catch (MalformedJwtException e) {
			throw new InvalidTokenException(
					"Invalid token format. Please provide a valid token.");
		} catch (ExpiredJwtException e) {
			throw new InvalidTokenException(
					"Token has expired. Please log in again.");
		} catch (Exception e) {
			throw new InvalidTokenException(
					"Token validation failed. Please try again or log in.");
		}

	}

	// Method used to extract the Subject (in this case, the userId)
	static public String extractSubject(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build()
				.parseClaimsJws(token).getBody().getSubject();
	}
}
