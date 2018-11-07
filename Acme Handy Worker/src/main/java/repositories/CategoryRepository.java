package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Category a where a.id = ?1") 
	Category findOne(Integer Id);
}
