package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Finder a where a.id = ?1") 
	Finder findOne(Integer Id);
}
