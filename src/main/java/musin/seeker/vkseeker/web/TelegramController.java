package musin.seeker.vkseeker.web;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import musin.seeker.vkseeker.telegram.ChangesNotifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@RestController
@AllArgsConstructor
public class TelegramController {

    private final ChangesNotifier changesNotifier;

    @RequestMapping("/telegram")
    @SneakyThrows
    public void ping(@RequestBody Update update) {
        changesNotifier.sendMessage("yes, i'm here", update.getMessage().getChatId());
    }

    @PostConstruct
    public void init() {
        changesNotifier.sendMessage("I'm alive");
    }

    @PreDestroy
    public void shutdown() {
        changesNotifier.sendMessage("I'm shutting down");
    }
}