package nestorcicardini.skilltrade.replies;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import nestorcicardini.skilltrade.profiles.Profile;
import nestorcicardini.skilltrade.reviews.Review;

@Entity
@Table(name = "replies")
@Getter
@Setter
public class Reply {
	@Id
	@GeneratedValue
	private UUID id;
	private String content;
	private LocalDate publicationDate;
	private long likes;

	@ManyToOne
	@JoinColumn(name = "profile_id")
	@JsonManagedReference
	private Profile profile;

	@ManyToOne
	@JoinColumn(name = "review_id")
	private Review review;

}
