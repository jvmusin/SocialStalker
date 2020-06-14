package musin.seeker.relation;

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

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("When empty should")
interface EmptyRelationListTests<
    TRelationList extends RelationList<TestUser, TestRelation, TestRelationUpdate>> {

  TRelationList createList();

  default TRelationList createList(List<TestRelation> relations) {
    TRelationList res = createList();
    relations.forEach(r -> res.apply(new TestRelationUpdate(r.getUser(), null, r)));
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
  default void return_empty_users() {
    assertThat(createList().users().collect(toList()), empty());
  }

  @Test
  default void return_empty_relations() {
    assertThat(createList().relations().collect(toList()), empty());
  }

  @Test
  default void return_empty_updates_on_empty_list() {
    assertThat(createList().updates(createList()).collect(toList()), empty());
  }

  @Test
  default void return_null_on_getSingleRelation_call() {
    assertThat(createList().getSingleRelation(new TestUser("john")), nullValue());
  }

  @Test
  default void add_and_return_new_relations_with_different_users() {
    TRelationList list = createList();
    list.apply(someRelations().get(0).asAdd());
    list.apply(someRelations().get(1).asAdd());
    assertThat(list.users().collect(toList()), hasSize(2));
    assertThat(list.relations().collect(toList()), hasSize(2));
    assertEquals(someRelations().get(0), list.getSingleRelation(someRelations().get(0).getUser()));
    assertEquals(someRelations().get(1), list.getSingleRelation(someRelations().get(1).getUser()));
    assertEquals(someRelations().get(0), list.getSingleRelation(someRelations().get(0).getUser()));
    assertThat(list.getSingleRelation(new TestUser("new")), nullValue());
  }

  @Test
  default void return_correct_updates_on_not_empty_list() {
    TRelationList a = createList();
    TRelationList b = createList(someRelations());
    List<TestRelationUpdate> result = a.updates(b).collect(toList());
    TestRelationUpdate[] expected = someRelations().stream()
        .map(TestRelation::asAdd)
        .toArray(TestRelationUpdate[]::new);
    assertThat(result, containsInAnyOrder(expected));
  }
}