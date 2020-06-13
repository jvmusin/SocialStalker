package musin.seeker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.seeker.SeekerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VkSeekerServiceImpl implements VkSeekerService {
  private final SeekerRepository seekerRepository;

  @Override
  public List<VkSeeker> findAll() {
    return seekerRepository.findAllByResource(VkDbConstants.RESOURCE).stream()
        .map(s -> new VkSeeker(s.getId(), Integer.parseInt(s.getOwner())))
        .collect(toList());
  }
}
