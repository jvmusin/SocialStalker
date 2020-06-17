package musin.seeker.vk.relation;

import lombok.RequiredArgsConstructor;
import musin.seeker.relation.RelationFactory;
import musin.seeker.relation.RelationListFactory;
import musin.seeker.relation.SingleHashMapRelationList;
import musin.seeker.relation.UpdateFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkRelationListFactory implements RelationListFactory<VkRelationList> {

  private final VkUpdateFactory updateFactory;
  private final VkRelationFactory relationFactory;

  @Override
  public VkRelationList create() {
    return new VkRelationListImpl(updateFactory, relationFactory);
  }

  private static class VkRelationListImpl
      extends SingleHashMapRelationList<VkUser, VkRelationType, VkRelation, VkUpdate>
      implements VkRelationList {

    public VkRelationListImpl(UpdateFactory<VkUser, VkRelationType, VkUpdate> updateFactory,
                              RelationFactory<?, VkUser, VkRelationType, VkRelation> relationFactory) {
      super(updateFactory, relationFactory);
    }
  }
}
