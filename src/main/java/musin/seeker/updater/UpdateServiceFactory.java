package musin.seeker.updater;

import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.relation.Update;
import musin.seeker.relation.User;
import musin.seeker.relation.list.RelationList;

public interface UpdateServiceFactory<
    ID,
    TUpdate extends Update<? extends User<ID>, ?>,
    TRelationList extends RelationList<? extends User<ID>, ?>,
    TNotifiableUpdate extends NotifiableUpdate<? extends User<ID>, ?>> {

  UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> create(Stalker stalker);
}
