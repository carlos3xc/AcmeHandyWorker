package repositories;

import domain.Quolet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface QuoletRepository extends JpaRepository<Quolet, Integer> {


    /*a1. Quolets per Customer: AVG and STDDEV*/

    @Query("select coalesce(avg(cus.quolets.size),0) from Quolet quot, Customer cus where quot.publicationMoment is not null")
    //@Query(" select (select count(quot) from Quolet quot where quot.publicationMoment is not null)*1.0 / count(cus) from Customer cus)")
    Double getAvgQuoletsPerCustomer();

    @Query("select sqrt(sum(cus.quolets.size*cus.quolets.size)/coalesce(count(cus.quolets.size),0)-(avg(cus.quolets.size)*avg(cus.quolets.size))) from Customer cus")
    Double getStddevQuoletsPerCustomer();

    /*a2. Quolets per FixUpTask: AVG and STDDEV*/

    @Query("select coalesce(avg(fix.quolets.size),0) from FixUpTask fix")
    Double getAvgQuoletsPerFixUpTask();

    @Query("select sqrt(sum(fix.quolets.size*fix.quolets.size)/coalesce(count(fix.quolets.size),0)-(avg(fix.quolets.size)*avg(fix.quolets.size))) from FixUpTask fix")
    Double getStddevQuoletsPerFixUpTask();

    /*b. Ratio of published Quolets per FixUpTask*/

    @Query("select count(quo)/(select coalesce(count(fix),0) from FixUpTask fix) from Quolet quo where quo.publicationMoment is not null")
    Double getRatioPublishedQuoletsPerFixUpTask();

    /*c. Ratio of unpublished Quolets per FixUpTask*/

    @Query("select count(quo)/(select coalesce(count(fix),0) from FixUpTask fix) from Quolet quo where quo.publicationMoment is null")
    Double getRatioUnpublishedQuoletsPerFixUpTask();

    /*AUX Queries ---------- */

    @Query("select quot from Quolet quot where quot.fixUpTask.id = ?1 and quot.publicationMoment is not null")
    Collection<Quolet> findPublishedByFixUpTaskId(int fixUpTaskId);

    @Query("select quot from Quolet quot where quot.customer.id = ?1")
    Collection<Quolet> findQuoletByCustomerId(int customerId);

}
