package musin.stalker.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
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
  private String was;
  private String now;
  private LocalDateTime time;
}
