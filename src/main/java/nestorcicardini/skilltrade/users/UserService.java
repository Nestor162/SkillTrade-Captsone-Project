package nestorcicardini.skilltrade.users;

import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nestorcicardini.skilltrade.profiles.Profile;
import nestorcicardini.skilltrade.users.exceptions.EmailAlreadyInUseException;
import nestorcicardini.skilltrade.users.exceptions.InvalidEmailFormatException;
import nestorcicardini.skilltrade.users.exceptions.UserNotFoundException;
import nestorcicardini.skilltrade.users.payloads.UserRegistrationPayload;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	public User save(UserRegistrationPayload u) {
		userRepo.findByEmail(u.getEmail()).ifPresent(user -> {
			throw new EmailAlreadyInUseException("Email " + "'"
					+ user.getEmail() + "'" + " already in use!");
		});
		Profile newProfile = new Profile();
		User newUser = new User(u.getUsername(), u.getEmail(), u.getPassword(),
				Role.USER, newProfile);
		newProfile.setUser(newUser);

		return userRepo.save(newUser);
	}

	public Page<User> findWithPagination(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return userRepo.findAll(pageable);
	}

	public User getUserByEmail(String email) {

		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

		if (!Pattern.matches(regexPattern, email)) {
			throw new InvalidEmailFormatException("Invalid email format");
		}

		return userRepo.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(
						"User not found for email: " + "'" + email + "'"));
	}

	public User getUserById(String userId) {
		return userRepo.findById(UUID.fromString(userId))
				.orElseThrow(() -> new UserNotFoundException(
						"User not found for id: " + userId));
	}

	public User findByIdAndUpdate(String userId, UserRegistrationPayload body)
			throws UserNotFoundException {

		User found = this.getUserById(userId);

		found.setId(UUID.fromString(userId));
		found.setEmail(body.getEmail());
		found.setPassword(body.getPassword());
		found.setUsername(body.getUsername());

		return userRepo.save(found);
	}

	public void findByIdAndDelete(String id) throws UserNotFoundException {
		User found = this.getUserById(id);
		userRepo.delete(found);
	}
}