
package dragon.project.controller.model;

import java.util.HashSet;
import java.util.Set;

import dragon.project.entity.Dragon;
import dragon.project.entity.House;
import dragon.project.entity.Rider;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DragonData {

	private Long dragonId; // contributor_Id
	private String dragonName;
	private String dragonColor;
	private Set<RiderResponse> riders = new HashSet<>();

	public DragonData(Dragon dragon) {
		dragonId = dragon.getDragonId();
		dragonName = dragon.getDragonName();
		dragonColor = dragon.getDragonColor();

		for (Rider rider : dragon.getRider()) {
			riders.add(new RiderResponse(rider));
		}
	}

	@Data
	@NoArgsConstructor
	static class RiderResponse {
		private Long riderId;
		private String riderName;
		private String riderHouse;
		private String riderGender;
		private String riderAllegiance;
		private Set<String> houses = new HashSet<>();

		RiderResponse(Rider rider) {
			riderId = rider.getRiderId();
			riderName = rider.getRiderName();
			riderHouse = rider.getRiderHouse();
			riderGender = rider.getRiderGender();
			riderAllegiance = rider.getRiderAllegiance();

			for (House house : rider.getHouses()) {
				houses.add(house.getHouseName());
			}
		}

	}

}
