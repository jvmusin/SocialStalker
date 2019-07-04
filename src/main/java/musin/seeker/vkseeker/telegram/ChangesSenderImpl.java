package musin.seeker.vkseeker.telegram;

import musin.seeker.vkseeker.api.SimpleVkUser;
import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.model.RelationChange;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class ChangesSenderImpl extends TelegramWebhookBot implements ChangesSender {

    private static final long MusinUID = 124275139L;

    private final VkApi vkApi;

    public ChangesSenderImpl(VkApi vkApi) {
        super(buildOptions());
        this.vkApi = vkApi;
    }

    private static DefaultBotOptions buildOptions() {
        DefaultBotOptions opt = new DefaultBotOptions();
        opt.setProxyType(DefaultBotOptions.ProxyType.HTTP);
        opt.setProxyHost("191.252.185.161");
        opt.setProxyPort(8090);
        return opt;
    }

    @Override
    public String getBotUsername() {
        return "VkSeekerBot";
    }

    @Override
    public String getBotToken() {
        return "809103789:AAFReBbzwDxrpVCFLJ2JZv2EKcaaAJuqP6o";
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
            SendMessage m = new SendMessage(MusinUID, s);
            m.setParseMode("HTML");
            execute(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String userLink(SimpleVkUser user) {
        return formatLink(user.toString(), user.link());
    }

    private String formatLink(String name, String url) {
        return String.format("<a href='%s'>%s</a>", url, name);
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        return new SendMessage(update.getMessage().getChatId(), "Hello, I'm not sleeping.");
    }

    @Override
    public String getBotPath() {
        return getBotUsername();
    }
}
