package nestorcicardini.skilltrade.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

	// Moved the endpoint to AuthController because here doesn't work
	// (I will refactor later)
	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public ServerStatus getStatus() {
		return new ServerStatus("ready");
	}
}
