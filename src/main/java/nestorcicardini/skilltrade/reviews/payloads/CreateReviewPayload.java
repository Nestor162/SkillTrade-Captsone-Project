package nestorcicardini.skilltrade.reviews.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateReviewPayload {

	@NotBlank(message = "Title is required")
	@Size(max = 150, message = "Title cannot exceed 150 characters")
	private String title;

	@NotBlank(message = "Content is required")
	@Size(max = 700, message = "Content cannot exceed 700 characters")
	private String content;

	@NotNull(message = "Rating is required")
	@Min(value = 1, message = "Rating must be at least 1")
	@Max(value = 5, message = "Rating must not exceed 5")
	private int rating;

	@NotNull(message = "Profile reviewed don't exist")
	private String profileReviewed;
}
