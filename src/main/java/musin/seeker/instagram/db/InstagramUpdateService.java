package musin.seeker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.db.update.RelationUpdateRepository;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.relation.*;
import musin.seeker.notifier.NotifiableUpdateBase;
import musin.seeker.updater.UpdateServiceBase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Service
@Profile("instagram")
@RequiredArgsConstructor
public class InstagramUpdateService extends UpdateServiceBase<InstagramID, InstagramUpdate, InstagramRelationList, InstagramNotifiableUpdate> {

  private final RelationUpdateRepository relationUpdateRepository;
  private final InstagramUserFactory instagramUserFactory;

  @Override
  public CompletableFuture<List<InstagramNotifiableUpdate>> findAllByOwner(InstagramID owner) {
    return relationUpdateRepository.findAllByResourceAndOwnerOrderById(getResource(), owner.toString())
        .thenApply(r -> r.stream().map(InstagramNotifiableUpdateImpl::new).collect(toList()));
  }

  @Override
  public List<InstagramNotifiableUpdate> saveAll(List<? extends InstagramUpdate> updates, InstagramID owner) {
    List<RelationUpdate> relationUpdates = updates.stream()
        .map(update -> updateToRelationUpdate(update, owner))
        .collect(toList());
    return relationUpdateRepository.saveAll(relationUpdates).stream()
        .map(InstagramNotifiableUpdateImpl::new)
        .collect(toList());
  }

  @Override
  protected InstagramRelationList createList(List<InstagramNotifiableUpdate> updates) {
    return new InstagramRelationList(updates);
  }

  @Override
  protected String getResource() {
    return InstagramConstants.RESOURCE;
  }

  private class InstagramNotifiableUpdateImpl extends NotifiableUpdateBase<InstagramID, InstagramUser, InstagramRelationType> implements InstagramNotifiableUpdate {
    protected InstagramNotifiableUpdateImpl(RelationUpdate update) {
      super(update);
    }

    @Override
    protected InstagramUser createUser(InstagramID id) {
      return instagramUserFactory.create(id);
    }

    @Override
    protected InstagramID parseId(String id) {
      return new InstagramID(id);
    }

    @Override
    protected InstagramRelationType parseRelationType(String type) {
      return InstagramRelationType.parseNullSafe(type);
    }

    @Override
    public String getResource() {
      return InstagramUpdateService.this.getResource();
    }
  }
}
