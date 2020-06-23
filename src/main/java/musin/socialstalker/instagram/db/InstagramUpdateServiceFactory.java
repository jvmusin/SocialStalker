package musin.socialstalker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.updater.GeneralUpdateService;
import musin.socialstalker.updater.UpdateService;
import musin.socialstalker.updater.UpdateServiceFactory;
import musin.socialstalker.updater.UpdateServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramUpdateServiceFactory implements UpdateServiceFactory<InstagramID, RelationType> {

  private final GeneralUpdateService<InstagramID, RelationType> generalUpdateService;

  @Override
  public UpdateService<InstagramID, RelationType> create(Stalker stalker) {
    return new UpdateServiceImpl<>(stalker, generalUpdateService);
  }
}
