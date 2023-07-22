package nestorcicardini.skilltrade.languages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/langs")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class LanguageController {
	@Autowired
	LanguageRepository langsRepo;

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public List<Language> getAllLanguages() {
		return langsRepo.findAll();
	}

}