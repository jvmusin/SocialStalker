package musin.socialstalker.instagram.notifier;

import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.instagram.relation.InstagramUpdate;
import musin.socialstalker.instagram.relation.InstagramUser;
import musin.socialstalker.notifier.NotifiableUpdate;

public interface InstagramNotifiableUpdate
    extends NotifiableUpdate<InstagramUser, InstagramRelationType>, InstagramUpdate {
}
