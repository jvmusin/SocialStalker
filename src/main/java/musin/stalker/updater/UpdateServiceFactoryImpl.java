package musin.stalker.updater;

import lombok.RequiredArgsConstructor;
import musin.stalker.db.model.Stalker;

@RequiredArgsConstructor
public class UpdateServiceFactoryImpl implements UpdateServiceFactory {
  private final GeneralUpdateService generalUpdateService;

  @Override
  public UpdateService create(Stalker stalker) {
    return new UpdateServiceImpl(stalker, generalUpdateService);
  }
}
