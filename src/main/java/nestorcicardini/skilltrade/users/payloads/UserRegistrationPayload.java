package nestorcicardini.skilltrade.users.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationPayload {
	@NotNull(message = "Username is required")
	private String username;

	@NotNull(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@NotNull(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
}
