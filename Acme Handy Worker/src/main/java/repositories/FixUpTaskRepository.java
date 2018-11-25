package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from FixUpTask a where a.id = ?1") 
	FixUpTask findOne(Integer Id);
	
	//C-RF 11.1
	@Query("select a.fixUpTask from Application a where a.handyWorker.id=?1")
	Collection<FixUpTask> getFixUpTasksHandyWorker(int handyWorkerId);
	
	@Query("select fx from FixUpTask fx join fx.applications a where a.status='ACCEPTED'")
	Collection<FixUpTask> getTasksAccepted();
}
