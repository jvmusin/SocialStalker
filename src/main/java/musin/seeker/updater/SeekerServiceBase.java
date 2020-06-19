package musin.seeker.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.config.ServiceProperties;
import musin.seeker.db.IdFactory;
import musin.seeker.db.seeker.Seeker;
import musin.seeker.db.seeker.SeekerRepository;
import musin.seeker.notifier.NotifiableUpdate;
import musin.seeker.relation.Update;
import musin.seeker.relation.UpdateFactory;
import musin.seeker.relation.User;
import musin.seeker.relation.list.RelationList;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class SeekerServiceBase<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TUpdate extends Update<TUser, TRelationType>,
    TRelationList extends RelationList<TUser, TRelationType>,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>> implements SeekerService<ID> {

  private final SeekerRepository seekerRepository;
  private final ServiceProperties properties;
  private final IdFactory<ID> idFactory;
  private final RelationListPuller<ID, TRelationList> relationListPuller;
  private final UpdateService<ID, TUpdate, TRelationList, TNotifiableUpdate> updateService;
  private final UpdateFactory<TUser, TRelationType, TUpdate> updateFactory;

  @Override
  public List<ID> findAllOwners() {
    return seekerRepository.findAllByResource(properties.getResource()).stream()
        .map(seeker -> idFactory.parse(seeker.getOwner()))
        .collect(toList());
  }

  protected void save(ID owner) {
    seekerRepository.save(new Seeker(null, properties.getResource(), owner.toString()));
  }

  @Override
  public void createSeeker(ID userId) {
    deleteSeeker(userId);
    TRelationList list = relationListPuller.pull(userId).join();
    updateService.saveAll(list.asUpdates(updateFactory).collect(toList()), userId);
    save(userId);
  }

  @Override
  @Transactional
  public void deleteSeeker(ID userId) {
    updateService.removeAllByOwner(userId);
    seekerRepository.deleteByOwner(userId.toString());
  }

  @Override
  public String getResource() {
    return properties.getResource();
  }
}
