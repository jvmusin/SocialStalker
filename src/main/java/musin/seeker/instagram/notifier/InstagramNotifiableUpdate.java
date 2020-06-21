package musin.seeker.instagram.notifier;

import musin.seeker.instagram.relation.InstagramRelationType;
import musin.seeker.instagram.relation.InstagramUpdate;
import musin.seeker.instagram.relation.InstagramUser;
import musin.seeker.notifier.NotifiableUpdate;

public interface InstagramNotifiableUpdate
    extends NotifiableUpdate<InstagramUser, InstagramRelationType>, InstagramUpdate {
}
