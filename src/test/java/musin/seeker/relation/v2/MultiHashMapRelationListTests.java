package musin.seeker.relation.v2;

import org.junit.jupiter.api.*;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("MultiHashMapRelationList should")
public class MultiHashMapRelationListTests implements EmptyRelationListTests<TestMultiHashMapRelationList> {
  @Override
  public TestMultiHashMapRelationList createList() {
    return new TestMultiHashMapRelationList();
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
    TestRelationUpdate[] expected = new TestRelationUpdate[]{
        new TestRelationUpdate("user1", "friend", null),
        new TestRelationUpdate("user1", null, "follower"),
        new TestRelationUpdate("user3", "fan", null),
        new TestRelationUpdate("user3", "friend", null),
        new TestRelationUpdate("user4", "follower", null),
        new TestRelationUpdate("user5", null, "nobody"),
        new TestRelationUpdate("user5", null, "or not")
    };
    List<TestRelationUpdate> updates = list1.updates(list2).collect(toList());
    assertThat(updates, containsInAnyOrder(expected));
  }

  @Test
  void return_correct_updates_on_empty_list_when_has_elements() {
    TestMultiHashMapRelationList a = createList(someRelations());
    TestMultiHashMapRelationList b = createList();
    List<TestRelationUpdate> result = a.updates(b).collect(toList());
    TestRelationUpdate[] expected = someRelations().stream()
        .map(TestRelation::asRemove)
        .toArray(TestRelationUpdate[]::new);
    assertThat(result, containsInAnyOrder(expected));
  }

  @Test
  void remove_user_when_no_relations_known() {
    TestMultiHashMapRelationList list = createList(asList(
        new TestRelation("user1", "friend"),
        new TestRelation("user2", "follower"),
        new TestRelation("user3", "fan")
    ));
    list.apply(new TestRelationUpdate("user2", "follower", null));
    assertThat(list.users().collect(toList()), containsInAnyOrder(
        new TestUser("user1"),
        new TestUser("user3")
    ));
  }

  @Test
  void remove_relations_correctly() {
    TestMultiHashMapRelationList list = createList();
    list.apply(new TestRelationUpdate("user1", null, "friend"));
    list.apply(new TestRelationUpdate("user2", null, "follower"));
    list.apply(new TestRelationUpdate("user3", null, "fan"));
    list.apply(new TestRelationUpdate("user1", null, "nobody"));
    list.apply(new TestRelationUpdate("user2", "follower", null));
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
      assertThrows(RuntimeException.class, () -> list.apply(new TestRelationUpdate("user", null, null)));
    }

    @Test
    void try_to_apply_change_and_not_add_or_remove_on_not_empty_list() {
      TestMultiHashMapRelationList list = createList(someRelations());
      TestRelation relation = someRelations().get(0);
      String user = relation.getUser().getName();
      String type = relation.getType().getName();
      assertThrows(RuntimeException.class, () -> list.apply(new TestRelationUpdate(user, type, type + "1")));
    }

    @Test
    void try_to_update_with_wrong_was_type() {
      TestMultiHashMapRelationList list = createList(someRelations());
      TestRelation relation = someRelations().get(0);
      String user = relation.getUser().getName();
      assertThrows(RuntimeException.class, () -> list.apply(new TestRelationUpdate(user, "something", null)));
    }

    @Test
    void try_to_add_existing_relation() {
      TestMultiHashMapRelationList list = createList(someRelations());
      assertThrows(RuntimeException.class, () -> list.apply(someRelations().get(0).asAdd()));
    }
  }
}
