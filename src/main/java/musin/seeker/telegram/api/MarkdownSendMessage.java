package musin.seeker.telegram.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MarkdownSendMessage extends SendMessage {
  public MarkdownSendMessage(Long receiverUid, String text) {
    setChatId(receiverUid);
    setText(text);
    setParseMode("Markdown");
  }
}
