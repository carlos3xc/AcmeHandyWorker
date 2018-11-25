package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Customer;
import domain.HandyWorker;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{

	@Query("select a from Application a where a.id = ?1") 
	Application findOne(Integer Id);
	
	@Query("select a from Application a where a.handyWorker.id = ?1")
	Collection<Application> applicationByHandyWorker(HandyWorker hw);
	
	@Query("select a from Application a")
	Collection<Application> applicationByCustomer(Customer c);
}
