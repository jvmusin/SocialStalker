package musin.seeker.updater;

import musin.seeker.db.model.Stalker;

public interface SeekerServiceFactory<ID> {
  SeekerService<ID> create(Stalker stalker);
}
