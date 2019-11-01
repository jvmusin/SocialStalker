package musin.seeker.vkseeker;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.db.RelationChangeService;
import musin.seeker.vkseeker.db.SeekerService;
import musin.seeker.vkseeker.telegram.ChangesNotifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static musin.seeker.vkseeker.db.model.RelationType.*;

@Service
@AllArgsConstructor
public class ScheduledSeeker {

    private final SeekerService seekerService;
    private final RelationChangeService relationChangeService;
    private final VkApi vkApi;
    private final ChangesNotifier changesNotifier;

    @Scheduled(fixedDelay = 1000)
    public void run() {
        seekerService.findAll().forEach(seeker -> run(seeker.getOwner()));
    }

    private void run(int owner) {
        final List<RelationChange> changes = relationChangeService.findAllByOwner(owner);
        final RelationList was = new RelationList(owner);
        was.applyChanges(changes);

        final List<Integer> friends = vkApi.loadFriends(owner);
        final List<Integer> followers = vkApi.loadFollowers(owner);
        final RelationList now = new RelationList(owner);
        friends.forEach(id -> now.applyChange(RelationChange.builder().curType(FRIEND).target(id).build()));
        followers.forEach(id -> now.applyChange(RelationChange.builder().curType(FOLLOWER).target(id).build()));

        final List<RelationChange> difference = was.getDifference(now);
        relationChangeService.saveAll(difference);
        changesNotifier.notify(difference);
    }
}