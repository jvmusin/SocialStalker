package musin.seeker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.seeker.SeekerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InstagramSeekerServiceImpl implements InstagramSeekerService {
  private final SeekerRepository seekerRepository;

  @Override
  public List<InstagramSeeker> findAll() {
    return seekerRepository.findAllByResource(InstagramDbConstants.RESOURCE).stream()
        .map(s -> new InstagramSeeker(s.getId(), Long.parseLong(s.getOwner())))
        .collect(toList());
  }
}
