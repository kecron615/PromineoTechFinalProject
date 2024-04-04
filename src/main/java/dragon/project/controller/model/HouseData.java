package dragon.project.controller.model;

import dragon.project.entity.House;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HouseData {
	private Long houseId;
	private String houseName; 
	private String houseAllegiance;
	

	public HouseData (House house) {
		houseId = house.getHouseId();
		houseName = house.getHouseName();
		houseAllegiance = house.getHouseAllegiance();
	}
}
