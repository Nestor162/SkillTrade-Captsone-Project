package nestorcicardini.skilltrade.languages;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, String> {

	Language findByLanguageCode(String languageCode);

}
