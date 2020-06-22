package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class UpdateServiceImpl<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TUpdate extends Update<TUser, TRelationType>,
    TRelationList extends RelationList<TUser, TRelationType>,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>>
    implements UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> {

  private final Stalker stalker;
  private final GeneralUpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> generalUpdateService;

  @Override
  public List<TNotifiableUpdate> saveAll(List<? extends TUpdate> updates, ID target) {
    return generalUpdateService.saveAll(stalker, updates, target);
  }

  @Override
  public CompletableFuture<TRelationList> buildList(ID target) {
    return generalUpdateService.buildList(stalker, target);
  }
}
