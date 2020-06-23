package musin.socialstalker.instagram.relation;

import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.RelationTypeFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationTypeFactory implements RelationTypeFactory<RelationType> {
  @Override
  public InstagramRelationType parseNullSafe(String type) {
    return type == null ? null : InstagramRelationType.valueOf(type);
  }
}
