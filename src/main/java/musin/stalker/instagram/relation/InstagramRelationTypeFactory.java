package musin.stalker.instagram.relation;

import musin.stalker.relation.RelationTypeFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationTypeFactory implements RelationTypeFactory {
  @Override
  public InstagramRelationType parseNullSafe(String type) {
    return type == null ? null : InstagramRelationType.valueOf(type);
  }
}
