package musin.seeker.vkseeker.db;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RelationChangeServiceImpl implements RelationChangeService {

    private final RelationChangeRepository repo;


    @Override
    public List<RelationChange> findAllByOwner(int owner) {
        return repo.findAllByOwnerOrderById(owner);
    }

    @Override
    public RelationChange save(RelationChange relationChange) {
        return repo.save(relationChange);
    }
}