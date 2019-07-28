package musin.seeker.vkseeker.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Enumerated(EnumType.STRING)
    RelationType prevType;
    @Enumerated(EnumType.STRING)
    RelationType curType;
    LocalDateTime time;
    boolean hidden;
}