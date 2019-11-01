package musin.seeker.vkseeker.telegram;

import musin.seeker.vkseeker.api.SimpleVkUser;
import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.model.RelationChange;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.IntSummaryStatistics;
import java.util.List;

@Component
public class ChangesNotifierImpl implements ChangesNotifier {

    private static final long MUSIN_UID = 124275139;
    private static final String BOT_TOKEN = "809103789:AAFReBbzwDxrpVCFLJ2JZv2EKcaaAJuqP6o";

    private final VkApi vkApi;
    private final RestTemplate restTemplate;

    public ChangesNotifierImpl(VkApi vkApi, RestTemplate restTemplate) {
        this.vkApi = vkApi;
        this.restTemplate = restTemplate;
    }

    private String getMethodUrl(String methodName) {
        return String.format("https://api.telegram.org/bot%s/%s", BOT_TOKEN, methodName);
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
        sendMessage(message, MUSIN_UID);
    }

    @Override
    public void sendMessage(String message, long uid) {
        SendMessage m = new SendMessage(uid, message);
        m.setParseMode("Markdown");
        restTemplate.postForEntity(getMethodUrl("sendMessage"), m, Message.class);
    }

    private String userLink(SimpleVkUser user) {
        return formatLink(user.toString(), user.link());
    }

    private String formatLink(String name, String url) {
        return String.format("[%s](%s)", name, url);
    }
}
