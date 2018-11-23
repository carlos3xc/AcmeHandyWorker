package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Actor a where a.id = '?1'") 
	Complaint findOne(Integer Id);
	
	// B-RF 36.1
	@Query("select c from Complaint c where c not in (select distinct r.complaint from Report r)")
	Collection<Complaint> getComplaintsWithNoReports();
	
	// B-RF 36.2
	@Query("select r.complaint from Report r where r.referee.id = ?1")
	Collection<Complaint> getComplaintsReferee(int refereeId);
	
	// B-RF 37.3
	@Query("select distinct fx.complaints from Application a join a.fixUpTask fx where a.handyWorker.id = ?1")
	Collection<Complaint> getComplaintsHandyWorker(int handyWorkerId);
}

