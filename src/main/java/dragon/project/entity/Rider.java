package dragon.project.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Rider {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long riderId;
	private String riderName; 
	private String riderHouse;
	private String riderGender;
	private String riderAllegiance;
	//private String riderDragon;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dragon_id", nullable = false)
	private Dragon dragon; 
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "rider_house", joinColumns = @JoinColumn(name = "rider_id"), inverseJoinColumns = @JoinColumn(name = "house_id"))
	private Set<House> houses = new HashSet<>();
}
