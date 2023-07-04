package nestorcicardini.skilltrade.replies.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReplyPayload {
	@NotNull
	@Size(min = 1, max = 500)
	private String content;

	@NotNull
	private String review;
}
