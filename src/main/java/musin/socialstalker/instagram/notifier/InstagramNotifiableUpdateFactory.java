package musin.socialstalker.instagram.notifier;

import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.api.InstagramIdFactory;
import musin.socialstalker.instagram.config.InstagramNetworkProperties;
import musin.socialstalker.instagram.relation.InstagramRelationTypeFactory;
import musin.socialstalker.instagram.relation.InstagramUserFactory;
import musin.socialstalker.notifier.NotifiableUpdateFactoryImpl;
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
