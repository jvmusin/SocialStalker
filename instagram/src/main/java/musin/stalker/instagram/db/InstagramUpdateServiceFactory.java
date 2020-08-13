package musin.stalker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.stalker.db.model.Stalker;
import musin.stalker.updater.UpdateService;
import musin.stalker.updater.UpdateServiceFactory;
import musin.stalker.updater.UpdateServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramUpdateServiceFactory implements UpdateServiceFactory {
  private final InstagramGeneralUpdateService generalUpdateService;

  @Override
  public UpdateService create(Stalker stalker) {
    return new UpdateServiceImpl(stalker, generalUpdateService);
  }
}
