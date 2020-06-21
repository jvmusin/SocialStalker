package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.list.RelationList;

public interface UpdateServiceFactory<
    ID,
    TUpdate extends Update<? extends User<ID>, ?>,
    TRelationList extends RelationList<? extends User<ID>, ?>,
    TNotifiableUpdate extends NotifiableUpdate<? extends User<ID>, ?>> {

  UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> create(Stalker stalker);
}
