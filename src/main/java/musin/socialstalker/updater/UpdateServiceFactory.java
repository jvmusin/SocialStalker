package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

public interface UpdateServiceFactory<
    ID,
    TUpdate extends Update<?>,
    TRelationList extends RelationList<?>,
    TNotifiableUpdate extends NotifiableUpdate<?>> {

  UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> create(Stalker stalker);
}
