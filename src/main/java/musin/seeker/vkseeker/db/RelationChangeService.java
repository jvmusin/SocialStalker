package musin.seeker.vkseeker.db;

import musin.seeker.vkseeker.db.model.RelationChange;

import java.util.List;

public interface RelationChangeService {
    List<RelationChange> findAll();
    List<RelationChange> findAllByOwner(int owner);
    RelationChange save(RelationChange relationChange);
    List<RelationChange> saveAll(List<RelationChange> relationChange);
}