package dragon.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dragon.project.entity.House;

public interface HouseDao extends JpaRepository<House, Long> {

	 //Set<House> findAllByHouseIn(Set<String> house);
}
