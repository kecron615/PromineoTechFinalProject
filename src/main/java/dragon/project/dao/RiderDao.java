package dragon.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dragon.project.entity.Rider;

public interface RiderDao extends JpaRepository<Rider, Long> {


}
