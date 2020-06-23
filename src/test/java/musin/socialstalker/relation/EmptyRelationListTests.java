package musin.socialstalker.relation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("When empty should")
interface EmptyRelationListTests<TRelationList extends TestRelationList> {

  TRelationList createList();

  default TRelationList createList(List<TestRelation> relations) {
    TRelationList res = createList();
    relations.forEach(r -> res.apply(r.asAdd()));
    return res;
  }

  default List<TestRelation> someRelations() {
    return asList(
        new TestRelation("a", "Friend"),
        new TestRelation("b", "Follower"),
        new TestRelation("c", "Fan"),
        new TestRelation("d", "Friend")
    );
  }

  @Test
  default void have_no_users() {
    assertThat(createList().users().collect(toList()), empty());
  }

  @Test
  default void have_no_relations() {
    assertThat(createList().relations().collect(toList()), empty());
  }

  @Test
  default void have_no_relation_types_for_any_user() {
    assertThat(createList().getAllRelationTypes(new TestUser("user")), empty());
  }

  @Test
  default void have_no_updates_on_empty_list() {
    assertThat(createList().updates(createList()).collect(toList()), empty());
  }

  @Test
  default void return_null_on_getRelationType_call() {
    assertThat(createList().getRelationType(new TestUser("john")), nullValue());
  }

  @Test
  default void add_and_return_new_relations_with_different_users() {
    TRelationList list = createList();
    list.apply(someRelations().get(0).asAdd());
    list.apply(someRelations().get(1).asAdd());
    assertThat(list.users().collect(toList()), hasSize(2));
    assertThat(list.relations().collect(toList()), hasSize(2));
    assertEquals(someRelations().get(0).getType(), list.getRelationType(someRelations().get(0).getUser()));
    assertEquals(someRelations().get(1).getType(), list.getRelationType(someRelations().get(1).getUser()));
    assertEquals(someRelations().get(0).getType(), list.getRelationType(someRelations().get(0).getUser()));
    assertThat(list.getRelationType(new TestUser("new")), nullValue());
  }

  @Test
  default void return_correct_updates_on_not_empty_list() {
    TRelationList a = createList();
    TRelationList b = createList(someRelations());
    List<Update<TestRelationType>> result = a.updates(b).collect(toList());
    TestUpdate[] expected = someRelations().stream()
        .map(TestRelation::asAdd)
        .toArray(TestUpdate[]::new);
    assertThat(result, containsInAnyOrder(expected));
  }

  @Test
  default void fail_when_suspected_is_null() {
    TestUpdate update = new TestUpdate(null, null, new TestRelationType("friend"));
    assertThrows(IllegalArgumentException.class, () -> createList().apply(update));
  }

  @Test
  default void fail_when_was_and_now_types_are_same_and_null() {
    TestUpdate update = new TestUpdate("user", null, null);
    assertThrows(IllegalArgumentException.class, () -> createList().apply(update));
  }

  @Test
  default void fail_when_was_and_now_types_are_same() {
    String user = "user";
    String friend = "friend";
    TestUpdate update = new TestUpdate(user, friend, friend);
    TRelationList list = createList();
    list.apply(new TestUpdate(user, null, friend));
    assertThrows(IllegalArgumentException.class, () -> list.apply(update));
  }
}