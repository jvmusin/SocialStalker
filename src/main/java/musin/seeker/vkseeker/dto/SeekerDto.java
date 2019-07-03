package musin.seeker.vkseeker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeekerDto {
    public int userId;
    public String name;

    public String link() {
        return "https://vk.com/id" + userId;
    }

    public String toString() {
        return String.format("%d: %s", userId, name);
    }
}