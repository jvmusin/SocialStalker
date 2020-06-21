package musin.socialstalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.api.SocialApi;
import musin.socialstalker.db.IdFactory;
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.UserFactory;
import musin.socialstalker.updater.SeekerService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class NetworkBase<ID, TUser extends User<ID>> implements Network {
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
  public List<User<?>> listTargets() {
    return seekerService.findAllTargets().stream()
        .map(userFactory::create)
        .collect(toList());
  }
}
