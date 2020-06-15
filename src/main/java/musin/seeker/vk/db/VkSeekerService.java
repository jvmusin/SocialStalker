package musin.seeker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.seeker.SeekerRepository;
import musin.seeker.updater.SeekerService;
import musin.seeker.vk.api.VkID;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VkSeekerService implements SeekerService<VkID> {
  private final SeekerRepository seekerRepository;

  @Override
  public List<VkID> findAllOwners() {
    return seekerRepository.findAllByResource(VkConstants.RESOURCE).stream()
        .map(seeker -> new VkID(seeker.getOwner()))
        .collect(toList());
  }
}
