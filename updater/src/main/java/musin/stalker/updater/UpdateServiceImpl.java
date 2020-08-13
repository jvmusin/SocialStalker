package musin.stalker.updater;

import lombok.RequiredArgsConstructor;
import musin.stalker.api.Id;
import musin.stalker.db.model.Stalker;
import musin.stalker.notifier.NotifiableUpdate;
import musin.stalker.relation.Update;
import musin.stalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {

  private final Stalker stalker;
  private final GeneralUpdateService generalUpdateService;

  @Override
  public List<NotifiableUpdate> saveAll(List<Update> updates, Id target) {
    return generalUpdateService.saveAll(stalker, updates, target);
  }

  @Override
  public CompletableFuture<RelationList> buildList(Id target) {
    return generalUpdateService.buildList(stalker, target);
  }
}
