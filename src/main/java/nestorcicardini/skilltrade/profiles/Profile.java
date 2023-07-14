package nestorcicardini.skilltrade.profiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Profile {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String surname;
	private String location;
	@Column(length = 500)
	private String biography;
	private LocalDate birthDate;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "profile_language", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "language_code"))
	@JsonManagedReference
	Set<Language> spokenLanguages;

	private double averageRating;
	private String profilePicture;

	@OneToMany(mappedBy = "profile")
	@JsonBackReference
	private Set<Post> posts;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@ManyToMany
	@JoinTable(name = "profile_interests", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "interest_id"))
	private Set<Interest> interests;

	@OneToMany(mappedBy = "profileReviewed")
	@JsonBackReference
	private List<Review> reviewsAboutCurrentProfile;

	@OneToMany(mappedBy = "reviewAuthor")
	@JsonBackReference
	private List<Review> reviewsPublishedByCurrentProfile;

	@OneToMany(mappedBy = "profile")
	@JsonBackReference
	private List<Reply> userReplies;

	public enum Gender {
		MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
	}

	public Profile(String name, String surname, String location,
			String biography, LocalDate birthDate, Gender gender,
			Set<Language> spokenLanguages, double averageRating,
			String profilePicture, Set<Post> posts, User user,
			Set<Interest> interests, List<Review> reviewsAboutCurrentProfile,
			List<Review> reviewsPublishedByCurrentProfile,
			List<Reply> replies) {
		super();
		this.name = name;
		this.surname = surname;
		this.location = location;
		this.biography = biography;
		this.birthDate = birthDate;
		this.gender = gender;
		this.spokenLanguages = spokenLanguages;
		this.averageRating = averageRating;
		this.profilePicture = profilePicture;
		this.posts = posts;
		this.user = user;
		this.interests = interests;
		this.reviewsAboutCurrentProfile = reviewsAboutCurrentProfile;
		this.reviewsPublishedByCurrentProfile = reviewsPublishedByCurrentProfile;
		this.userReplies = replies;
	}

}
