package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Warranty;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Warranty a where a.id = ?1") 
	Warranty findOne(Integer Id);
}
