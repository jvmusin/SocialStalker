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

@Component
public class ChangesNotifierImpl implements ChangesNotifier {

    private static final long MUSIN_UID = 124275139L;
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
    public void sendMessage(RelationChange relationChange) {
        SimpleVkUser owner = vkApi.loadUser(relationChange.getOwner());
        SimpleVkUser target = vkApi.loadUser(relationChange.getTarget());
        String s = String.format("" +
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
            SendMessage m = new SendMessage(MUSIN_UID, s);
            m.setParseMode("Markdown");
            restTemplate.postForEntity(getMethodUrl("sendMessage"), m, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String userLink(SimpleVkUser user) {
        return formatLink(user.toString(), user.link());
    }

    private String formatLink(String name, String url) {
        return String.format("[%s](%s)", name, url);
    }
}
