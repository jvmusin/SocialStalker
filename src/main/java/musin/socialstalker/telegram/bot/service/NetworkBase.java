package musin.socialstalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.api.SocialApi;
import musin.socialstalker.db.IdFactory;
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.UserFactory;
import musin.socialstalker.updater.MonitoringService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class NetworkBase<ID, TUser extends User<ID>> implements Network {
  private final MonitoringService<ID> monitoringService;
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
  public boolean addMonitoring(String targetUsernameOrId) {
    return monitoringService.createMonitoring(idFactory.parse(targetUsernameOrId));
  }

  @Override
  public boolean deleteMonitoring(String targetUsernameOrId) {
    return monitoringService.deleteMonitoring(idFactory.parse(targetUsernameOrId));
  }

  @Override
  public List<User<?>> listTargets() {
    return monitoringService.findAllTargets().stream()
        .map(userFactory::create)
        .collect(toList());
  }
}
