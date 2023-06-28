package nestorcicardini.skilltrade.posts;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import nestorcicardini.skilltrade.interests.Interest;
import nestorcicardini.skilltrade.profiles.Profile;

@Entity
@Table(name = "posts")
@Getter
public class Post {

	@Id
	@GeneratedValue
	private UUID id;
	private String title;
	private String content;

	@Enumerated(EnumType.STRING)
	private SkillLevel skillLevel;

	@Enumerated(EnumType.STRING)
	private PostStatus status;

	@Enumerated(EnumType.STRING)
	private Availability availability;

	private LocalDate publicationDate;
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "profile_id")
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

}