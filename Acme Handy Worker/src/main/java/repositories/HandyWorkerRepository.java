package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from HandyWorker a where a.id = ?1") 
	HandyWorker findOne(Integer Id);
	
	@Query("select hw from HandyWorker hw where hw.userAccount.id = ?1") 
	HandyWorker findByUserAccountId(Integer Id);
	
	@Query("select hw from HandyWorker hw join hw.userAccount ac where ac.id = ?1")
	HandyWorker findByPrincipal(int id);

	@Query("select f.handyWorker from Finder f where f.id=?1")
	HandyWorker findByFinderId(int finderId);
}
