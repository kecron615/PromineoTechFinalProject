package dragon.project.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dragon.project.controller.model.DragonData;
import dragon.project.controller.model.HouseData;
import dragon.project.controller.model.RiderData;
import dragon.project.dao.DragonDao;
import dragon.project.dao.HouseDao;
import dragon.project.dao.RiderDao;
import dragon.project.entity.Dragon;
import dragon.project.entity.House;
import dragon.project.entity.Rider;

@Service
public class DragonProjectService {

	@Autowired
	private DragonDao dragonDao;

	@Autowired
	public RiderDao riderDao;

	@Autowired
	public HouseDao houseDao;

	@Transactional(readOnly = false)
	public DragonData saveDragon(DragonData dragonData) {
		Long dragonId = dragonData.getDragonId();
		Dragon dragon = findOrCreateDragon(dragonId, dragonData.getDragonName());

		setFieldsInDragon(dragon, dragonData);
		return new DragonData(dragonDao.save(dragon));
	}

	private void setFieldsInDragon(Dragon dragon, DragonData dragonData) {
		dragon.setDragonName(dragonData.getDragonName());
		dragon.setDragonColor(dragon.getDragonColor());
	}

	private Dragon findOrCreateDragon(Long dragonId, String dragonName) {
		Dragon dragon;

		if (Objects.isNull(dragonId)) {
			Optional<Dragon> opDragon = dragonDao.findByDragonName(dragonName);

			if (opDragon.isPresent()) {
				throw new DuplicateKeyException("Dragon with name " + dragonName + " already exists.");
			}

			dragon = new Dragon();
		} else {
			dragon = findDragonById(dragonId);
		}
		return dragon;
	}

	private Dragon findDragonById(Long dragonId) {
		return dragonDao.findById(dragonId)
				.orElseThrow(() -> new NoSuchElementException("Dragon with ID=" + dragonId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<DragonData> retrieveAllDragons() {

		List<Dragon> dragons = dragonDao.findAll();
		List<DragonData> response = new LinkedList<>();

		for (Dragon dragon : dragons) {
			response.add(new DragonData(dragon));
		}

		return response;
	}

	@Transactional(readOnly = true)
	public DragonData retrieveDragonById(Long dragonId) {
		Dragon dragon = findDragonById(dragonId);
		return new DragonData(dragon);
	}

	@Transactional(readOnly = false)
	public void deleteDragonById(Long dragonId) {
		Dragon dragon = findDragonById(dragonId);
		dragonDao.delete(dragon);
	}

	@Transactional(readOnly = false)
	public RiderData saveRider(Long dragonId, RiderData riderData) {
		Dragon dragon = findDragonById(dragonId);

		Rider rider = findOrCreateRider(riderData.getRiderId(), dragonId);
		setRiderFields(rider, riderData);

		rider.setDragon(dragon);
		dragon.getRider().add(rider);

		Rider dbrider = riderDao.save(rider);
		return new RiderData(dbrider);
	}

	private void setRiderFields(Rider rider, RiderData riderData) {
		rider.setRiderName(riderData.getRiderName());
		rider.setRiderAllegiance(riderData.getRiderAllegiance());
		rider.setRiderGender(riderData.getRiderGender());
		rider.setRiderHouse(riderData.getRiderHouse());
		rider.setRiderId(riderData.getRiderId());

	}

	private Rider findOrCreateRider(Long riderId, Long dragonId) {
		Rider rider;

		if (Objects.isNull(riderId)) {
			rider = new Rider();
		} else {
			rider = findRiderById(riderId, dragonId);
		}

		return rider;
	}

	private Rider findRiderById(Long riderId, Long dragonId) {
		Rider dbRider = riderDao.findById(riderId)
				.orElseThrow(() -> new NoSuchElementException("Rider with ID=" + riderId + " does not exist."));
		if (dbRider.getDragon().getDragonId() != dragonId) {
			throw new IllegalStateException("Rider with ID={}" + riderId + " never rode dragon with ID={}" + dragonId);
		}

		return dbRider;

	}

	@Transactional(readOnly = true)
	public RiderData retrieveRiderByID(Long dragonId, Long riderId) {
		findDragonById(dragonId);
		Rider rider = findRiderById(riderId, dragonId);

		if (rider.getDragon().getDragonId() != dragonId) {
			throw new IllegalStateException("Rider with ID={}" + riderId + " never rode dragon with ID={}" + dragonId);
		}

		return new RiderData(rider);
	}
	
	@Transactional
	public HouseData saveHouse(Long riderId, Long dragonId, HouseData houseData) {
		Rider rider = findRiderById(riderId, dragonId);
		Long houseId = houseData.getHouseId();
		House house = findOrCreateHouse(riderId, houseId);
		
		copyHouseFields(house, houseData);
		
		rider.getHouses().add(house);
		house.getRider().add(rider);
		
		House dbHouse = houseDao.save(house);
		
		return new HouseData(dbHouse);
	}
	
	private void copyHouseFields(House house, HouseData houseData) {

		house.setHouseId(houseData.getHouseId());
		house.setHouseName(houseData.getHouseName());
		house.setHouseAllegiance(houseData.getHouseAllegiance());
	}

	private House findOrCreateHouse(Long riderId, Long houseId) {
		House house;
		
		if(Objects.isNull(houseId)) {
			house = new House();
		}
		else {
			throw new DuplicateKeyException("House with ID=" + houseId + " already exists");
		}
		return house;
	}
	
	private House findHouseById(Long houseId, Long riderId) {
		House house = houseDao.findById(houseId)
				.orElseThrow(() -> new NoSuchElementException("House with Id=" + houseId + " does not exist."));
		boolean found = false;
		for (Rider rider : house.getRider()) {
			if (rider.getRiderId()==(riderId)) {
				found = true;
				break;
			} 
		} 
		if(!found) {
			throw new IllegalArgumentException(
					"Rider with ID=" + riderId + " not found for House with ID=" + houseId);
		}
		return house;
	}

	public HouseData retrieveHouseById(Long riderId, Long houseId) {

		return new HouseData(findHouseById(houseId, riderId));
	}
}
