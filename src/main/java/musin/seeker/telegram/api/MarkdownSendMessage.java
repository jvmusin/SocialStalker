package musin.seeker.telegram.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MarkdownSendMessage extends SendMessage {
  public MarkdownSendMessage(Long chatId, String text) {
    setChatId(chatId);
    setText(text);
    setParseMode("Markdown");
  }
}
