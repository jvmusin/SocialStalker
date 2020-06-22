package musin.socialstalker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.notifier.InstagramNotifiableUpdate;
import musin.socialstalker.instagram.relation.InstagramRelationList;
import musin.socialstalker.instagram.relation.InstagramUpdate;
import musin.socialstalker.updater.GeneralUpdateService;
import musin.socialstalker.updater.UpdateService;
import musin.socialstalker.updater.UpdateServiceFactory;
import musin.socialstalker.updater.UpdateServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramUpdateServiceFactory
    implements UpdateServiceFactory<InstagramID, InstagramUpdate, InstagramRelationList, InstagramNotifiableUpdate> {

  private final GeneralUpdateService<InstagramID, InstagramUpdate, InstagramRelationList, InstagramNotifiableUpdate> generalUpdateService;

  @Override
  public UpdateService<InstagramID, InstagramUpdate, InstagramRelationList, InstagramNotifiableUpdate> create(Stalker stalker) {
    return new UpdateServiceImpl<>(stalker, generalUpdateService);
  }
}