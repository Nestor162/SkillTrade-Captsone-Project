package nestorcicardini.skilltrade.reviews;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import nestorcicardini.skilltrade.profiles.Profile;
import nestorcicardini.skilltrade.replies.Reply;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {
	@Id
	@GeneratedValue
	private UUID id;
	private String title;
	private String content;
	private LocalDate publicationDate;
	private int likes;
	private int rating;

	@ManyToOne
	@JoinColumn(name = "review_author_id")
	private Profile reviewAuthor;

	@ManyToOne
	@JoinColumn(name = "profile_reviewed_id")
	private Profile profileReviewed;

	@OneToMany(mappedBy = "review")
	private List<Reply> replies;

}
