package dragon.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dragon.project.entity.Dragon;

public interface DragonDao extends JpaRepository<Dragon, Long> {

	Optional<Dragon> findByDragonName(String dragonName);

}
