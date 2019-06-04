package musin.seeker.vkseeker.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationChangeDto {
    int id;
    LocalDateTime time;
    SeekerDto owner;
    SeekerDto target;
    String prevType;
    String curType;

    public String getTypeTransfer() {
        return prevType + "->" + curType;
    }
}