package nestorcicardini.skilltrade.profiles.payloads;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import nestorcicardini.skilltrade.interests.Interest;

@Getter
public class ProfileCreationPayload {

	@Size(max = 100)
	private String name;

	@Size(max = 100)
	private String surname;

	@Size(max = 100)
	private String location;

	@Size(min = 25, max = 500)
	private String biography;

	@Past
	private LocalDate birthDate;

	private String gender;

	private Set<String> langs;

	private Set<Interest> interests;

	private String profilePicture;

}
