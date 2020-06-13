package musin.seeker.db.update;

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
@Table(name = "relation_update", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelationUpdate {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  Integer id;
  String resource;
  String owner;
  String target;
  String was;
  String now;
  LocalDateTime time;
  boolean hidden;
}
