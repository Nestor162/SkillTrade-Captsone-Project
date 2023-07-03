package nestorcicardini.skilltrade.posts.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PublishPostPayload {

	@NotBlank(message = "Title is required")
	@Size(max = 100, message = "Title cannot exceed 100 characters")
	private String title;

	@NotBlank(message = "Content is required")
	private String content;

	@NotNull(message = "Skill level is required")
	private String skillLevel;

	@NotNull(message = "Post status is required")
	private String postStatus;

	@NotNull(message = "Availability is required")
	private String availability;

	@Pattern(regexp = "(https?|ftp)://\\S+\\.(jpeg|jpg|png|gif)$", message = "Invalid image URL. Only JPEG, JPG, PNG, and GIF formats are allowed.")
	private String imageUrl;

//	@NotNull(message = "Category is required")
	private String categoryId;
}
