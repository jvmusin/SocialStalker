package musin.seeker.vkseeker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import musin.seeker.vkseeker.db.model.RelationType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationChangeDto {
    int id;
    LocalDateTime time;
    SeekerDto owner;
    SeekerDto target;
    RelationType prevType;
    RelationType curType;

    public String getTypeTransfer() {
        return prevType + "->" + curType;
    }
}