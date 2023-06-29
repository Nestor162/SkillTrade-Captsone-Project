package nestorcicardini.skilltrade.profiles;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nestorcicardini.skilltrade.profiles.exceptions.ProfileNotFoundException;
import nestorcicardini.skilltrade.profiles.payloads.ProfileCreationPayload;

@Service
public class ProfileService {
	@Autowired
	ProfileRepository profileRepo;

	public Page<Profile> findWithPagination(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return profileRepo.findAll(pageable);
	}

	public Profile getProfileById(String profileId) {
		return profileRepo.findById(UUID.fromString(profileId))
				.orElseThrow(() -> new ProfileNotFoundException(
						"Profile not found for id: " + profileId));
	}

	public Profile findByIdAndUpdate(String profileId,
			ProfileCreationPayload body) throws ProfileNotFoundException {

		Profile found = this.getProfileById(profileId);

		found.setId(UUID.fromString(profileId));
		found.setName(body.getName());
		found.setSurname(body.getSurname());
		found.setLocation(body.getLocation());
		found.setBiography(body.getBiography());
		found.setBirthDate(body.getBirthDate());
		found.setGender(body.getGender());
		found.setLanguage(body.getLanguage());
		found.setProfilePicture(body.getProfilePicture());
//		found.setInterests(body.getInterests());

		return profileRepo.save(found);
	}

	public void findByIdAndDelete(String profileId)
			throws ProfileNotFoundException {
		Profile found = this.getProfileById(profileId);
		profileRepo.delete(found);
	}
}
