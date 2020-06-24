package musin.socialstalker.vk.notifier;

import musin.socialstalker.notifier.NotifiableUpdateFactoryImpl;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.api.VkIdFactory;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.relation.VkRelationTypeFactory;
import musin.socialstalker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

@Component
public class VkNotifiableUpdateFactory extends NotifiableUpdateFactoryImpl<VkID> {
  public VkNotifiableUpdateFactory(VkUserFactory userFactory,
                                   VkRelationTypeFactory relationTypeFactory,
                                   VkNetworkProperties networkProperties,
                                   VkIdFactory idFactory) {
    super(userFactory, relationTypeFactory, networkProperties, idFactory);
  }
}
