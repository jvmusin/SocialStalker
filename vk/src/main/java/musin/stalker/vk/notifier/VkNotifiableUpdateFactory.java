package musin.stalker.vk.notifier;

import musin.stalker.notifier.NotifiableUpdateFactoryImpl;
import musin.stalker.vk.api.VkID;
import musin.stalker.vk.api.VkIdFactory;
import musin.stalker.vk.config.VkNetworkProperties;
import musin.stalker.vk.relation.VkRelationTypeFactory;
import musin.stalker.vk.relation.VkUserFactory;
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
