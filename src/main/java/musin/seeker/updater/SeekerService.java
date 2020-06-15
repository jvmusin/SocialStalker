package musin.seeker.updater;

import java.util.List;

public interface SeekerService<ID> {
  List<ID> findAllOwners();
}
