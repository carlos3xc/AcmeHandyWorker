package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from FixUpTask a where a.id = ?1") 
	FixUpTask findOne(Integer Id);
}
