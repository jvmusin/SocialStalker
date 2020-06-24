package musin.socialstalker.relation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import musin.socialstalker.api.Id;
import org.springframework.data.util.Lazy;

import java.util.function.Supplier;

@EqualsAndHashCode(of = "id")
public abstract class LazyLoadingUser<ID extends Id, TUser> implements User {
  @Getter
  private final ID id;
  private final Lazy<TUser> loadUser;

  public LazyLoadingUser(ID userId, Supplier<TUser> loadUser) {
    this.id = userId;
    this.loadUser = Lazy.of(loadUser);
  }

  protected TUser user() {
    return loadUser.get();
  }

  @Override
  public String toString() {
    return getFullyQualifiedName();
  }
}
