package musin.seeker.instagram.relation;

import musin.seeker.relation.RelationTypeFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationTypeFactory implements RelationTypeFactory<InstagramRelationType> {
  @Override
  public InstagramRelationType parseNullSafe(String type) {
    return type == null ? null : InstagramRelationType.valueOf(type);
  }
}
