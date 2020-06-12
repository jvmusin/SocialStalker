package musin.seeker.vkseeker.telegram.notifier;

import lombok.RequiredArgsConstructor;
import musin.seeker.vkseeker.notifier.MarkdownUpdateNotifier;
import musin.seeker.vkseeker.notifier.Update;
import musin.seeker.vkseeker.notifier.User;
import musin.seeker.vkseeker.relation.Relation;
import musin.seeker.vkseeker.telegram.api.TelegramMessageSender;
import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@RequiredArgsConstructor
public class TelegramUpdateNotifier<
    TRelation extends Relation<? extends User, ?>,
    TUpdate extends Update<? extends TRelation>> extends MarkdownUpdateNotifier<TRelation, TUpdate> {

  private final TelegramMessageSender sender;

  @Override
  protected void sendMessage(@NotNull String message) {
    sender.sendMessage(message, false);
  }

  @PostConstruct
  public void init() {
    sender.sendMessage("I'm alive", false);
  }

  @PreDestroy
  public void shutdown() {
    sender.sendMessage("I'm shutting down", true);
  }
}
