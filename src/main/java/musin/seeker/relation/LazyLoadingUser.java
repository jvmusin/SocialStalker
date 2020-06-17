package musin.seeker.relation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.util.Lazy;

import java.util.function.Function;

@EqualsAndHashCode(of = "id")
public abstract class LazyLoadingUser<ID, TUser> implements User<ID> {
  @Getter
  private final ID id;
  private final Lazy<TUser> loadUser;

  public LazyLoadingUser(ID instagramID, Function<ID, TUser> loadUser) {
    this.id = instagramID;
    this.loadUser = Lazy.of(() -> loadUser.apply(this.id));
  }

  protected TUser user() {
    return loadUser.get();
  }

  @Override
  public String toString() {
    return getFullyQualifiedName();
  }
}
