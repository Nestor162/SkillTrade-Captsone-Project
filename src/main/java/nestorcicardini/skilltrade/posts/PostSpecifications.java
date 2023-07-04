package nestorcicardini.skilltrade.posts;

import org.springframework.data.jpa.domain.Specification;

import nestorcicardini.skilltrade.interests.Interest;
import nestorcicardini.skilltrade.posts.Post.Availability;
import nestorcicardini.skilltrade.posts.Post.PostStatus;
import nestorcicardini.skilltrade.posts.Post.SkillLevel;

public class PostSpecifications {

	public static Specification<Post> availabilityEquals(
			Availability availability) {
		return (root, query, cb) -> cb.equal(root.get("availability"),
				availability);
	}

	public static Specification<Post> categoryEquals(Interest category) {
		return (root, query, cb) -> cb.equal(root.get("category"), category);
	}

	public static Specification<Post> skillLevelEquals(SkillLevel skillLevel) {
		return (root, query, cb) -> cb.equal(root.get("skillLevel"),
				skillLevel);
	}

	public static Specification<Post> statusEquals(PostStatus status) {
		return (root, query, cb) -> cb.equal(root.get("status"), status);
	}

	public static Specification<Post> titleContains(String title) {
		return (root, query, cb) -> cb.like(cb.lower(root.get("title")),
				"%" + title.toLowerCase() + "%");
	}

	public static Specification<Post> titleOrDescriptionContains(String query) {
		return (root, cq, cb) -> cb.or(
				cb.like(cb.lower(root.get("title")),
						"%" + query.toLowerCase() + "%"),
				cb.like(cb.lower(root.get("content")),
						"%" + query.toLowerCase() + "%"));
	}

}
