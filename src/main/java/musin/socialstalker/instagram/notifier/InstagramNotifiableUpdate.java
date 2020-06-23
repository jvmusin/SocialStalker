package musin.socialstalker.instagram.notifier;

import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.instagram.relation.InstagramUpdate;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.RelationType;

public interface InstagramNotifiableUpdate extends NotifiableUpdate<RelationType>, InstagramUpdate {
}
