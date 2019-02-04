package repositories;

import domain.Quolet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuoletRepository extends JpaRepository<Quolet, Integer> {


    /*a1. Quolets per Customer: AVG and STDDEV*/

    @Query("select coalesce(avg(cus.quolets.size),0) from Customer cus")
    Double getAvgRemarksPerCustomer();

    @Query("select sqrt(sum(cus.quolets.size*cus.quolets.size)/coalesce(count(cus.quolets.size),0)-(avg(cus.quolets.size)*avg(cus.quolets.size))) from Customer cus")
    Double getStddevRemarksPerCustomer();

    /*a2. Quolets per FixUpTask: AVG and STDDEV*/

    @Query("select coalesce(avg(fix.quolets.size),0) from FixUpTask fix")
    Double getAvgRemarksPerFixUpTask();

    @Query("select sqrt(sum(fix.quolets.size*fix.quolets.size)/coalesce(count(fix.quolets.size),0)-(avg(fix.quolets.size)*avg(fix.quolets.size))) from FixUpTask fix")
    Double getStddevRemarksPerFixUpTask();

    /*b. Ratio of published Quolets per FixUpTask*/

    @Query("select count(quo)/(select coalesce(count(fix),0) from FixUpTask fix) from Quolet quo where quo.publicationMoment is not null")
    Double getRatioPublishedQuoletPerFixUpTask();

    /*c. Ratio of unpublished Quolets per FixUpTask*/

    @Query("select count(quo)/(select coalesce(count(fix),0) from FixUpTask fix) from Quolet quo where quo.publicationMoment is null")
    Double getRatioUnOublishedQuoletPerFixUpTask();
}
