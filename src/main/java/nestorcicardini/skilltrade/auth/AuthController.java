package nestorcicardini.skilltrade.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import nestorcicardini.skilltrade.auth.exceptions.InvalidTokenException;
import nestorcicardini.skilltrade.auth.payloads.successfulRegistrationPayload;
import nestorcicardini.skilltrade.users.User;
import nestorcicardini.skilltrade.users.UserService;
import nestorcicardini.skilltrade.users.exceptions.UserNotFoundException;
import nestorcicardini.skilltrade.users.payloads.UserLoginPayload;
import nestorcicardini.skilltrade.users.payloads.UserRegistrationPayload;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService usersService;

	@Autowired
	private PasswordEncoder bcrypt;

	@PostMapping("/register")
	// 1. CREATE (POST METHOD) - http://localhost:3001/auth/register
	public ResponseEntity<User> register(
			@RequestBody @Validated UserRegistrationPayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		User createdUser = usersService.save(body);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<successfulRegistrationPayload> login(
			@RequestBody UserLoginPayload body) throws UserNotFoundException {
		User user = usersService.getUserByEmail(body.getEmail());

		String plainPW = body.getPassword();
		String hashedPW = user.getPassword();

		if (!bcrypt.matches(plainPW, hashedPW))
			throw new InvalidTokenException(
					"Invalid credentials. Please check your email and password and try again.");

		String token = JWTUtils.createToken(user);
		return new ResponseEntity<>(new successfulRegistrationPayload(token),
				HttpStatus.OK);
	}

}
