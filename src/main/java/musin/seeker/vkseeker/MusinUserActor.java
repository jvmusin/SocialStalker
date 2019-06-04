package musin.seeker.vkseeker;

import com.vk.api.sdk.client.actors.UserActor;

public class MusinUserActor extends UserActor {
    private static final int USER_ID = 39666902;
    private static final String ACCESS_TOKEN = "8adfb3490b53e93cb63eff55d28dc5143f3bf05232ca4e3eef0e9e05dd5d53255b94da46eb78fffede214";

    public MusinUserActor() {
        super(USER_ID, ACCESS_TOKEN);
    }
}
