package musin.seeker.instagram.relation;

import musin.seeker.relation.TreeMapRelationList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class InstagramRelationList extends TreeMapRelationList<InstagramUser, InstagramRelation, InstagramUpdateImpl> {

  public InstagramRelationList(List<? extends InstagramUpdate> updates) {
    updates.forEach(u -> add(u.getNow()));
  }

  public InstagramRelationList(Stream<? extends InstagramRelation> relations) {
    relations.forEach(this::add);
  }

  @Override
  protected InstagramUpdateImpl createUpdate(@NotNull InstagramUser target, InstagramRelation was, InstagramRelation now) {
    return new InstagramUpdateImpl(target, was, now);
  }
}
