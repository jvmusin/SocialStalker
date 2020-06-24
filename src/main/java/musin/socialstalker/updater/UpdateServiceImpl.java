package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.api.Id;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

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
