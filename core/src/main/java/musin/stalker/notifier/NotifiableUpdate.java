package musin.stalker.notifier;

import musin.stalker.relation.Update;
import musin.stalker.relation.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.StringJoiner;

public interface NotifiableUpdate extends Update {
  Integer getId();

  String getNetwork();

  User getTarget();

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
