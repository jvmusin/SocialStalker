package musin.seeker.vkseeker.web;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class TelegramController {

    @RequestMapping("/telegram")
    @SneakyThrows
    public SendMessage telegram(@RequestBody Update update) {
        return new SendMessage(update.getMessage().getChatId(), "yes, i'm here");
    }
}