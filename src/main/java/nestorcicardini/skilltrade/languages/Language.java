package nestorcicardini.skilltrade.languages;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nestorcicardini.skilltrade.profiles.Profile;

@Entity
@Table(name = "langs")
@Getter
@Setter
@NoArgsConstructor
public class Language {
	@Id
	private String languageCode;
	private String languageName;

	@ManyToMany(mappedBy = "spokenLanguages", fetch = FetchType.EAGER)
	@JsonBackReference
	List<Profile> profiles;

	public Language(String languageName, String languageCode) {
		this.languageName = languageName;
		this.languageCode = languageCode;
	}

}
