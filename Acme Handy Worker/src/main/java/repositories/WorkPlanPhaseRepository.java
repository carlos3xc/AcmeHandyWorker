package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WorkPlanPhase;

@Repository
public interface WorkPlanPhaseRepository extends JpaRepository<WorkPlanPhase, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from WorkPlanPhase a where a.id = ?1") 
	WorkPlanPhase findOne(Integer Id);
}
