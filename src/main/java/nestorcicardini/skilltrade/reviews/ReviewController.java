package nestorcicardini.skilltrade.reviews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nestorcicardini.skilltrade.reviews.payloads.CreateReviewPayload;
import nestorcicardini.skilltrade.users.UserUtils;

@RestController
@RequestMapping("/reviews")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class ReviewController {
	@Autowired
	ReviewService reviewService;

	@Autowired
	// Custom class that have useful methods to check roles/authorities and to
	// get info from authenticated user
	UserUtils userUtils;

	// CRUD:
//	// 1. CREATE (POST METHOD) - http://localhost:3001/reviews
	@PostMapping("")
	public ResponseEntity<Review> publishReview(
			@RequestBody @Validated CreateReviewPayload body) {

		Review newPost = reviewService.publish(body);
		return new ResponseEntity<>(newPost, HttpStatus.CREATED);
	}

	// 2. READ (GET METHOD) - http://localhost:3001/reviews
	@GetMapping("")
	public Page<Review> getAllReviews(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortValue) {

		return reviewService.findWithPagination(page, size, sortValue);
	}

	// 3. READ (GET METHOD) - http://localhost:3001/reviews/:reviewId
	@GetMapping("/{reviewId}")
	public Review getReviewById(@PathVariable String reviewId) {
		return reviewService.getReviewById(reviewId);
	}

	// 4. READ (GET METHOD) - http://localhost:3001/reviews&author=userId
	@GetMapping(params = "author")
	public List<Review> getReviewByAuthor(@RequestParam String author) {
		return reviewService.getReviewByProfileId(author);
	}

	// 5. UPDATE (PUT METHOD) - http://localhost:3001/reviews/:reviewId + req.
	// body
	@PutMapping("/{reviewId}")
	public Review updateUser(@PathVariable String reviewId,
			@RequestBody @Validated CreateReviewPayload body) throws Exception {

		return reviewService.findByIdAndUpdate(reviewId, body);
	}

	// 6. DELETE (DELETE METHOD) - http://localhost:3001/reviews/:reviewId
	@DeleteMapping("/{reviewId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN') or @reviewService.isReviewAuthor(#reviewId, @userUtils.getCurrentProfileId())")
	public void deleteUser(@PathVariable String reviewId) {
		reviewService.findByIdAndDelete(reviewId);
	}

}
