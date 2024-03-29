package dragon.project.controller.model;

import java.util.HashSet;
import java.util.Set;

import dragon.project.controller.model.DragonData.RiderResponse;
import dragon.project.entity.Dragon;
import dragon.project.entity.House;
import dragon.project.entity.Rider;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RiderData {
	private Long riderId;
	private String riderName; 
	private String riderHouse;
	private String riderGender;
	private String riderAllegiance;
	//private String riderDragon;
	private DragonRider dragon; 
	private Set<String> houses = new HashSet<>();
	
	public RiderData(Rider rider) {
		riderId = rider.getRiderId();
		riderName = rider.getRiderName();
		riderHouse = rider.getRiderHouse();
		riderGender = rider.getRiderGender();
		riderAllegiance = rider.getRiderAllegiance();
		dragon = new DragonRider(rider.getDragon());
		
		/*
		 * public void assignHouse(House house) { this.riderHouse =
		 * house.getHouseName(); this.houses.add(house.getHouseName()); }
		 */

		for(House house : rider.getHouses()) {
			houses.add(house.getHouseName());
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class DragonRider {
		private Long dragonId;
		private String dragonName;
		private String dragonColor;
		
		public DragonRider(Dragon dragon) {
			dragonId = dragon.getDragonId();
			dragonName = dragon.getDragonName();
			dragonColor = dragon.getDragonColor();
		}
		
	}
}
