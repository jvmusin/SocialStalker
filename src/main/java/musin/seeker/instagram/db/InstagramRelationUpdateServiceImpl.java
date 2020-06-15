package musin.seeker.instagram.db;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.relation.InstagramRelationType;
import musin.seeker.instagram.relation.InstagramUpdate;
import musin.seeker.instagram.relation.InstagramUser;
import musin.seeker.instagram.relation.InstagramUserFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InstagramRelationUpdateServiceImpl implements InstagramRelationUpdateService {

  private final RelationUpdateRepository relationUpdateRepository;
  private final InstagramUserFactory instagramUserFactory;

  @Override
  public CompletableFuture<List<InstagramNotifiableUpdate>> findAllByOwner(long owner) {
    return relationUpdateRepository.findAllByResourceAndOwnerOrderById(InstagramDbConstants.RESOURCE, owner + "")
        .thenApply(r -> r.stream().map(InstagramNotifiableUpdateImpl::new).collect(toList()));
  }

  @Override
  public List<InstagramNotifiableUpdate> saveAll(List<? extends InstagramUpdate> updates, long owner) {
    List<RelationUpdate> relationUpdates = updates.stream()
        .map(upd -> instagramUpdateToRelationUpdate(upd, owner))
        .collect(toList());
    return relationUpdateRepository.saveAll(relationUpdates).stream()
        .map(InstagramNotifiableUpdateImpl::new)
        .collect(toList());
  }

  private RelationUpdate instagramUpdateToRelationUpdate(InstagramUpdate upd, long owner) {
    return RelationUpdate.builder()
        .resource(InstagramDbConstants.RESOURCE)
        .owner(owner + "")
        .target(upd.getTarget().getId() + "")
        .was(upd.getWas() == null ? null : upd.getWas().toString())
        .now(upd.getNow() == null ? null : upd.getNow().toString())
        .time(LocalDateTime.now())
        .build();
  }

  @Data
  private class InstagramNotifiableUpdateImpl implements InstagramNotifiableUpdate {
    private final Integer id;
    private final InstagramUser owner;
    private final InstagramUser target;
    private final InstagramRelationType was;
    private final InstagramRelationType now;
    private final LocalDateTime time;

    InstagramNotifiableUpdateImpl(RelationUpdate update) {
      id = update.getId();
      owner = instagramUserFactory.create(Long.parseLong(update.getOwner()));
      target = instagramUserFactory.create(Long.parseLong(update.getTarget()));
      was = InstagramRelationType.parseNullSafe(update.getWas());
      now = InstagramRelationType.parseNullSafe(update.getNow());
      time = update.getTime();
    }
  }
}
