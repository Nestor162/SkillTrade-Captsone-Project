package nestorcicardini.skilltrade.reviews;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nestorcicardini.skilltrade.profiles.Profile;
import nestorcicardini.skilltrade.replies.Reply;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
	@Id
	@GeneratedValue
	private UUID id;
	private String title;
	@Column(length = 700)
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

	public Review(String title, String content, LocalDate publicationDate,
			int likes, int rating, Profile reviewAuthor,
			Profile profileReviewed) {
		super();
		this.title = title;
		this.content = content;
		this.publicationDate = publicationDate;
		this.likes = likes;
		this.rating = rating;
		this.reviewAuthor = reviewAuthor;
		this.profileReviewed = profileReviewed;
	}

}
