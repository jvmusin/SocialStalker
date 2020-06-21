package musin.seeker.notifier;

import musin.seeker.relation.Update;
import musin.seeker.relation.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.StringJoiner;

public interface NotifiableUpdate<TUser extends User<?>, TRelationType>
    extends Update<TUser, TRelationType> {

  String getNetwork();

  Integer getId();

  TUser getOwner();

  LocalDateTime getTime();

  default String toMultilineMarkdownString() {
    DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    StringJoiner sj = new StringJoiner(System.lineSeparator());
    sj.add("Update id: " + getId());
    sj.add("Network: " + getNetwork());
    sj.add("Time: " + getTime().format(fmt));
    sj.add("Owner: " + getOwner().getMarkdownLink());
    sj.add("Suspected: " + getSuspected().getMarkdownLink());
    sj.add("Type: " + getWas() + " to " + getNow());
    return sj.toString();
  }
}
