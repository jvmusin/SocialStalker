package musin.seeker.vkseeker;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.RelationChangeService;
import musin.seeker.vkseeker.db.SeekerService;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.db.model.Seeker;
import musin.seeker.vkseeker.telegram.ChangesNotifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static musin.seeker.vkseeker.db.model.RelationType.FOLLOWER;
import static musin.seeker.vkseeker.db.model.RelationType.FRIEND;

@Service
@AllArgsConstructor
public class ScheduledSeeker {

  private final SeekerService seekerService;
  private final RelationChangeService relationChangeService;
  private final VkApi vkApi;
  private final ChangesNotifier changesNotifier;

  @Scheduled(fixedDelay = 1000L * 60 * 10)
  public void run() {
    seekerService.findAll().stream()
        .parallel()
        .map(Seeker::getOwner)
        .forEach(this::run);
  }

  private void run(int owner) {
    List<RelationChange> changes = relationChangeService.findAllByOwner(owner);
    RelationList was = new RelationList(owner);
    was.applyChanges(changes);

    List<Integer> friends = vkApi.loadFriends(owner);
    List<Integer> followers = vkApi.loadFollowers(owner);
    RelationList now = new RelationList(owner);
    friends.forEach(id -> now.applyChange(RelationChange.builder().curType(FRIEND).target(id).build()));
    followers.forEach(id -> now.applyChange(RelationChange.builder().curType(FOLLOWER).target(id).build()));

    List<RelationChange> difference = was.getDifference(now);
    relationChangeService.saveAll(difference);
    changesNotifier.notify(difference);
  }
}