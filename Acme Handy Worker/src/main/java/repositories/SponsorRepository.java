package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Sponsor a where a.id = ?1") 
	Sponsor findOne(Integer Id);
}
