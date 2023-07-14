package nestorcicardini.skilltrade.posts;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nestorcicardini.skilltrade.interests.Interest;
import nestorcicardini.skilltrade.profiles.Profile;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post {

	@Id
	@GeneratedValue
	private UUID id;
	private String title;
	@Column(length = 700)
	private String content;

	private boolean isEdited;

	@Enumerated(EnumType.STRING)
	private SkillLevel skillLevel;

	@Enumerated(EnumType.STRING)
	private PostStatus status;

	@Enumerated(EnumType.STRING)
	private Availability availability;

	private LocalDate publicationDate;
	@Column(length = 300)
	private String imageUrl;

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "profile_id")
//	@JsonIncludeProperties(value = { "id" })
	@JsonIdentityReference(alwaysAsId = true)
	private Profile profile;

	// Referring to 'interest' as 'category' in the context of posts because
	// it is more coherent.
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Interest category;

	public enum SkillLevel {
		BEGINNER, INTERMEDIATE, ADVANCED, EXPERT, ALL_LEVELS, NOT_APPLICABLE
	}

	public enum PostStatus {
		ACTIVE, INACTIVE, COMPLETED, PENDING, CANCELLED, ARCHIVED, DRAFT
	}

	public enum Availability {
		FULL_TIME, PART_TIME, WEEKDAYS, WEEKENDS, EVENINGS, MORNINGS, FLEXIBLE,
		BY_APPOINTMENT, REMOTE, NOT_AVAILABLE
	}

	public Post(String title, String content, SkillLevel skillLevel,
			PostStatus status, Availability availability,
			LocalDate publicationDate, String imageUrl, Profile profile,
			Interest category) {
		super();
		this.title = title;
		this.content = content;
		this.skillLevel = skillLevel;
		this.status = status;
		this.availability = availability;
		this.publicationDate = publicationDate;
		this.imageUrl = imageUrl;
		this.profile = profile;
		this.category = category;
	}

}
