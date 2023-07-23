package nestorcicardini.skilltrade.reviews;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nestorcicardini.skilltrade.profiles.Profile;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
	Optional<List<Review>> findByReviewAuthor(Profile reviewAuthor);

	Optional<Page<Review>> findByProfileReviewed(Profile profileReviewed,
			Pageable pageable);

	@Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.profileReviewed.id = :profileId GROUP BY r.rating")
	List<Object[]> countReviewsByStars(@Param("profileId") UUID profileId);

}
