package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Tutorial a where a.id = ?1") 
	Tutorial findOne(Integer Id);
}
