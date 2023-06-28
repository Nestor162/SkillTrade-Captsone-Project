package nestorcicardini.skilltrade.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import nestorcicardini.skilltrade.users.payloads.UserRegistrationPayload;

@Controller
@RequestMapping("users")
public class userController {
	@Autowired
	UserService userService;

	// CRUD:
	// 1. CREATE (POST METHOD) - http://localhost:3001/users
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody @Validated UserRegistrationPayload body) {
		return userService.save(body);

	}

	// 2. READ (GET METHOD) - http://localhost:3001/users
	@GetMapping("")
	public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortValue) {
		return userService.findWithPagination(page, size, sortValue);
	}

	// 3. READ (GET METHOD) - http://localhost:3001/users?email={email}
	@GetMapping(params = "email")
	public User getUserByEmail(@RequestParam("email") String email) {
		return userService.getUserByEmail(email);
	}

	// 4. READ (GET METHOD) - http://localhost:3001/users/{userId}
	@GetMapping("/{userId}")
	public User getUserById(@PathVariable String userId) {
		return userService.getUserById(userId);
	}

	// 5. UPDATE (PUT METHOD) - http://localhost:3001/users/:userId + req. body
	@PutMapping("/{userId}")
	@PostAuthorize("hasAuthority('ADMIN')")
	public User updateUser(@PathVariable String userId,
			@RequestBody @Validated UserRegistrationPayload body)
			throws Exception {
		return userService.findByIdAndUpdate(userId, body);
	}

	// 6. DELETE (DELETE METHOD) - http://localhost:3001/users/:userId
	@DeleteMapping("/{addressId}")
	@PostAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable String userId) {
		userService.findByIdAndDelete(userId);
	}

}
