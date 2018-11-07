package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTaskApplication;

@Repository
public interface FixUpTaskApplicationRepository extends JpaRepository<FixUpTaskApplication, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from FixUpTaskApplication a where a.id = ?1") 
	FixUpTaskApplication findOne(Integer Id);
}
