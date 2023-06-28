package nestorcicardini.skilltrade.auth.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class successfulRegistrationPayload {
	private String accessToken;
}
