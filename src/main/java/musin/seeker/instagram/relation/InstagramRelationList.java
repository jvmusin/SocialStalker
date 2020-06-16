package musin.seeker.instagram.relation;

import musin.seeker.relation.MultiHashMapRelationList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class InstagramRelationList extends MultiHashMapRelationList<InstagramUser, InstagramRelationType, InstagramRelation, InstagramUpdate> {

  public InstagramRelationList(Stream<? extends InstagramRelation> relations) {
    initRelations(relations);
  }

  public InstagramRelationList(List<? extends InstagramUpdate> updates) {
    initUpdates(updates);
  }

  @Override
  protected @NotNull InstagramUpdate createUpdate(@NotNull InstagramUser user, InstagramRelationType was, InstagramRelationType now) {
    return new InstagramUpdateImpl(user, was, now);
  }

  @Override
  protected @NotNull InstagramRelation createRelation(@NotNull InstagramUser user, InstagramRelationType type) {
    return new InstagramRelation(user, type);
  }
}
