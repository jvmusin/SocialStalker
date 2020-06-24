package musin.socialstalker.relation;

public class UpdateFactoryImpl implements UpdateFactory {
  @Override
  public Update updating(User user, RelationType was, RelationType now) {
    return new UpdateImpl(user, was, now);
  }
}
