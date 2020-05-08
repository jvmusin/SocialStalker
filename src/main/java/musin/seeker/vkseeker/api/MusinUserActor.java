package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.actors.UserActor;

public class MusinUserActor extends UserActor {
    private static final int USER_ID = 39666902;
    private static final String ACCESS_TOKEN = "2f8e6773252d2d2b088caa290cd2e38aad2ef15b9df0e0dade25bbd76f1894452b401697f907fd22b25e7";

    public MusinUserActor() {
        super(USER_ID, ACCESS_TOKEN);
    }
}
