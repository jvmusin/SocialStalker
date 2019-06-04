package musin.seeker.vkseeker.db;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SeekerServiceImpl implements SeekerService {

    private final SeekerRepository seekerRepository;
    private final RelationChangeRepository relationChangeRepository;

    @Override
    public List<Seeker> findAll() {
        return seekerRepository.findAll();
    }

    @Override
    public Seeker create(int owner, List<RelationChange> changes) {
        changes.stream()
                .peek(c -> c.setHidden(true))
                .forEach(relationChangeRepository::save);
        return seekerRepository.save(new Seeker(null, owner));
    }

    @Override
    public void delete(Seeker seeker) {
        seekerRepository.deleteById(seeker.getId());
    }
}
