package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.FixUpTask;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	@Query("select f from FixUpTask f where f.category.id = ?1")
	public Collection<FixUpTask> listTaskByCategory(int id);
}
