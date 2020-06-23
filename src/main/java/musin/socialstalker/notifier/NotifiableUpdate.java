package musin.socialstalker.notifier;

import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.StringJoiner;

public interface NotifiableUpdate<TRelationType> extends Update<TRelationType> {
  Integer getId();

  String getNetwork();

  User<?> getTarget();

  LocalDateTime getTime();

  default String toMultilineMarkdownString() {
    DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    StringJoiner sj = new StringJoiner(System.lineSeparator());
    sj.add("Update id: " + getId());
    sj.add("Network: " + getNetwork());
    sj.add("Time: " + getTime().format(fmt));
    sj.add("Target: " + getTarget().getMarkdownLink());
    sj.add("Suspected: " + getSuspected().getMarkdownLink());
    sj.add("Type: " + getWas() + " to " + getNow());
    return sj.toString();
  }
}
