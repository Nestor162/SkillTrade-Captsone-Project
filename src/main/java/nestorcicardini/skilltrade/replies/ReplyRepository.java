package nestorcicardini.skilltrade.replies;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import nestorcicardini.skilltrade.profiles.Profile;

public interface ReplyRepository extends JpaRepository<Reply, UUID> {
	Optional<List<Reply>> findByProfile(Profile profile);
}
