package musin.seeker.vkseeker.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "relation_change", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelationChange {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;
    int owner;
    int target;
    String prevType;
    String curType;
    LocalDateTime time;
    boolean hidden;
}