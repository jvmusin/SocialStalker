package musin.seeker.vkseeker.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeekerListDto {
    public List<SeekerDto> seekers;
}