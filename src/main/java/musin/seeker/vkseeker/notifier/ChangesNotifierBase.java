package musin.seeker.vkseeker.notifier;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.vk.SimpleVkUser;
import musin.seeker.vkseeker.vk.VkApi;
import musin.seeker.vkseeker.db.model.RelationChange;
import org.springframework.scheduling.annotation.Async;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.StringJoiner;

import static java.util.Arrays.asList;

@AllArgsConstructor
public abstract class ChangesNotifierBase implements ChangesNotifier {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(ZoneId.systemDefault());

  protected final VkApi vkApi;

  private static String userLink(SimpleVkUser user) {
    return String.format("[%s](%s)", user.toString(), user.link());
  }

  private static String buildMessage(RelationChange change, SimpleVkUser owner, SimpleVkUser target) {
    StringJoiner sj = new StringJoiner("\n");
    sj.add("Change id: " + change.getId());
    sj.add("Time: " + change.getTime().format(DATE_TIME_FORMATTER));
    sj.add("Owner: " + userLink(owner));
    sj.add("Target: " + userLink(target));
    sj.add("Change: " + change.getPrevType() + " to " + change.getCurType());
    return sj.toString();
  }

  private static String buildMessageForABunchOfChanges(List<RelationChange> changes, SimpleVkUser owner) {
    IntSummaryStatistics idStats = changes.stream()
        .mapToInt(RelationChange::getId)
        .summaryStatistics();
    RelationChange anyChange = changes.get(0);
    StringJoiner sj = new StringJoiner("\n");
    sj.add(String.format("Too many changes (%d) for user %s", changes.size(), owner));
    sj.add(String.format("Change ids: %d..%d", idStats.getMin(), idStats.getMax()));
    sj.add(String.format("Type: %s to %s", anyChange.getPrevType(), anyChange.getCurType()));
    return sj.toString();
  }

  @Override
  @Async
  public void notify(RelationChange change) {
    vkApi.loadUsersAsync(asList(change.getOwner(), change.getTarget()))
        .thenApply(u -> buildMessage(change, u.get(0), u.get(1)))
        .thenAccept(this::sendMessage);
  }

  @Override
  @Async
  public void notify(List<RelationChange> changes) {
    if (changes.size() > 10) notifyABunchOfChanges(changes);
    else changes.forEach(this::notify);
  }

  private void notifyABunchOfChanges(List<RelationChange> changes) {
    vkApi.loadUserAsync(changes.get(0).getOwner())
        .thenApply(owner -> buildMessageForABunchOfChanges(changes, owner))
        .thenAccept(this::sendMessage);
  }

  protected abstract void sendMessage(String message);
}
