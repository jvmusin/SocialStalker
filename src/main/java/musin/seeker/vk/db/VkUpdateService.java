package musin.seeker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.notifier.NotifiableUpdateBase;
import musin.seeker.updater.UpdateService;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.relation.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VkUpdateService implements UpdateService<VkID, VkUpdate, VkRelationList, VkNotifiableUpdate> {

  private final RelationUpdateRepository relationUpdateRepository;
  private final VkUserFactory vkUserFactory;

  @Override
  public CompletableFuture<List<VkNotifiableUpdate>> findAllByOwner(VkID owner) {
    return relationUpdateRepository.findAllByResourceAndOwnerOrderById(VkConstants.RESOURCE, owner.toString())
        .thenApply(r -> r.stream().map(VkNotifiableUpdateImpl::new).collect(toList()));
  }

  @Override
  public List<VkNotifiableUpdate> saveAll(List<? extends VkUpdate> updates, VkID owner) {
    List<RelationUpdate> relationUpdates = updates.stream()
        .map(upd -> vkUpdateToRelationUpdate(upd, owner))
        .collect(toList());
    return relationUpdateRepository.saveAll(relationUpdates).stream()
        .map(VkNotifiableUpdateImpl::new)
        .collect(toList());
  }

  @Override
  public CompletableFuture<VkRelationList> buildList(VkID owner) {
    return findAllByOwner(owner).thenApply(VkRelationList::new);
  }

  private RelationUpdate vkUpdateToRelationUpdate(VkUpdate upd, VkID owner) {
    return RelationUpdate.builder()
        .resource(VkConstants.RESOURCE)
        .owner(owner.toString())
        .target(upd.getTarget().getId().toString())
        .was(upd.getWas() == null ? null : upd.getWas().toString())
        .now(upd.getNow() == null ? null : upd.getNow().toString())
        .time(LocalDateTime.now())
        .build();
  }

  private class VkNotifiableUpdateImpl extends NotifiableUpdateBase<VkID, VkUser, VkRelationType> implements VkNotifiableUpdate {
    protected VkNotifiableUpdateImpl(RelationUpdate update) {
      super(update);
    }

    @Override
    protected VkUser createUser(VkID vkID) {
      return vkUserFactory.create(vkID);
    }

    @Override
    protected VkID parseId(String id) {
      return new VkID(id);
    }

    @Override
    protected VkRelationType parseRelationType(String type) {
      return VkRelationType.parseNullSafe(type);
    }

    @Override
    public String getResource() {
      return VkConstants.RESOURCE;
    }
  }
}
