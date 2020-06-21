package musin.seeker.db.seeker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "seeker", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seeker {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  Integer id;

  String network;

  String owner;
}