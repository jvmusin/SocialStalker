package musin.seeker.notifier;

import musin.seeker.relation.Relation;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.StringJoiner;

public abstract class MarkdownUpdateNotifier<
    TRelation extends Relation<? extends User, ?>,
    TUpdate extends Update<? extends TRelation>>
    implements UpdateNotifier<TUpdate> {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(ZoneId.systemDefault());

  @Override
  public void notify(TUpdate update) {
    sendMessage(buildMessage(update));
  }

  @Override
  public void notify(List<TUpdate> updates) {
    if (updates.size() >= getMinSizeForABunchOfChanges()) notifyABunchOfChanges(updates);
    else updates.forEach(this::notify);
  }

  private void notifyABunchOfChanges(List<TUpdate> updates) {
    sendMessage(buildMessageForABunchOfUpdates(updates));
  }

  private String userLink(User user) {
    return String.format("[%s](%s)", user.getName(), user.getLink());
  }

  private String buildMessage(TUpdate update) {
    StringJoiner sj = new StringJoiner("\n");
    sj.add("Update id: " + update.getId());
    sj.add("Time: " + update.getTime().format(DATE_TIME_FORMATTER));
    sj.add("Owner: " + userLink(update.getOwner()));
    sj.add("Target: " + userLink(update.getTarget()));
    sj.add("Type: " + update.getWas().getType() + " to " + update.getNow().getType());
    return sj.toString();
  }

  private String buildMessageForABunchOfUpdates(List<TUpdate> updates) {
    IntSummaryStatistics idStats = updates.stream()
        .mapToInt(Update::getId)
        .summaryStatistics();
    TUpdate someUpdate = updates.get(0);
    StringJoiner sj = new StringJoiner("\n");
    sj.add(String.format("Too many updates (%d) for user %s", updates.size(), someUpdate.getOwner()));
    sj.add(String.format("Update ids: %d..%d", idStats.getMin(), idStats.getMax()));
    sj.add(String.format("Type: %s to %s", someUpdate.getWas().getType(), someUpdate.getNow().getType()));
    return sj.toString();
  }

  protected int getMinSizeForABunchOfChanges() {
    return 10;
  }

  protected abstract void sendMessage(@NotNull String message);
}
