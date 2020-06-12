package musin.seeker.db;

import musin.seeker.db.model.RelationChange;
import musin.seeker.db.model.Seeker;

import java.util.List;

public interface SeekerService {
    List<Seeker> findAll();

    Seeker create(int owner, List<RelationChange> changes);

    void delete(Seeker seeker);
}