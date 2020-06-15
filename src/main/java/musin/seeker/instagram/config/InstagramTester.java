package musin.seeker.instagram.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InstagramTester {
  private final Instagram4j instagram;

  @SneakyThrows
  @EventListener(ApplicationReadyEvent.class)
  public void test() {
    InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest("github"));
    System.out.println("ID for @github is " + userResult.getUser().getPk());
    System.out.println("Number of followers: " + userResult.getUser().getFollower_count());

    InstagramGetUserFollowersResult githubFollowers = instagram.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));
    List<InstagramUserSummary> users = githubFollowers.getUsers();
    for (InstagramUserSummary user : users) {
      System.out.println("User " + user.getUsername() + " follows Github!");
    }

    System.out.println("HI?");
  }
}
