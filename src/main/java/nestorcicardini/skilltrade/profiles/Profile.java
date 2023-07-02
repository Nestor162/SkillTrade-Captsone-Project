package nestorcicardini.skilltrade.profiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nestorcicardini.skilltrade.interests.Interest;
import nestorcicardini.skilltrade.languages.Language;
import nestorcicardini.skilltrade.posts.Post;
import nestorcicardini.skilltrade.replies.Reply;
import nestorcicardini.skilltrade.reviews.Review;
import nestorcicardini.skilltrade.users.User;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Profile {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String surname;
	private String location;
	private String biography;
	private LocalDate birthDate;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@ManyToMany
	@JoinTable(name = "profile_language", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "language_code"))
	List<Language> spokenLanguages;

	private double averageRating;
	private String profilePicture;

	@OneToMany(mappedBy = "profile")
	private Set<Post> posts;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@ManyToMany
	@JoinTable(name = "profile_interests", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "interest_id"))
	private Set<Interest> interests;

	@OneToMany(mappedBy = "profileReviewed")
	private List<Review> reviewsAboutCurrentProfile;

	@OneToMany(mappedBy = "reviewAuthor")
	private List<Review> reviewsPublishedByCurrentProfile;

	@OneToMany(mappedBy = "profile")
	private List<Reply> replies;

	public enum Gender {
		MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
	}

}
