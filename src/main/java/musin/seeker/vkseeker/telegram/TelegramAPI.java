package musin.seeker.vkseeker.telegram;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.util.List;

@Component
@AllArgsConstructor
public class TelegramAPI implements InitializingBean {

    private final List<LongPollingBot> bots;

    @Override
    public void afterPropertiesSet() throws Exception {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        for (LongPollingBot bot : bots) telegramBotsApi.registerBot(bot);
    }
}