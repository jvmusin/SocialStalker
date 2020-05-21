package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.actors.UserActor;

public class MusinUserActor extends UserActor {
    private static final int USER_ID = 39666902;
    private static final String ACCESS_TOKEN = "a5b128bedc842fb5a0daafd1bf786cbe269fd1aeb853d45a80eb50291bcb30582277a52988f5dd7d2a6d6";

    public MusinUserActor() {
        super(USER_ID, ACCESS_TOKEN);
    }
}
