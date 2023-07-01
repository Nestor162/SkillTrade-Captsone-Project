package nestorcicardini.skilltrade.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nestorcicardini.skilltrade.users.payloads.UserRegistrationPayload;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class userController {
	@Autowired
	UserService userService;

	@Autowired
	private PasswordEncoder bcrypt;

	@Autowired
	// Custom class that have useful methods to check roles/authorities and to
	// get info from authenticated user
	UserUtils userUtils;

	// CRUD:
//	// 1. CREATE (POST METHOD) - User create method is available in Auth Controller

	// 2. READ (GET METHOD) - http://localhost:3001/users
	@GetMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortValue) {

		return userService.findWithPagination(page, size, sortValue);
	}

	// 3. READ (GET METHOD) - http://localhost:3001/users?email={email}
	@GetMapping(params = "email")
	@PreAuthorize("hasAuthority('ADMIN') or #email == authentication.principal.email")
	public User getUserByEmail(@RequestParam("email") String email) {
		return userService.getUserByEmail(email);
	}

	// 4. READ (GET METHOD) - http://localhost:3001/users/{userId}
	@GetMapping("/{userId}")
	@PreAuthorize("hasAuthority('ADMIN') or #userId == @userUtils.getCurrentUserId().toString()")
	public User getUserById(@PathVariable String userId) {
		return userService.getUserById(userId);
	}

	// 5. UPDATE (PUT METHOD) - http://localhost:3001/users/:userId + req. body
	@PutMapping("/{userId}")
	@PreAuthorize("hasAuthority('ADMIN') or #userId == @userUtils.getCurrentUserId().toString()")
	public User updateUser(@PathVariable String userId,
			@RequestBody @Validated UserRegistrationPayload body)
			throws Exception {
		body.setPassword(bcrypt.encode(body.getPassword()));
		return userService.findByIdAndUpdate(userId, body);
	}

	// 6. DELETE (DELETE METHOD) - http://localhost:3001/users/:userId
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN') or #userId == @userUtils.getCurrentUserId().toString()")
	public void deleteUser(@PathVariable String userId) {
		userService.findByIdAndDelete(userId);
	}

}
