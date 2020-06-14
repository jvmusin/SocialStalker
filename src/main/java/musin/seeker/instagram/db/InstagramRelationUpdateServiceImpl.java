package musin.seeker.instagram.db;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.relation.InstagramRelation;
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
    return relationUpdateRepository.findAllByResourceAndOwner(InstagramDbConstants.RESOURCE, owner + "")
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
        .was(upd.getWas() == null ? null : upd.getWas().getType().toString())
        .now(upd.getNow() == null ? null : upd.getNow().getType().toString())
        .time(LocalDateTime.now())
        .build();
  }

  @Data
  private class InstagramNotifiableUpdateImpl implements InstagramNotifiableUpdate {
    private final Integer id;
    private final InstagramUser owner;
    private final InstagramUser target;
    private final InstagramRelation was;
    private final InstagramRelation now;
    private final LocalDateTime time;

    InstagramNotifiableUpdateImpl(RelationUpdate update) {
      id = update.getId();
      owner = instagramUserFactory.create(Long.parseLong(update.getOwner()));
      target = instagramUserFactory.create(Long.parseLong(update.getTarget()));
      was = new InstagramRelation(target, InstagramRelationType.parseNullSafe(update.getWas()));
      now = new InstagramRelation(target, InstagramRelationType.parseNullSafe(update.getNow()));
      time = update.getTime();
    }
  }
}
