package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.actors.UserActor;

public class MusinUserActor extends UserActor {
    private static final int USER_ID = 39666902;
    private static final String ACCESS_TOKEN = "935a4239ad41716de2b9e0702d799827d495abc787ef8f020a0330a7c93727ee685de6d2b8c6cb02ebf6f";

    public MusinUserActor() {
        super(USER_ID, ACCESS_TOKEN);
    }
}
