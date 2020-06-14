package musin.seeker.relation.v2;

import org.junit.jupiter.api.*;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("SingleHashMapRelationList should")
public class SingleHashMapRelationListTests implements EmptyRelationListTests<TestSingleHashMapRelationList> {

  @Override
  public TestSingleHashMapRelationList createList() {
    return new TestSingleHashMapRelationList();
  }

  @Test
  void return_correct_updates_on_not_empty_list_when_has_elements() {
    TestSingleHashMapRelationList list1 = createList(asList(
        new TestRelation("user1", "friend"),
        new TestRelation("user2", "follower"),
        new TestRelation("user3", "fan"),
        new TestRelation("user4", "follower")
    ));
    TestSingleHashMapRelationList list2 = createList(asList(
        new TestRelation("user1", "follower"),
        new TestRelation("user2", "follower"),
        new TestRelation("user5", "nobody")
    ));
    TestRelationUpdate[] expected = new TestRelationUpdate[]{
        new TestRelationUpdate("user1", "friend", "follower"),
        new TestRelationUpdate("user3", "fan", null),
        new TestRelationUpdate("user4", "follower", null),
        new TestRelationUpdate("user5", null, "nobody")
    };
    List<TestRelationUpdate> updates = list1.updates(list2).collect(toList());
    assertThat(updates, containsInAnyOrder(expected));
  }

  @Test
  void override_relations() {
    TestSingleHashMapRelationList list = createList();
    list.apply(new TestRelationUpdate("user1", null, "friend"));
    list.apply(new TestRelationUpdate("user2", null, "follower"));
    list.apply(new TestRelationUpdate("user1", "friend", "fan"));
    list.apply(new TestRelationUpdate("user4", null, "friend"));
    list.apply(new TestRelationUpdate("user2", "follower", null));
    assertThat(list.users().collect(toList()), containsInAnyOrder(new TestUser("user1"), new TestUser("user4")));
    assertThat(list.relations().collect(toList()), containsInAnyOrder(
        new TestRelation("user1", "fan"),
        new TestRelation("user4", "friend")
    ));
  }

  @Test
  void return_correct_updates_on_empty_list_when_has_elements() {
    TestSingleHashMapRelationList a = createList(someRelations());
    TestSingleHashMapRelationList b = createList();
    List<TestRelationUpdate> result = a.updates(b).collect(toList());
    TestRelationUpdate[] expected = someRelations().stream()
        .map(TestRelation::asRemove)
        .toArray(TestRelationUpdate[]::new);
    assertThat(result, containsInAnyOrder(expected));
  }

  @Nested
  class throw_exception_when {
    @Test
    void try_to_apply_update_with_same_was_and_now() {
      TestSingleHashMapRelationList list = createList(someRelations());
      TestRelation r = someRelations().get(0);
      String user = r.getUser().getName();
      String type = r.getType().getName();
      assertThrows(RuntimeException.class, () -> list.apply(new TestRelationUpdate(user, type, type)));
    }

    @Test
    void try_to_apply_update_which_doesnt_change_anything_but_also_null() {
      TestSingleHashMapRelationList list = createList(someRelations());
      TestRelation r = someRelations().get(0);
      String user = r.getUser().getName();
      assertThrows(RuntimeException.class, () -> list.apply(new TestRelationUpdate(user, null, null)));
    }

    @Test
    void try_to_apply_update_with_incorrect_was_type() {
      TestSingleHashMapRelationList list = createList(someRelations());
      TestRelation r = someRelations().get(0);
      String user = r.getUser().getName();
      assertThrows(RuntimeException.class, () -> list.apply(new TestRelationUpdate(user, "something", "anything")));
    }
  }
}
