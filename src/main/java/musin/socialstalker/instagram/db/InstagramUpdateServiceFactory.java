package musin.socialstalker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.updater.GeneralUpdateService;
import musin.socialstalker.updater.UpdateService;
import musin.socialstalker.updater.UpdateServiceFactory;
import musin.socialstalker.updater.UpdateServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramUpdateServiceFactory implements UpdateServiceFactory {

  private final GeneralUpdateService generalUpdateService;

  @Override
  public UpdateService create(Stalker stalker) {
    return new UpdateServiceImpl(stalker, generalUpdateService);
  }
}
