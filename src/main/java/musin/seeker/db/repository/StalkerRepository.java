package musin.seeker.db.repository;

import musin.seeker.db.model.Stalker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StalkerRepository extends JpaRepository<Stalker, Integer> {
  Optional<Stalker> findByTelegramId(long telegramId);
}
