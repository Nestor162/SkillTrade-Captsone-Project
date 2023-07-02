package nestorcicardini.skilltrade.profiles;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

		found.setName(body.getName());
		found.setSurname(body.getSurname());
		found.setLocation(body.getLocation());
		found.setBiography(body.getBiography());
		found.setBirthDate(body.getBirthDate());
		found.setGender(Gender.valueOf(body.getGender()));
		found.setProfilePicture(body.getProfilePicture());
//		found.setInterests(body.getInterests());

		found.getSpokenLanguages().clear();
		profileRepo.save(found);

		for (String languageCode : body.getLangs()) {

			Language language = langRepo.findByLanguageCode(languageCode)
					.orElseThrow(() -> new LanguageNotFoundException(
							"Language not found with code: " + languageCode));

			if (!body.getLangs().contains(language)) {
				System.out.println("language found: " + language.toString());
				found.getSpokenLanguages().add(language);
			}

		}

		return profileRepo.save(found);
	}

	public void findByIdAndDelete(String profileId)
			throws ProfileNotFoundException {
		Profile found = this.getProfileById(profileId);
		profileRepo.delete(found);
	}

}
