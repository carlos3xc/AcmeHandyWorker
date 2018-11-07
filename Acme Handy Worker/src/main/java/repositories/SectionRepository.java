package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Section a where a.id = ?1") 
	Section findOne(Integer Id);
}
