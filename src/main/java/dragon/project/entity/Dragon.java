package dragon.project.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Dragon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dragonId;
	
	private String dragonName;
	
	private String dragonColor;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "dragon", cascade = CascadeType.ALL)
	private Set<Rider> rider = new HashSet<>();
}

