package musin.stalker.instagram.notifier;

import musin.stalker.instagram.api.InstagramID;
import musin.stalker.instagram.api.InstagramIdFactory;
import musin.stalker.instagram.config.InstagramNetworkProperties;
import musin.stalker.instagram.relation.InstagramRelationTypeFactory;
import musin.stalker.instagram.relation.InstagramUserFactory;
import musin.stalker.notifier.NotifiableUpdateFactoryImpl;
import org.springframework.stereotype.Component;

@Component
public class InstagramNotifiableUpdateFactory extends NotifiableUpdateFactoryImpl<InstagramID> {
  public InstagramNotifiableUpdateFactory(InstagramUserFactory userFactory,
                                          InstagramRelationTypeFactory relationTypeFactory,
                                          InstagramNetworkProperties networkProperties,
                                          InstagramIdFactory idFactory) {
    super(userFactory, relationTypeFactory, networkProperties, idFactory);
  }
}
