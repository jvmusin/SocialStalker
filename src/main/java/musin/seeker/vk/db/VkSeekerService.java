package musin.seeker.vk.db;

import musin.seeker.vk.relation.VkID;

import java.util.List;

public interface VkSeekerService {
  List<VkID> findAllOwners();
}
