package musin.seeker.vkseeker.api;

import com.vk.api.sdk.client.actors.UserActor;

public class MusinUserActor extends UserActor {
    private static final int USER_ID = 39666902;
    private static final String ACCESS_TOKEN = "89dfd6c8f531ccf6f7ee057256d2698979503ce67e355d5ab1b7abf86276397599d769ed3748de046eb9a";

    public MusinUserActor() {
        super(USER_ID, ACCESS_TOKEN);
    }
}
