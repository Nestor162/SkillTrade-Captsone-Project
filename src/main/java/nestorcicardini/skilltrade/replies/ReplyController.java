package nestorcicardini.skilltrade.replies;

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

import nestorcicardini.skilltrade.replies.payloads.ReplyPayload;
import nestorcicardini.skilltrade.users.UserUtils;

@RestController
@RequestMapping("/replies")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class ReplyController {
	@Autowired
	ReplyService replyService;

	@Autowired
	// Custom class that have useful methods to check roles/authorities and to
	// get info from authenticated user
	UserUtils userUtils;

	// CRUD:
//	// 1. CREATE (POST METHOD) - http://localhost:3001/replies
	@PostMapping("")
	public ResponseEntity<Reply> publishReply(
			@RequestBody @Validated ReplyPayload body) {

		Reply newReply = replyService.publish(body);
		return new ResponseEntity<>(newReply, HttpStatus.CREATED);
	}

	// 2. READ (GET METHOD) - http://localhost:3001/replies
	@GetMapping("")
	public Page<Reply> getAllReplies(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortValue) {

		return replyService.findWithPagination(page, size, sortValue);
	}

	// 3. READ (GET METHOD) - http://localhost:3001/replies/:replyId
	@GetMapping("/{replyId}")
	public Reply getReplyById(@PathVariable String replyId) {
		return replyService.getReplyById(replyId);
	}

	// 4. READ (GET METHOD) - http://localhost:3001/replies&author=replyId
	@GetMapping(params = "author")
	public List<Reply> getReviewByAuthor(@RequestParam String author) {
		return replyService.getReplyByProfileId(author);
	}

	// 5. UPDATE (PUT METHOD) - http://localhost:3001/replies/:replyId + req.
	// body
	@PutMapping("/{replyId}")
	public Reply updateUser(@PathVariable String replyId,
			@RequestBody @Validated ReplyPayload body) throws Exception {

		return replyService.findByIdAndUpdate(replyId, body);
	}

	// 6. DELETE (DELETE METHOD) - http://localhost:3001/replies/:replyId
	@DeleteMapping("/{replyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN') or @replyService.isReplyAuthor(#replyId, @userUtils.getCurrentProfileId())")
	public void deleteUser(@PathVariable String replyId) {
		replyService.findByIdAndDelete(replyId);
	}

}
