package musin.seeker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.seeker.SeekerRepository;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.updater.SeekerService;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InstagramSeekerService implements SeekerService<InstagramID> {
  private final SeekerRepository seekerRepository;

  @Override
  public List<InstagramID> findAllOwners() {
    return seekerRepository.findAllByResource(InstagramConstants.RESOURCE).stream()
        .map(seeker -> new InstagramID(seeker.getOwner()))
        .collect(toList());
  }
}
