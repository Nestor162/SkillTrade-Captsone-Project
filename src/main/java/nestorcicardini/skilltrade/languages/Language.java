package nestorcicardini.skilltrade.languages;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nestorcicardini.skilltrade.profiles.Profile;

@Entity
@Table(name = "langs")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Language {
	@Id
	private String languageCode;
	private String languageName;

	@ManyToMany(mappedBy = "spokenLanguages")
	List<Profile> profiles;

	public Language(String languageName, String languageCode) {
		super();
		this.languageName = languageName;
		this.languageCode = languageCode;
	}

}
