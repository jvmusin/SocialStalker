package musin.seeker.vkseeker.db;

import musin.seeker.vkseeker.db.model.RelationChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationChangeRepository extends JpaRepository<RelationChange, Integer> {
    List<RelationChange> findAllByOrderById();
    List<RelationChange> findAllByOwnerOrderById(int owner);
}