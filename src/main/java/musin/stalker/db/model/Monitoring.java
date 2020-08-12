package musin.stalker.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Monitoring {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Integer id;
  @ManyToOne
  private Stalker stalker;
  private String network;
  private String target;
}
