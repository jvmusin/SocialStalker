package musin.socialstalker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.notifier.InstagramNotifiableUpdate;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.updater.GeneralUpdateService;
import musin.socialstalker.updater.UpdateService;
import musin.socialstalker.updater.UpdateServiceFactory;
import musin.socialstalker.updater.UpdateServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramUpdateServiceFactory
    implements UpdateServiceFactory<InstagramID, InstagramNotifiableUpdate, InstagramRelationType> {

  private final GeneralUpdateService<InstagramID, InstagramRelationType> generalUpdateService;

  @Override
  public UpdateService<InstagramID, InstagramNotifiableUpdate, InstagramRelationType> create(Stalker stalker) {
    return new UpdateServiceImpl<>(stalker, generalUpdateService);
  }
}
