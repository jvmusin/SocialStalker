package musin.seeker.notifier;

import lombok.extern.log4j.Log4j2;
import musin.seeker.relation.Relation;
import org.jetbrains.annotations.NotNull;

@Log4j2
public class ConsoleUpdateNotifier<
    TRelation extends Relation<? extends User, ?>,
    TUpdate extends Update<? extends TRelation>> extends MarkdownUpdateNotifier<TRelation, TUpdate> {

  @Override
  protected void sendMessage(@NotNull String message) {
    log.info(message);
  }
}
