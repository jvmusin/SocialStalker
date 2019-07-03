package musin.seeker.vkseeker.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleVkUser {
    int userId;
    String firstName;
    String lastName;

    @Override
    public String toString() {
        return String.format("%d: %s %s", userId, firstName, lastName);
    }

    public String link() {
        return "https://vk.com/id" + userId;
    }
}