package nestorcicardini.skilltrade.languages;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, String> {

	Optional<Language> findByLanguageCode(String languageCode);

}
