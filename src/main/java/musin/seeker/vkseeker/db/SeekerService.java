package musin.seeker.vkseeker.db;

import java.util.List;

public interface SeekerService {
    List<Seeker> findAll();

    Seeker create(int owner, List<RelationChange> changes);

    void delete(Seeker seeker);
}