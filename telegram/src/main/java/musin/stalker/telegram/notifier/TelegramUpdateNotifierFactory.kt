package musin.stalker.telegram.notifier

import musin.stalker.db.model.Stalker
import musin.stalker.notifier.MarkdownUpdateNotifier
import musin.stalker.notifier.MessageSenderFactory
import musin.stalker.notifier.UpdateNotifierFactory
import org.springframework.stereotype.Component

@Component
class TelegramUpdateNotifierFactory(private val messageSenderFactory: MessageSenderFactory) : UpdateNotifierFactory {
    override fun create(stalker: Stalker) = MarkdownUpdateNotifier(messageSenderFactory.create(stalker))
}
