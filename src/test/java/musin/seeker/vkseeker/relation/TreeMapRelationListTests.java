package musin.seeker.vkseeker.relation;

import org.assertj.core.util.TriFunction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Relation list")
class TreeMapRelationListTests {

  static List<TestRelation> someRelations() {
    return asList(
        new TestRelation("a", "Friend"),
        new TestRelation("b", "Follower"),
        new TestRelation("c", "Fan"),
        new TestRelation("d", "Friend")
    );
  }

  static TestTreeMapRelationList someRelationList() {
    return new TestTreeMapRelationList(someRelations());
  }

  @Nested
  class when_created_should {

    TestTreeMapRelationList list;

    @BeforeEach
    void initList() {
      list = new TestTreeMapRelationList();
    }

    @Test
    void be_empty() {
      assertThat(list, empty());
    }

    @Test
    void return_empty_users() {
      assertThat(list.users().collect(toList()), empty());
    }

    @Test
    void return_null_on_get_call() {
      assertThat(list.get("john"), nullValue());
    }

    @Test
    void return_empty_updates_with_empty_list() {
      assertThat(list.updates(new TestTreeMapRelationList()).collect(toList()), empty());
    }

    @Test
    void return_empty_stream_on_asUpdates_call() {
      assertThat(list.asUpdates().collect(toList()), empty());
    }

    @Test
    void return_updates_which_represent_another_list_on_updates_call() {
      TestTreeMapRelationList other = someRelationList();
      List<TestRelationUpdate> actual = list.updates(other).collect(toList());
      assertEquals(other.asUpdates().collect(toList()), actual);
    }

    @Test
    void return_true_on_add_relation() {
      assertTrue(list.add(someRelations().get(0)));
    }

    @Test
    void return_true_on_add_several_relations() {
      for (TestRelation relation : someRelations())
        assertTrue(list.add(relation));
    }
  }

  @Nested
  class after_adding_some_items_should {

    TestTreeMapRelationList list;

    @BeforeEach
    void initList() {
      list = someRelationList();
    }

    @Test
    void return_real_size() {
      assertThat(list, hasSize(someRelations().size()));
    }

    @Test
    void return_added_users() {
      List<String> expected = someRelations().stream().map(TestRelation::getUser).collect(toList());
      assertEquals(expected, list.users().collect(toList()));
    }

    @Test
    void return_relations_on_get_call() {
      for (TestRelation relation : someRelations())
        assertEquals(relation, list.get(relation.getUser()));
    }

    @Test
    void return_removes_on_updates_call_on_empty_list() {
      List<TestRelationUpdate> expected = someRelations().stream()
          .map(r -> new TestRelationUpdate(r.getUser(), r, null))
          .collect(toList());
      List<TestRelationUpdate> actual = list.updates(new TestTreeMapRelationList()).collect(toList());
      assertEquals(expected, actual);
    }

    @Test
    void return_correct_updates_on_asUpdates_call() {
      List<TestRelationUpdate> expected = someRelations().stream()
          .map(r -> new TestRelationUpdate(r.getUser(), null, r))
          .collect(toList());
      assertEquals(list.asUpdates().collect(toList()), expected);
    }

    @Test
    void return_true_when_changing_relation() {
      assertTrue(list.add(new TestRelation(someRelations().get(0).getUser(), "some new type")));
    }

    @Test
    void remain_same_size_after_changing_relation() {
      int wasSize = list.size();
      list.add(new TestRelation(someRelations().get(0).getUser(), "some new type"));
      assertThat(list, hasSize(wasSize));
    }

    @Test
    void update_relation_after_changing_existing_one() {
      TestRelation newRelation = new TestRelation(someRelations().get(0).getUser(), "some new type");
      list.add(newRelation);
      assertEquals(list.get(newRelation.getUser()), newRelation);
    }

    @Test
    void return_false_when_adding_existing_relation() {
      assertFalse(list.add(someRelations().get(0)));
    }

    @Test
    void remain_same_size_after_trying_to_add_existing_relation() {
      int wasSize = list.size();
      list.add(someRelations().get(0));
      assertThat(list, hasSize(wasSize));
    }

    @Test
    void return_true_after_removing_existing_relation() {
      assertTrue(list.add(new TestRelation(someRelations().get(0).getUser(), null)));
    }

    @Test
    void decrease_size_after_removing_relations() {
      int wasSize = list.size();
      list.add(new TestRelation(someRelations().get(0).getUser(), null));
      list.add(new TestRelation(someRelations().get(1).getUser(), null));
      assertThat(list, hasSize(wasSize - 2));
    }

    @Test
    void return_null_on_get_after_removing_relation() {
      TestRelation relation = new TestRelation(someRelations().get(0).getUser(), null);
      list.add(relation);
      assertNull(list.get(relation.getUser()));
    }

    @Test
    void return_false_when_removing_not_existing_relation() {
      assertFalse(list.remove(new TestRelation("new user", "some type")));
    }

    @Test
    void remain_same_size_after_trying_to_remove_not_existing_relation() {
      int wasSize = list.size();
      list.add(new TestRelation("new user", null));
      assertThat(list, hasSize(wasSize));
    }

    @Test
    void return_true_after_trying_to_add_previously_removed_relation() {
      list.add(new TestRelation(someRelations().get(0).getUser(), null));
      assertTrue(list.add(someRelations().get(0)));
    }

    @Test
    void remain_same_size_after_adding_previously_removed_relation() {
      int wasSize = list.size();
      TestRelation relation = someRelations().get(0);
      list.add(new TestRelation(relation.getUser(), null));
      list.add(relation);
      assertThat(list, hasSize(wasSize));
    }

    @Test
    void return_correct_updates() {
      TestTreeMapRelationList list1 = new TestTreeMapRelationList(asList(
          new TestRelation("user1", "friend"),
          new TestRelation("user2", "follower"),
          new TestRelation("user3", "fan"),
          new TestRelation("user4", "follower")
      ));
      TestTreeMapRelationList list2 = new TestTreeMapRelationList(asList(
          new TestRelation("user1", "follower"),
          new TestRelation("user2", "follower"),
          new TestRelation("user5", "nobody")
      ));
      TriFunction<String, String, String, TestRelationUpdate> update = (user, was, now) -> new TestRelationUpdate(
          user,
          was == null ? null : new TestRelation(user, was),
          now == null ? null : new TestRelation(user, now)
      );
      TestRelationUpdate[] expected = new TestRelationUpdate[]{
          update.apply("user1", "friend", "follower"),
          update.apply("user3", "fan", null),
          update.apply("user4", "follower", null),
          update.apply("user5", null, "nobody")
      };
      List<TestRelationUpdate> updates = list1.updates(list2).collect(toList());
      assertThat(updates, containsInAnyOrder(expected));
    }

    @Nested
    class not_include_removed_relation {

      TestRelation relation;

      @BeforeEach
      void remove() {
        relation = someRelations().get(0);
        list.add(new TestRelation(relation.getUser(), null));
      }

      @Test
      void in_updates() {
        List<TestRelationUpdate> result = list.updates(new TestTreeMapRelationList()).collect(toList());
        assertThat(result, hasSize(someRelations().size() - 1));
        assertThat(result, not(contains(new TestRelationUpdate(relation.getUser(), relation, null))));
      }

      @Test
      void in_users() {
        List<String> users = list.users().collect(toList());
        assertThat(users, hasSize(someRelations().size() - 1));
        assertThat(users, not(contains(relation.getUser())));
      }
    }
  }
}