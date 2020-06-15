package musin.seeker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.seeker.SeekerRepository;
import musin.seeker.instagram.relation.InstagramID;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InstagramSeekerServiceImpl implements InstagramSeekerService {
  private final SeekerRepository seekerRepository;

  @Override
  public List<InstagramID> findAllOwners() {
    return seekerRepository.findAllByResource(InstagramConstants.RESOURCE).stream()
        .map(s -> new InstagramID(s.getOwner()))
        .collect(toList());
  }
}
