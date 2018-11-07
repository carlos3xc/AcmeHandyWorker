package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Box a where a.id = ?1") 
	Box findOne(Integer Id);
}
