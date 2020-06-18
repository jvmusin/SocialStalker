package musin.seeker.vk.relation;

import lombok.RequiredArgsConstructor;
import musin.seeker.relation.RelationFactory;
import musin.seeker.relation.RelationListFactory;
import musin.seeker.relation.SingleHashMapRelationList;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkRelationListFactory implements RelationListFactory<VkRelationList> {

  private final VkRelationFactory relationFactory;

  @Override
  public VkRelationList create() {
    return new VkRelationListImpl(relationFactory);
  }

  private static class VkRelationListImpl
      extends SingleHashMapRelationList<VkUser, VkRelationType, VkRelation>
      implements VkRelationList {

    public VkRelationListImpl(RelationFactory<?, VkUser, VkRelationType, VkRelation> relationFactory) {
      super(relationFactory);
    }
  }
}
