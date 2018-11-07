package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Customer a where a.id = ?1") 
	Customer findOne(Integer Id);
	
	@Query("select c.customer from Complaint c order by c.customer limit 0,3")
	List<Customer> TopThreeInComplaints();
}
