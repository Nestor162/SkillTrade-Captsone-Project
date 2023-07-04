package nestorcicardini.skilltrade.posts.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangeStatusPayload {

	@NotBlank(message = "Post status must not be blank")
	private String postStatus;

}
