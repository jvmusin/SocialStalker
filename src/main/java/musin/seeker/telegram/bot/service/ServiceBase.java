package musin.seeker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.seeker.api.SocialApi;
import musin.seeker.db.IdFactory;
import musin.seeker.relation.User;
import musin.seeker.relation.UserFactory;
import musin.seeker.updater.SeekerService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class ServiceBase<ID, TUser extends User<ID>> implements Service {
  private final SeekerService<ID> seekerService;
  private final IdFactory<ID> idFactory;
  private final UserFactory<ID, TUser> userFactory;
  private final SocialApi<ID> api;

  @Override
  public Optional<User<?>> searchByUsername(String username) {
    return api.searchByUsername(username).map(userFactory::create);
  }

  @Override
  public Optional<User<?>> searchById(String id) {
    return idFactory.tryParse(id)
        .flatMap(api::searchById)
        .map(userFactory::create);
  }

  @Override
  public void add(String id) {
    seekerService.createSeeker(idFactory.parse(id));
  }

  @Override
  public void delete(String id) {
    seekerService.deleteSeeker(idFactory.parse(id));
  }

  @Override
  public List<User<?>> listSeekers() {
    return seekerService.findAllTargets().stream()
        .map(userFactory::create)
        .collect(toList());
  }
}
