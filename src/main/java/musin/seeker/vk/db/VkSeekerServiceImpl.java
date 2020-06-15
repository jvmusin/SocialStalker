package musin.seeker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.seeker.SeekerRepository;
import musin.seeker.vk.relation.VkID;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VkSeekerServiceImpl implements VkSeekerService {
  private final SeekerRepository seekerRepository;

  @Override
  public List<VkID> findAllOwners() {
    return seekerRepository.findAllByResource(VkConstants.RESOURCE).stream()
        .map(s -> new VkID(s.getOwner()))
        .collect(toList());
  }
}
