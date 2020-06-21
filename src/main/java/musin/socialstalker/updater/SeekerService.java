package musin.socialstalker.updater;

import java.util.List;

public interface SeekerService<ID> {
  List<ID> findAllTargets();

  void createSeeker(ID userId);

  void deleteSeeker(ID userId);
}
