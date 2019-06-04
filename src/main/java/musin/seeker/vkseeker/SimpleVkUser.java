package musin.seeker.vkseeker;

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
}