package musin.seeker.vkseeker.db;

import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.db.model.Seeker;

import java.util.List;

public interface SeekerService {
    List<Seeker> findAll();

    Seeker create(int owner, List<RelationChange> changes);

    void delete(Seeker seeker);
}