package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Report a where a.id = ?1") 
	Report findOne(Integer Id);
}
