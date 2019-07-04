package musin.seeker.vkseeker.web;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramController {

    @RequestMapping("/telegram")
    @SneakyThrows
    public String telegram() {
        return "yes i'm here";
    }
}