package nestorcicardini.skilltrade.users.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationPayload {

// USERNAME VALIDATION CRITERIA:
//    Only contains alphanumeric characters, underscore and dot.
//    Underscore and dot can't be at the end or start of a username (e.g _username / username_ / .username / username.).
//    Underscore and dot can't be next to each other (e.g user_.name).
//    Underscore or dot can't be used multiple times in a row (e.g user__name / user..name).
//    Number of characters must be between 8 to 20.

	@NotNull(message = "Username is required")
	@Pattern(regexp = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$", message = "The username does not meet the required criteria")
	private String username;

	@NotNull(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	// Minimum eight characters, at least one uppercase letter, one lowercase
	// letter, one number and one special character (@$!_#%*?&)
	@NotNull(message = "Password is required")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!_#%*?&]{8,}$", message = "The password does not meet the required criteria")
	private String password;
}
