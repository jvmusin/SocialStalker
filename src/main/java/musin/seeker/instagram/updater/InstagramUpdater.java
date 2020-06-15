package musin.seeker.instagram.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.instagram.db.InstagramRelationUpdateService;
import musin.seeker.instagram.db.InstagramSeekerService;
import musin.seeker.instagram.notifier.InstagramUpdateNotifier;
import musin.seeker.instagram.relation.InstagramRelationList;
import musin.seeker.instagram.relation.InstagramRelationListPuller;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstagramUpdater implements Runnable {

  private final InstagramSeekerService instagramSeekerService;
  private final InstagramRelationUpdateService instagramRelationUpdateService;
  private final List<InstagramUpdateNotifier> notifiers;
  private final InstagramRelationListPuller instagramRelationListPuller;
  private final TaskExecutor taskExecutor;

  @Override
  public void run() {
    instagramSeekerService.findAll().forEach(s -> taskExecutor.execute(() -> run(s.getOwner())));
  }

  private void run(long owner) {
    CompletableFuture<InstagramRelationList> was = instagramRelationUpdateService.buildList(owner);
    CompletableFuture<InstagramRelationList> now = instagramRelationListPuller.pull(owner);
    was.thenCombine(now, InstagramRelationList::updates)
        .thenApply(u -> u.collect(toList()))
        .thenApply(updates -> instagramRelationUpdateService.saveAll(updates, owner))
        .thenAccept(updates -> notifiers.forEach(notifier -> notifier.notify(updates)));
  }
}
