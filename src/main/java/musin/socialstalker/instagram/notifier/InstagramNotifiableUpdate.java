package musin.socialstalker.instagram.notifier;

import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.instagram.relation.InstagramUpdate;
import musin.socialstalker.notifier.NotifiableUpdate;

public interface InstagramNotifiableUpdate extends NotifiableUpdate<InstagramRelationType>, InstagramUpdate {
}
