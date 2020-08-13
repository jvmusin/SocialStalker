package musin.stalker.relation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import musin.stalker.db.Id;
import org.springframework.data.util.Lazy;

import java.util.function.Supplier;

@EqualsAndHashCode(of = "id")
public abstract class LazyLoadingUser<TUser> implements User {
  @Getter
  private final Id id;
  private final Lazy<TUser> loadUser;

  public LazyLoadingUser(Id userId, Supplier<TUser> loadUser) {
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
