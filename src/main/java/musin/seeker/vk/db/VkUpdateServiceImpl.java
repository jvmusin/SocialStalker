package musin.seeker.vk.db;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.relation.VkRelationType;
import musin.seeker.vk.relation.VkUpdate;
import musin.seeker.vk.relation.VkUser;
import musin.seeker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VkUpdateServiceImpl implements VkUpdateService {

  private final RelationUpdateRepository relationUpdateRepository;
  private final VkUserFactory vkUserFactory;

  @Override
  public CompletableFuture<List<VkNotifiableUpdate>> findAllByOwner(int owner) {
    return relationUpdateRepository.findAllByResourceAndOwnerOrderById(VkDbConstants.RESOURCE, owner + "")
        .thenApply(r -> r.stream().map(VkNotifiableUpdateImpl::new).collect(toList()));
  }

  @Override
  public List<VkNotifiableUpdate> saveAll(List<? extends VkUpdate> updates, int owner) {
    List<RelationUpdate> relationUpdates = updates.stream()
        .map(upd -> vkUpdateToRelationUpdate(upd, owner))
        .collect(toList());
    return relationUpdateRepository.saveAll(relationUpdates).stream()
        .map(VkNotifiableUpdateImpl::new)
        .collect(toList());
  }

  private RelationUpdate vkUpdateToRelationUpdate(VkUpdate upd, int owner) {
    return RelationUpdate.builder()
        .resource(VkDbConstants.RESOURCE)
        .owner(owner + "")
        .target(upd.getTarget().getId().toString())
        .was(upd.getWas() == null ? null : upd.getWas().toString())
        .now(upd.getNow() == null ? null : upd.getNow().toString())
        .time(LocalDateTime.now())
        .build();
  }

  @Data
  private class VkNotifiableUpdateImpl implements VkNotifiableUpdate {
    private final String resource = VkDbConstants.RESOURCE;
    private final Integer id;
    private final VkUser owner;
    private final VkUser target;
    private final VkRelationType was;
    private final VkRelationType now;
    private final LocalDateTime time;

    VkNotifiableUpdateImpl(RelationUpdate update) {
      id = update.getId();
      owner = vkUserFactory.create(Integer.parseInt(update.getOwner()));
      target = vkUserFactory.create(Integer.parseInt(update.getTarget()));
      was = VkRelationType.parseNullSafe(update.getWas());
      now = VkRelationType.parseNullSafe(update.getNow());
      time = update.getTime();
    }
  }
}
