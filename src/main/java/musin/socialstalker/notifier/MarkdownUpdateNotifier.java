package musin.socialstalker.notifier;

import lombok.RequiredArgsConstructor;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.StringJoiner;

@RequiredArgsConstructor
public class MarkdownUpdateNotifier<TUpdate extends NotifiableUpdate<?>, TRelationType>
    implements UpdateNotifier<TUpdate, TRelationType> {

  private final MessageSender sender;

  @Override
  public void notify(NotifiableUpdate<TRelationType> update) {
    sender.sendMessage(update.toMultilineMarkdownString());
  }

  @Override
  public void notify(List<? extends NotifiableUpdate<TRelationType>> updates) {
    if (updates.size() >= getMinSizeForABunchOfChanges()) notifyABunchOfChanges(updates);
    else updates.forEach(this::notify);
  }

  private void notifyABunchOfChanges(List<? extends NotifiableUpdate<TRelationType>> updates) {
    sender.sendMessage(buildMessageForABunchOfUpdates(updates));
  }

  private String buildMessageForABunchOfUpdates(List<? extends NotifiableUpdate<TRelationType>> updates) {
    IntSummaryStatistics idStats = updates.stream()
        .mapToInt(NotifiableUpdate::getId)
        .summaryStatistics();
    NotifiableUpdate<TRelationType> someUpdate = updates.get(0);
    StringJoiner sj = new StringJoiner("\n");
    sj.add(String.format("Too many updates (%d) for user %s", updates.size(), someUpdate.getTarget()));
    sj.add(String.format("Network: %s", someUpdate.getNetwork()));
    sj.add(String.format("Update ids: %d..%d", idStats.getMin(), idStats.getMax()));
    sj.add(String.format("Type: %s to %s", someUpdate.getWas(), someUpdate.getNow()));
    return sj.toString();
  }

  protected int getMinSizeForABunchOfChanges() {
    return 10;
  }
}
