package musin.seeker.vkseeker.db;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelationChangeService {
    List<RelationChange> findAllByOwner(int owner);
    RelationChange save(RelationChange relationChange);
}