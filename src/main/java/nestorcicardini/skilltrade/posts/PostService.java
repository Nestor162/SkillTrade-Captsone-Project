package nestorcicardini.skilltrade.posts;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nestorcicardini.skilltrade.interests.Interest;
import nestorcicardini.skilltrade.posts.Post.Availability;
import nestorcicardini.skilltrade.posts.Post.PostStatus;
import nestorcicardini.skilltrade.posts.Post.SkillLevel;
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

	public Post publish(PublishPostPayload post) {

		Profile foundAuthor = profileService
				.getProfileById(userUtils.getCurrentProfileId().toString());

		Interest category = new Interest();

		System.err.println(new Profile());
		System.err.println("Found author: " + foundAuthor);

		Post newPost = new Post();
		newPost.setTitle(post.getTitle());
		newPost.setContent(post.getContent());
		newPost.setSkillLevel(SkillLevel.valueOf(post.getSkillLevel()));
		newPost.setStatus(PostStatus.valueOf(post.getPostStatus()));
		newPost.setAvailability(Availability.valueOf(post.getAvailability()));
		newPost.setPublicationDate(LocalDate.now());
		newPost.setImageUrl(post.getImageUrl());
		newPost.setProfile(foundAuthor);
//		newPost.setCategory(category);

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
				.orElseThrow(() -> new UserNotFoundException(
						"Post not found for id: " + postId));
	}

	public Post findByIdAndUpdate(String postId, PublishPostPayload body)
			throws UserNotFoundException {

		Post found = this.getPostById(postId);

		found.setId(UUID.fromString(postId));
		found.setContent(body.getContent());
		found.setImageUrl(body.getImageUrl());
		found.setPublicationDate(LocalDate.now());
		found.setSkillLevel(SkillLevel.valueOf(body.getSkillLevel()));
		found.setStatus(PostStatus.valueOf(body.getPostStatus()));
		found.setAvailability(Availability.valueOf(body.getAvailability()));

		Profile postAuthor = profileService
				.getProfileById(userUtils.getCurrentProfileId().toString());
		found.setProfile(postAuthor);

//		found.setCategory(body.getCategoryId());

		return postRepo.save(found);
	}

	public void findByIdAndDelete(String id) throws UserNotFoundException {
		Post found = this.getPostById(id);
		postRepo.delete(found);
	}
}