package nestorcicardini.skilltrade.profiles;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nestorcicardini.skilltrade.interests.Interest;
import nestorcicardini.skilltrade.interests.InterestRepository;
import nestorcicardini.skilltrade.interests.exceptions.InterestNotFoundException;
import nestorcicardini.skilltrade.languages.Language;
import nestorcicardini.skilltrade.languages.LanguageRepository;
import nestorcicardini.skilltrade.languages.exceptions.LanguageNotFoundException;
import nestorcicardini.skilltrade.profiles.Profile.Gender;
import nestorcicardini.skilltrade.profiles.exceptions.ProfileNotFoundException;
import nestorcicardini.skilltrade.profiles.payloads.ProfileCreationPayload;

@Service
public class ProfileService {
	@Autowired
	ProfileRepository profileRepo;

	@Autowired
	LanguageRepository langRepo;

	@Autowired
	InterestRepository interestRepo;

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

	@SuppressWarnings("unlikely-arg-type")
	public Profile findByIdAndUpdate(String profileId,
			ProfileCreationPayload body) throws ProfileNotFoundException {

		Profile found = this.getProfileById(profileId);

		found.setName(body.getName());
		found.setSurname(body.getSurname());
		found.setLocation(body.getLocation());
		found.setBiography(body.getBiography());
		found.setBirthDate(body.getBirthDate());

		if (body.getGender() != null) {
			found.setGender(Gender.valueOf(body.getGender()));
		}

		if (body.getProfilePicture() != null) {
			found.setProfilePicture(body.getProfilePicture());
		}

		if (body.getLangs() != null) {
			// Clear previous languages
			found.getSpokenLanguages().clear();
			profileRepo.save(found);

			// Set profile languages
			for (String languageCode : body.getLangs()) {

				Language language = langRepo.findByLanguageCode(languageCode)
						.orElseThrow(() -> new LanguageNotFoundException(
								"Language not found with code: "
										+ languageCode));

				if (!body.getLangs().contains(language)) {
					found.getSpokenLanguages().add(language);
				}

			}
		}

		// Clear previous interests
		found.getInterests().clear();
		profileRepo.save(found);

		// Set profile interests
		for (Interest currentInterest : body.getInterests()) {
			Interest foundInterest = interestRepo
					.findById(currentInterest.getId())
					.orElseThrow(() -> new InterestNotFoundException(
							"Interest not found with id: "
									+ currentInterest.getId()));
			found.getInterests().add(foundInterest);

		}

		return profileRepo.save(found);
	}

	public void findByIdAndDelete(String profileId)
			throws ProfileNotFoundException {
		Profile found = this.getProfileById(profileId);
		profileRepo.delete(found);
	}

}
