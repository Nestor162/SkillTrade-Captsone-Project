package nestorcicardini.skilltrade.reviews;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nestorcicardini.skilltrade.profiles.Profile;
import nestorcicardini.skilltrade.profiles.ProfileService;
import nestorcicardini.skilltrade.reviews.exceptions.ReviewNotFoundException;
import nestorcicardini.skilltrade.reviews.exceptions.SelfReviewNotAllowedException;
import nestorcicardini.skilltrade.reviews.payloads.CreateReviewPayload;
import nestorcicardini.skilltrade.users.UserUtils;
import nestorcicardini.skilltrade.users.exceptions.UserNotFoundException;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepo;

	@Autowired
	UserUtils userUtils;

	@Autowired
	ProfileService profileService;

	public Review publish(CreateReviewPayload body) {

		Profile foundAuthor = profileService
				.getProfileById(userUtils.getCurrentProfileId().toString());

		Profile foundProfileReviewed = profileService
				.getProfileById(body.getProfileReviewed());

		if (foundAuthor.equals(foundProfileReviewed)) {
			throw new SelfReviewNotAllowedException(
					"Reviews on your own profile are not allowed");
		}

		Review newReview = new Review(body.getTitle(), body.getContent(),
				LocalDate.now(), 0, body.getRating(), foundAuthor,
				foundProfileReviewed);

		return reviewRepo.save(newReview);
	}

	public Page<Review> findWithPagination(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return reviewRepo.findAll(pageable);
	}

	public Review getReviewById(String reviewId) {
		return reviewRepo.findById(UUID.fromString(reviewId))
				.orElseThrow(() -> new ReviewNotFoundException(
						"Review not found for id: " + reviewId));
	}

	public Review findByIdAndUpdate(String reviewId, CreateReviewPayload body)
			throws ReviewNotFoundException {

		Review found = this.getReviewById(reviewId);

		found.setId(UUID.fromString(reviewId));
		found.setTitle(body.getTitle());
		found.setContent(body.getContent());
		found.setLikes(0);
		found.setPublicationDate(LocalDate.now());
		found.setRating(body.getRating());

		Profile foundAuthor = profileService
				.getProfileById(userUtils.getCurrentProfileId().toString());

		Profile foundProfileReviewed = profileService
				.getProfileById(body.getProfileReviewed());

		found.setProfileReviewed(foundProfileReviewed);
		found.setReviewAuthor(foundAuthor);

		return reviewRepo.save(found);
	}

	public void findByIdAndDelete(String id) throws UserNotFoundException {
		Review found = this.getReviewById(id);
		reviewRepo.delete(found);
	}

	// Checks whether the current user is the author of the review,
	// allowing only the author to delete it.
	public boolean isReviewAuthor(UUID reviewId, UUID profileId) {

		Review review = this.getReviewById(reviewId.toString());
		return review.getReviewAuthor().getId().equals(profileId);
	}
}