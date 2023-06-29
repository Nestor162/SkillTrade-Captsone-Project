package nestorcicardini.skilltrade.profiles.payloads;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import nestorcicardini.skilltrade.profiles.Profile;

@Getter
public class ProfileCreationPayload {
	@NotBlank
	@Size(max = 100)
	private String name;

	@NotBlank
	@Size(max = 100)
	private String surname;

	@NotBlank
	@Size(max = 100)
	private String location;

	@NotBlank
	@Size(max = 500)
	private String biography;

	@NotNull
	@Past
	private LocalDate birthDate;

	@NotNull
	private Profile.Gender gender;

	@NotNull
	private Profile.Language language;

	private String profilePicture;

}
