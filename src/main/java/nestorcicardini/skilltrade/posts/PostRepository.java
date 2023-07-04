package nestorcicardini.skilltrade.posts;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository
		extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {
}
