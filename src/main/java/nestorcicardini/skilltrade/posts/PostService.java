package nestorcicardini.skilltrade.posts;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nestorcicardini.skilltrade.interests.Interest;
import nestorcicardini.skilltrade.interests.InterestRepository;
import nestorcicardini.skilltrade.posts.Post.Availability;
import nestorcicardini.skilltrade.posts.Post.PostStatus;
import nestorcicardini.skilltrade.posts.Post.SkillLevel;
import nestorcicardini.skilltrade.posts.exceptions.PostNotFoundException;
import nestorcicardini.skilltrade.posts.payloads.ChangeStatusPayload;
import nestorcicardini.skilltrade.posts.payloads.PublishPostPayload;
import nestorcicardini.skilltrade.profiles.Profile;
import nestorcicardini.skilltrade.profiles.ProfileService;
import nestorcicardini.skilltrade.users.UserUtils;
import nestorcicardini.skilltrade.users.exceptions.UserNotFoundException;

@Service
public class PostService {

	@Autowired
	PostRepository postRepo;

	@Autowired
	ProfileService profileService;

	@Autowired
	UserUtils userUtils;

	@Autowired
	InterestRepository interestRepo;

	public Post publish(PublishPostPayload post) {

		Profile foundAuthor = profileService
				.getProfileById(userUtils.getCurrentProfileId().toString());

		Interest category = interestRepo.findById(post.getCategoryId())
				.orElseThrow(() -> new PostNotFoundException(
						"Interest not found with id: " + post.getCategoryId()));

		Post newPost = new Post();
		newPost.setTitle(post.getTitle());
		newPost.setContent(post.getContent());
		newPost.setSkillLevel(SkillLevel.valueOf(post.getSkillLevel()));
		newPost.setStatus(PostStatus.valueOf(post.getPostStatus()));
		newPost.setAvailability(Availability.valueOf(post.getAvailability()));
		newPost.setPublicationDate(LocalDate.now());
		newPost.setImageUrl(post.getImageUrl());
		newPost.setProfile(foundAuthor);
		newPost.setCategory(category);

		return postRepo.save(newPost);
	}

	public Page<Post> findWithPagination(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return postRepo.findAll(pageable);
	}

	public Post getPostById(String postId) {
		return postRepo.findById(UUID.fromString(postId))
				.orElseThrow(() -> new PostNotFoundException(
						"Post not found for id: " + postId));
	}

	public Post findByIdAndUpdate(String postId, PublishPostPayload body)
			throws PostNotFoundException {

		Post found = this.getPostById(postId);

		found.setId(UUID.fromString(postId));
		found.setContent(body.getContent());
		found.setImageUrl(body.getImageUrl());
		found.setPublicationDate(LocalDate.now());
		found.setSkillLevel(SkillLevel.valueOf(body.getSkillLevel()));
		found.setStatus(PostStatus.valueOf(body.getPostStatus()));
		found.setAvailability(Availability.valueOf(body.getAvailability()));
		found.setEdited(true);

		Profile postAuthor = profileService
				.getProfileById(userUtils.getCurrentProfileId().toString());
		found.setProfile(postAuthor);

		Interest category = interestRepo.findById(body.getCategoryId())
				.orElseThrow(() -> new PostNotFoundException(
						"Interest not found with id: " + body.getCategoryId()));
		found.setCategory(category);

		return postRepo.save(found);
	}

	public void findByIdAndDelete(String id) throws PostNotFoundException {
		Post found = this.getPostById(id);
		postRepo.delete(found);
	}

	public Post findByIdAndChangeStatus(String postId,
			ChangeStatusPayload status) {
		Post found = this.getPostById(postId);
		found.setStatus(PostStatus.valueOf(status.getPostStatus()));
		return postRepo.save(found);
	}

	public List<Post> getPostsByProfileId(String profileId) {

		List<Post> foundPosts = postRepo
				.findByProfile(profileService.getProfileById(profileId))
				.orElseThrow(() -> new UserNotFoundException(
						"Post author not found for id: " + profileId));
		return foundPosts;
	}
}