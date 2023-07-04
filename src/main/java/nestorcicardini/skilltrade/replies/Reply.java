package nestorcicardini.skilltrade.replies;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nestorcicardini.skilltrade.profiles.Profile;
import nestorcicardini.skilltrade.reviews.Review;

@Entity
@Table(name = "replies")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Reply {
	@Id
	@GeneratedValue
	private UUID id;
	@Column(length = 700)
	private String content;
	private LocalDate publicationDate;
	private long likes;
	private boolean isEdited;

	@ManyToOne
	@JoinColumn(name = "replyAuthor")
	@JsonManagedReference
	@JsonIncludeProperties({ "id" })
//	@JsonIdentityReference(alwaysAsId = true)
	private Profile profile;

	@ManyToOne
	@JoinColumn(name = "review_id")
	@JsonIncludeProperties({ "id" })
	@JsonIdentityReference(alwaysAsId = true)
	private Review review;

	public Reply(String content, LocalDate publicationDate, long likes,
			Profile profile, Review review) {
		super();
		this.content = content;
		this.publicationDate = publicationDate;
		this.likes = likes;
		this.profile = profile;
		this.review = review;
	}

}
