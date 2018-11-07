package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Curricula a where a.id = ?1") 
	Curricula findOne(Integer Id);
}
