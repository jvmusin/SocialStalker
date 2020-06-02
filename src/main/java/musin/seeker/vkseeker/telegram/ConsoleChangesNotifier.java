package musin.seeker.vkseeker.telegram;

import lombok.AllArgsConstructor;
import musin.seeker.vkseeker.api.SimpleVkUser;
import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.model.RelationChange;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.IntSummaryStatistics;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsoleChangesNotifier implements ChangesNotifier {

  private final VkApi vkApi;

  private static String userLink(SimpleVkUser user) {
    return String.format("[%s](%s)", user.toString(), user.link());
  }

  @Override
  public void notify(RelationChange relationChange) {
    SimpleVkUser owner = vkApi.loadUser(relationChange.getOwner());
    SimpleVkUser target = vkApi.loadUser(relationChange.getTarget());
    String message = String.format("" +
            "Change id: %s\n" +
            "Time: %s\n" +
            "Owner: %s\n" +
            "Target: %s\n" +
            "Change: %s to %s",
        relationChange.getId(),
        relationChange.getTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(ZoneId.systemDefault())),
        userLink(owner),
        userLink(target),
        relationChange.getPrevType(),
        relationChange.getCurType());
    try {
      sendMessage(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void notify(List<RelationChange> relationChanges) {
    if (relationChanges.size() > 10) {
      RelationChange relationChange = relationChanges.get(0);
      SimpleVkUser owner = vkApi.loadUser(relationChange.getOwner());
      IntSummaryStatistics idStats = relationChanges.stream()
          .mapToInt(RelationChange::getId)
          .summaryStatistics();
      String format = "Too many changes (%d) for user %s\n" +
          "Change ids: %d..%d\n" +
          "Type: %s to %s";
      String message = String.format(format,
          idStats.getCount(), owner, idStats.getMin(), idStats.getMax(),
          relationChange.getPrevType(), relationChange.getCurType());
      try {
        sendMessage(message);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      relationChanges.forEach(this::notify);
    }
  }

  @Override
  public void sendMessage(String message) {
    LogManager.getLogger(ConsoleChangesNotifier.class).info(message);
  }

  @Override
  public void sendMessage(String message, long uid) {
    LogManager.getLogger(ConsoleChangesNotifier.class).info("Message to " + uid);
    LogManager.getLogger(ConsoleChangesNotifier.class).info(message);
  }
}
