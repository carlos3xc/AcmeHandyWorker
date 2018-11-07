package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Sponsorship a where a.id = ?1") 
	Sponsorship findOne(Integer Id);
}
