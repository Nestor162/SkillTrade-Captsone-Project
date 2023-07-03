package nestorcicardini.skilltrade.posts;

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

import nestorcicardini.skilltrade.posts.payloads.PublishPostPayload;
import nestorcicardini.skilltrade.users.UserUtils;

@RestController
@RequestMapping("/posts")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class PostController {
	@Autowired
	PostService postService;

	@Autowired
	// Custom class that have useful methods to check roles/authorities and to
	// get info from authenticated user
	UserUtils userUtils;

	// CRUD:
	// 1. CREATE (POST METHOD) - http://localhost:3001/posts
	@PostMapping("")
	public ResponseEntity<Post> publish(
			@RequestBody @Validated PublishPostPayload body) {

		Post newPost = postService.publish(body);
		return new ResponseEntity<>(newPost, HttpStatus.CREATED);
	}

//	// 2. READ (GET METHOD) - http://localhost:3001/posts
	@GetMapping("")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public Page<Post> getAllPosts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortValue) {

		return postService.findWithPagination(page, size, sortValue);
	}

	// 4. READ (GET METHOD) - http://localhost:3001/posts/{postId}
	@GetMapping("/{postId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public Post getPostById(@PathVariable String postId) {
		return postService.getPostById(postId);
	}

	// 5. UPDATE (PUT METHOD) - http://localhost:3001/post/:postId + req. body
	@PutMapping("/{postId}")
	@PreAuthorize("hasAuthority('ADMIN') or @postService.getPostById(#postId).getProfile().getUser().getId().toString() == @userUtils.getCurrentUserId().toString()")
	public Post updateUser(@PathVariable String postId,
			@RequestBody @Validated PublishPostPayload body) throws Exception {

		return postService.findByIdAndUpdate(postId, body);
	}

	// 6. DELETE (DELETE METHOD) - http://localhost:3001/posts/:postId
	@DeleteMapping("/{postId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN') or @postService.getPostById(#postId).getProfile().getUser().getId().toString() == @userUtils.getCurrentUserId().toString()")
	public void deleteUser(@PathVariable String postId) {
		postService.findByIdAndDelete(postId);
	}

}
