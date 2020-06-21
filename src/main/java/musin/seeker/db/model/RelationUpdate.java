package musin.seeker.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RelationUpdate {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Integer id;
  @ManyToOne
  private Stalker stalker;
  private String network;
  private String target;
  private String suspected;
  private String wasType;
  private String nowType;
  private LocalDateTime createdAt;
}
