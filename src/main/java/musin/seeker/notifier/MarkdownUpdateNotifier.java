package musin.seeker.notifier;

import musin.seeker.relation.User;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.StringJoiner;

public abstract class MarkdownUpdateNotifier<
    TUpdate extends NotifiableUpdate<? extends User<?>, ?>>
    implements UpdateNotifier<TUpdate> {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(ZoneId.systemDefault());

  @Override
  public void notify(TUpdate update) {
    sendMessage(buildMessage(update));
  }

  @Override
  public void notify(List<? extends TUpdate> updates) {
    if (updates.size() >= getMinSizeForABunchOfChanges()) notifyABunchOfChanges(updates);
    else updates.forEach(this::notify);
  }

  private void notifyABunchOfChanges(List<? extends TUpdate> updates) {
    sendMessage(buildMessageForABunchOfUpdates(updates));
  }

  private String userLink(User<?> user) {
    return String.format("[%s](%s)", user.getFullyQualifiedName(), user.getLink());
  }

  private String buildMessage(TUpdate update) {
    StringJoiner sj = new StringJoiner("\n");
    sj.add("Update id: " + update.getId());
    sj.add("Resource: " + update.getResource());
    sj.add("Time: " + update.getTime().format(DATE_TIME_FORMATTER));
    sj.add("Owner: " + userLink(update.getOwner()));
    sj.add("Target: " + userLink(update.getTarget()));
    sj.add("Type: " + update.getWas() + " to " + update.getNow());
    return sj.toString();
  }

  private String buildMessageForABunchOfUpdates(List<? extends TUpdate> updates) {
    IntSummaryStatistics idStats = updates.stream()
        .mapToInt(NotifiableUpdate::getId)
        .summaryStatistics();
    TUpdate someUpdate = updates.get(0);
    StringJoiner sj = new StringJoiner("\n");
    sj.add(String.format("Too many updates (%d) for user %s", updates.size(), someUpdate.getOwner()));
    sj.add("Resource: " + someUpdate.getResource());
    sj.add(String.format("Update ids: %d..%d", idStats.getMin(), idStats.getMax()));
    sj.add(String.format("Type: %s to %s", someUpdate.getWas(), someUpdate.getNow()));
    return sj.toString();
  }

  @SuppressWarnings("SameReturnValue")
  protected int getMinSizeForABunchOfChanges() {
    return 10;
  }

  protected abstract void sendMessage(@NotNull String message);
}
