package nestorcicardini.skilltrade.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nestorcicardini.skilltrade.profiles.payloads.ProfileCreationPayload;

@RestController
@RequestMapping("/profiles")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class ProfileController {

	@Autowired
	ProfileService profileService;

	// CRUD:
	// 1. CREATE (POST METHOD)
	// We can't create a profile without registering a user first.
	// The POST method in UserController creates a new user and automatically
	// links a profile to it.

	// At the time of the user creation only the profile_id is defined (all
	// other profile attributes are null). Later, with a PATCH method we set all
	// the missing attributes

	// 2. READ (GET METHOD) - http://localhost:3001/profiles
	@GetMapping("")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public Page<Profile> getAllProfiles(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortValue) {

		return profileService.findWithPagination(page, size, sortValue);
	}

	// 3. READ (GET METHOD) - http://localhost:3001/profiles/{profileId}
	@GetMapping("/{profileId}")
	// Admin and users can acces this endpoint
	public Profile getProfileById(@PathVariable String profileId) {
		return profileService.getProfileById(profileId);
	}

	// 4. UPDATE (PATCH METHOD) - http://localhost:3001/profiles/:profileId
	// + req. body
	@PatchMapping("/{profileId}")
	// USER have access to this method only if the profile matches the
	// authenticated user's profile.
	@PreAuthorize("hasAuthority('ADMIN') or #profileId == @userUtils.getCurrentProfileId().toString()")
	public Profile updateProfile(@PathVariable String profileId,
			@RequestBody @Validated ProfileCreationPayload body) {

		return profileService.findByIdAndUpdate(profileId, body);
	}

	// 6. DELETE (DELETE METHOD) - http://localhost:3001/profiles/:profileId
	@DeleteMapping("/{profileId}")
	@PreAuthorize("hasAuthority('ADMIN') or #profileId == @userUtils.getCurrentProfileId().toString()")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProfile(@PathVariable String profileId) {
		profileService.findByIdAndDelete(profileId);
	}

}
