package musin.stalker.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import musin.stalker.api.Id;
import musin.stalker.api.SocialApi;
import musin.stalker.db.IdFactory;
import musin.stalker.relation.User;
import musin.stalker.relation.UserFactory;
import musin.stalker.updater.MonitoringService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class NetworkBase<ID extends Id> implements Network {
  private final MonitoringService<ID> monitoringService;
  private final IdFactory<ID> idFactory;
  private final UserFactory<ID> userFactory;
  private final SocialApi<ID> api;

  @Override
  public Optional<User> searchByUsername(String username) {
    return api.searchByUsername(username).map(userFactory::create);
  }

  @Override
  public Optional<User> searchById(String id) {
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
  public List<User> listTargets() {
    return monitoringService.findAllTargets().stream()
        .map(userFactory::create)
        .collect(toList());
  }
}
