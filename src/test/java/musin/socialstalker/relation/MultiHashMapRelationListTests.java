package musin.socialstalker.relation;

import org.junit.jupiter.api.*;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("MultiHashMapRelationList should")
public class MultiHashMapRelationListTests implements EmptyRelationListTests<TestMultiHashMapRelationList> {
  @Override
  public TestMultiHashMapRelationList createList() {
    return new TestMultiHashMapRelationList();
  }

  @Test
  void return_correct_relation_types() {
    TestMultiHashMapRelationList list = createList();
    list.apply(new TestUpdate("user1", null, "friend"));
    list.apply(new TestUpdate("user2", null, "follower"));
    list.apply(new TestUpdate("user3", null, "fan"));
    list.apply(new TestUpdate("user1", null, "nobody"));
    list.apply(new TestUpdate("user2", "follower", null));
    assertThat(list.getAllRelationTypes(new TestUser("user1")), containsInAnyOrder(
        new TestRelationType("friend"),
        new TestRelationType("nobody")
    ));
    assertEquals(list.getAllRelationTypes(new TestUser("user2")), emptySet());
    assertEquals(list.getAllRelationTypes(new TestUser("user3")), singleton(new TestRelationType("fan")));
    assertEquals(list.getAllRelationTypes(new TestUser("user4")), emptySet());
    assertEquals(list.getAllRelationTypes(new TestUser("user3")), singleton(new TestRelationType("fan")));
    assertThat(list.getAllRelationTypes(new TestUser("user1")), containsInAnyOrder(
        new TestRelationType("friend"),
        new TestRelationType("nobody")
    ));
  }

  @Test
  void return_correct_updates_on_not_empty_list_when_has_elements() {
    TestMultiHashMapRelationList list1 = createList(asList(
        new TestRelation("user1", "friend"),
        new TestRelation("user2", "follower"),
        new TestRelation("user3", "fan"),
        new TestRelation("user3", "friend"),
        new TestRelation("user4", "follower")
    ));
    TestMultiHashMapRelationList list2 = createList(asList(
        new TestRelation("user1", "follower"),
        new TestRelation("user2", "follower"),
        new TestRelation("user5", "nobody"),
        new TestRelation("user5", "or not")
    ));
    TestUpdate[] expected = new TestUpdate[]{
        new TestUpdate("user1", "friend", null),
        new TestUpdate("user1", null, "follower"),
        new TestUpdate("user3", "fan", null),
        new TestUpdate("user3", "friend", null),
        new TestUpdate("user4", "follower", null),
        new TestUpdate("user5", null, "nobody"),
        new TestUpdate("user5", null, "or not")
    };
    List<Update> updates = list1.updates(list2).collect(toList());
    assertThat(updates, containsInAnyOrder(expected));
  }

  @Test
  void return_correct_updates_on_empty_list_when_has_elements() {
    TestMultiHashMapRelationList a = createList(someRelations());
    TestMultiHashMapRelationList b = createList();
    List<Update> result = a.updates(b).collect(toList());
    TestUpdate[] expected = someRelations().stream()
        .map(TestRelation::asRemove)
        .toArray(TestUpdate[]::new);
    assertThat(result, containsInAnyOrder(expected));
  }

  @Test
  void remove_user_when_no_relations_known() {
    TestMultiHashMapRelationList list = createList(asList(
        new TestRelation("user1", "friend"),
        new TestRelation("user2", "follower"),
        new TestRelation("user3", "fan")
    ));
    list.apply(new TestUpdate("user2", "follower", null));
    assertThat(list.users().collect(toList()), containsInAnyOrder(
        new TestUser("user1"),
        new TestUser("user3")
    ));
  }

  @Test
  void remove_relations_correctly() {
    TestMultiHashMapRelationList list = createList();
    list.apply(new TestUpdate("user1", null, "friend"));
    list.apply(new TestUpdate("user2", null, "follower"));
    list.apply(new TestUpdate("user3", null, "fan"));
    list.apply(new TestUpdate("user1", null, "nobody"));
    list.apply(new TestUpdate("user2", "follower", null));
    assertThat(list.relations().collect(toList()), containsInAnyOrder(
        new TestRelation("user1", "friend"),
        new TestRelation("user1", "nobody"),
        new TestRelation("user3", "fan")
    ));
  }

  @Nested
  class throw_exception_when {
    @Test
    void try_to_apply_change_and_not_add_or_remove_on_empty_list() {
      TestMultiHashMapRelationList list = createList(someRelations());
      assertThrows(RuntimeException.class, () -> list.apply(new TestUpdate("user", null, null)));
    }

    @Test
    void try_to_apply_change_and_not_add_or_remove_on_not_empty_list() {
      TestMultiHashMapRelationList list = createList(someRelations());
      TestRelation relation = someRelations().get(0);
      String user = relation.getUser().getFullyQualifiedName();
      String type = relation.getType().toString();
      assertThrows(RuntimeException.class, () -> list.apply(new TestUpdate(user, type, type + "1")));
    }

    @Test
    void try_to_update_with_wrong_was_type() {
      TestMultiHashMapRelationList list = createList(someRelations());
      TestRelation relation = someRelations().get(0);
      String user = relation.getUser().getFullyQualifiedName();
      assertThrows(RuntimeException.class, () -> list.apply(new TestUpdate(user, "something", null)));
    }

    @Test
    void try_to_add_existing_relation() {
      TestMultiHashMapRelationList list = createList(someRelations());
      assertThrows(RuntimeException.class, () -> list.apply(someRelations().get(0).asAdd()));
    }
  }
}
