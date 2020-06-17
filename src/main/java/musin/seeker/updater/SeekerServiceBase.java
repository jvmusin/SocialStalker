package musin.seeker.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.config.ServiceProperties;
import musin.seeker.db.IdFactory;
import musin.seeker.db.seeker.SeekerRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class SeekerServiceBase<ID> implements SeekerService<ID> {
  private final SeekerRepository seekerRepository;
  private final ServiceProperties properties;
  private final IdFactory<ID> idFactory;

  @Override
  public List<ID> findAllOwners() {
    return seekerRepository.findAllByResource(properties.getResource()).stream()
        .map(seeker -> idFactory.create(seeker.getOwner()))
        .collect(toList());
  }
}
