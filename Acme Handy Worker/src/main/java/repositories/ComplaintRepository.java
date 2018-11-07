package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Actor a where a.id = '?1'") 
	Complaint findOne(Integer Id);
	
}

