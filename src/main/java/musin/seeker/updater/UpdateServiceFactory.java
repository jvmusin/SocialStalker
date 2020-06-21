package musin.seeker.updater;

import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.relation.Update;
import musin.seeker.relation.list.RelationList;

public interface UpdateServiceFactory<
    ID,
    TUpdate extends Update<?, ?>,
    TRelationList extends RelationList<?, ?>,
    TNotifiableUpdate extends NotifiableUpdate<?, ?>> {

  UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> create(Stalker stalker);
}
