package musin.seeker.vkseeker.telegram;

import musin.seeker.vkseeker.api.SimpleVkUser;
import musin.seeker.vkseeker.api.VkApi;
import musin.seeker.vkseeker.db.model.RelationChange;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ChangesSenderImpl extends TelegramLongPollingBot implements ChangesSender {

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
                "Id: %s\n" +
                "Time: %s\n" +
                "Owner: %s\n" +
                "Target: %s\n" +
                "Change: %s to %s",
                relationChange.getId(),
                relationChange.getTime(),
                owner,
                target,
                relationChange.getPrevType(),
                relationChange.getCurType());
        SendMessage m = new SendMessage(MusinUID, s);
        try {
            execute(m);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
    }
}
