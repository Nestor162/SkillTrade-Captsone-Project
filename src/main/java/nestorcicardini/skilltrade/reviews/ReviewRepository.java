package nestorcicardini.skilltrade.reviews;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import nestorcicardini.skilltrade.profiles.Profile;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
	Optional<List<Review>> findByReviewAuthor(Profile reviewAuthor);

	Optional<Page<Review>> findByProfileReviewed(Profile profileReviewed,
			Pageable pageable);
}
