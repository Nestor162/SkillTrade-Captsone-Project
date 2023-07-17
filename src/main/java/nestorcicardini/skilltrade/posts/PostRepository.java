package nestorcicardini.skilltrade.posts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import nestorcicardini.skilltrade.profiles.Profile;

public interface PostRepository
		extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

	Optional<List<Post>> findByProfile(Profile author);
}
