package nestorcicardini.skilltrade.interests;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interests")
public class InterestController {
	@Autowired
	InterestRepository interestRepo;

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public List<Interest> getAllInterests() {
		return interestRepo.findAll();
	}

	@GetMapping("/{interestId}")
	public Optional<Interest> getInterestById(@PathVariable Long interestId) {
		return interestRepo.findById(interestId);
	}
}
