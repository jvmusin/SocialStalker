package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

public interface SeekerServiceFactory<ID> {
  SeekerService<ID> create(Stalker stalker);
}
