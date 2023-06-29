package nestorcicardini.skilltrade.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import nestorcicardini.skilltrade.profiles.payloads.ProfileCreationPayload;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

	@Autowired
	ProfileService profileService;

	// CRUD:
	// 1. CREATE (POST METHOD)
	// We can't create a profile without registering a user first.
	// The POST method in UserController creates a new user and automatically
	// links a profile to it.

	// At the time of the user creation only the profile_id is defined (all
	// other profile attributes are null). Later, with a PUT method we set all
	// the missing attributes

	// 2. READ (GET METHOD) - http://localhost:3001/profiles
	@GetMapping("")
	public Page<Profile> getAllProfiles(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortValue) {
		return profileService.findWithPagination(page, size, sortValue);
	}

	// 3. READ (GET METHOD) - http://localhost:3001/profiles/{profileId}
	@GetMapping("/{profileId}")
	public Profile getProfileById(@PathVariable String profileId) {
		return profileService.getProfileById(profileId);
	}

	// 4. UPDATE (PUT METHOD) - http://localhost:3001/profiles/:profileId
	// + req. body
	@PutMapping("/{profileId}")
	public Profile updateProfile(@PathVariable String profileId,
			@RequestBody @Validated ProfileCreationPayload body) {

		return profileService.findByIdAndUpdate(profileId, body);
	}

	// 5. DELETE (DELETE METHOD) - http://localhost:3001/profiles/:profileId
	@DeleteMapping("/{profileId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable String profileId) {
		profileService.findByIdAndDelete(profileId);
	}
}
