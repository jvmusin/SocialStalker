package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class UpdateServiceImpl<ID, TRelationType> implements UpdateService<ID, TRelationType> {

  private final Stalker stalker;
  private final GeneralUpdateService<? super ID, TRelationType> generalUpdateService;

  @Override
  public List<? extends NotifiableUpdate<? extends TRelationType>> saveAll(List<? extends Update<?>> updates, ID target) {
    return generalUpdateService.saveAll(stalker, updates, target);
  }

  @Override
  public CompletableFuture<? extends RelationList<TRelationType>> buildList(ID target) {
    return generalUpdateService.buildList(stalker, target);
  }
}
