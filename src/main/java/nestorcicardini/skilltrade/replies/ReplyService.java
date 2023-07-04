package nestorcicardini.skilltrade.replies;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nestorcicardini.skilltrade.profiles.Profile;
import nestorcicardini.skilltrade.profiles.ProfileService;
import nestorcicardini.skilltrade.replies.exceptions.ReplyNotFoundException;
import nestorcicardini.skilltrade.replies.payloads.ReplyPayload;
import nestorcicardini.skilltrade.reviews.Review;
import nestorcicardini.skilltrade.reviews.ReviewService;
import nestorcicardini.skilltrade.users.UserUtils;
import nestorcicardini.skilltrade.users.exceptions.UserNotFoundException;

@Service
public class ReplyService {

	@Autowired
	ReplyRepository replyRepo;

	@Autowired
	UserUtils userUtils;

	@Autowired
	ProfileService profileService;

	@Autowired
	ReviewService reviewService;

	public Reply publish(ReplyPayload body) {

		Profile foundReplyAuthor = profileService
				.getProfileById(userUtils.getCurrentProfileId().toString());

		Review reviewToReply = reviewService.getReviewById(body.getReview());

		Reply newReply = new Reply(body.getContent(), LocalDate.now(), 0L,
				foundReplyAuthor, reviewToReply);

		return replyRepo.save(newReply);
	}

	public Page<Reply> findWithPagination(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return replyRepo.findAll(pageable);
	}

	public Reply getReplyById(String replyId) {
		return replyRepo.findById(UUID.fromString(replyId))
				.orElseThrow(() -> new ReplyNotFoundException(
						"Reply not found for id: " + replyId));
	}

	public Reply findByIdAndUpdate(String replyId, ReplyPayload body)
			throws ReplyNotFoundException {

		Reply found = this.getReplyById(replyId);

		found.setId(UUID.fromString(replyId));
		found.setContent(body.getContent());
		found.setLikes(0);
		found.setPublicationDate(LocalDate.now());

		Profile foundReplyAuthor = profileService
				.getProfileById(userUtils.getCurrentProfileId().toString());
		found.setProfile(foundReplyAuthor);

		Review reviewToReply = reviewService.getReviewById(body.getReview());
		found.setReview(reviewToReply);

		return replyRepo.save(found);
	}

	public void findByIdAndDelete(String id) throws ReplyNotFoundException {
		Reply found = this.getReplyById(id);
		replyRepo.delete(found);
	}

	public List<Reply> getReplyByProfileId(String profileId) {

		List<Reply> foundReplies = replyRepo
				.findByProfile(profileService.getProfileById(profileId))
				.orElseThrow(() -> new UserNotFoundException(
						"Reply author not found for id: " + profileId));
		return foundReplies;
	}

	public List<Reply> getReplyByProfileReviewed(String profileId) {

		List<Reply> foundReplies = replyRepo
				.findByProfile(profileService.getProfileById(profileId))
				.orElseThrow(() -> new UserNotFoundException(
						"Reply author not found for id: " + profileId));
		return foundReplies;
	}

	// Checks whether the current user is the author of the reply,
	// allowing only the author to delete it.
	public boolean isReplyAuthor(UUID replyId, UUID profileId) {

		Reply reply = this.getReplyById(replyId.toString());
		return reply.getProfile().getId().equals(profileId);
	}
}