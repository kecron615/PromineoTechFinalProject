package dragon.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dragon.project.controller.model.DragonData;
import dragon.project.controller.model.HouseData;
import dragon.project.controller.model.RiderData;
import dragon.project.service.DragonProjectService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dragon_project")
@Slf4j
public class DragonController {
	@Autowired
	private DragonProjectService dragonProjectService;

	@PostMapping("/dragon")
	@ResponseStatus(code = HttpStatus.CREATED)
	public DragonData insertDragon(@RequestBody DragonData dragonData) {
		log.info("Creating a dragon with {}", dragonData);
		return dragonProjectService.saveDragon(dragonData);
	}

	@PutMapping("/dragon/{dragonId}")
	public DragonData updateDragon(@PathVariable Long dragonId, @RequestBody DragonData dragonData) {
		dragonData.setDragonId(dragonId);
		log.info("Updating dragon {}", dragonData);
		return dragonProjectService.saveDragon(dragonData);
	}

	@GetMapping("/dragon")
	public List<DragonData> retrieveAllDragons() {
		log.info("Retrieving all dragons.");
		return dragonProjectService.retrieveAllDragons();
	}

	@GetMapping("/dragon/{dragonId}")
	public DragonData retrieveDragonById(@PathVariable Long dragonId) {
		log.info("Retrieving dragon with Id= {}", dragonId);
		return dragonProjectService.retrieveDragonById(dragonId);
	}

	@DeleteMapping("/dragon")
	public void deleteAllDragons() {
		log.info("Attempting to delete all dragons");
		throw new UnsupportedOperationException("Deleting all dragons is not allowed.");
	}

	@DeleteMapping("/dragon/{dragonId}")
	public Map<String, String> deleteDragonById(@PathVariable Long dragonId) {
		log.info("Deleting dragon with ID= {}", dragonId);

		dragonProjectService.deleteDragonById(dragonId);

		return Map.of("message", "Deletion of dragon with ID=" + dragonId + " was successful.");
	}

	@PostMapping("/dragon/{dragonId}/rider")
	@ResponseStatus(code = HttpStatus.CREATED)
	public RiderData insertRider(@PathVariable Long dragonId, @RequestBody RiderData riderData) {
		log.info("Creating rider {} for Dragon with ID={}", riderData, dragonId);
		return dragonProjectService.saveRider(dragonId, riderData);
	}

	@PutMapping("/dragon/{dragonId}/rider/{riderId}")
	public RiderData updateRider(@PathVariable Long dragonId, @PathVariable Long riderId,
			@RequestBody RiderData riderData) {
		riderData.setRiderId(riderId);
		log.info("Updating rider {} for Dragon with ID={}", riderData, dragonId);
		return dragonProjectService.saveRider(dragonId, riderData);
	}

	@GetMapping("/dragon/{dragonId}/rider/{riderId}")
	public RiderData retrieveRiderById(@PathVariable Long dragonId, @PathVariable Long riderId) {
		log.info("Retrieving rider with ID={} for dragon with ID={}", riderId, dragonId);
		return dragonProjectService.retrieveRiderByID(dragonId, riderId);
	}
	
	@PostMapping("/{riderId}/house")
	@ResponseStatus(code = HttpStatus.CREATED)
	public HouseData insertHouse(@PathVariable Long riderId, @RequestBody HouseData houseData, @PathVariable Long dragonId) {
		log.info("Creating house with ID={} rider with ID={}", houseData, riderId);
		return dragonProjectService.saveHouse(riderId, dragonId, houseData);
	}
	
	@GetMapping("/{riderId}/house")
	public HouseData retrieveHouseById(@PathVariable Long riderId, @PathVariable Long houseId) {
		log.info("Retrieving house with ID={} for rider with ID{}", houseId, riderId);
		return dragonProjectService.retrieveHouseById(riderId, houseId);
	}
}
