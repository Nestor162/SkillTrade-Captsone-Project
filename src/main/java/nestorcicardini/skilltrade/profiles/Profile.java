package nestorcicardini.skilltrade.profiles;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import nestorcicardini.skilltrade.interests.Interest;
import nestorcicardini.skilltrade.posts.Post;
import nestorcicardini.skilltrade.reviews.Review;
import nestorcicardini.skilltrade.users.User;

@Entity
@Table(name = "profiles")
@Getter
@Setter
public class Profile {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String surname;
	private String location;
	private String biography;
	private LocalDate birtDate;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private Language language;

	private double averageRating;
	private String profilePicture;

	@OneToMany(mappedBy = "profiles")
	private Set<Post> posts;

	@OneToOne(mappedBy = "profiles", cascade = CascadeType.ALL)
	private User user;

	@ManyToMany
	@JoinTable(name = "profile_interests", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "interest_id"))
	private Set<Interest> interests;

	@OneToMany(mappedBy = "profiles")
	private Set<Review> reviewsAboutCurrentProfile;

	@OneToMany(mappedBy = "profiles")
	private Set<Review> reviewsPublishedByCurrentProfile;

	public enum Gender {
		MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
	}

	public enum Language {
		CHINESE, SPANISH, ENGLISH, HINDI, ARABIC, BENGALI, PORTUGUESE, RUSSIAN,
		JAPANESE, GERMAN, JAVANESE, PUNJABI, SHANGHAINESE, FRENCH, TELUGU
	}

}
