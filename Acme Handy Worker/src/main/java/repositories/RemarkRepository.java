package repositories;

import domain.Remark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RemarkRepository extends JpaRepository<Remark, Integer> {


    /*Remarks per Customer*/

    @Query("select avg(cus.remarks.size) from Customer cus")
    Double getAvgRemarksPerCustomer();

    @Query("select sqrt(sum(cus.remarks.size*cus.remarks.size)) " +
            "/(count(cus.remarks.size)-(avg(cus.remarks.size)*avg(cus.remarks.size))) from Customer cus")
    Double getStddevRemarksPerCustomer();

    /*Remarks per FixUpTask*/

    @Query("select avg(fix.remarks.size) from FixUpTask fix")
    Double getAvgRemarksPerFixUpTask();

    @Query("select sqrt(sum(fix.remarks.size*fix.remarks.size)) " +
            "/(count(fix.remarks.size)-(avg(fix.remarks.size)*avg(fix.remarks.size))) from FixUpTask fix")
    Double getStddevRemarksPerFixUpTask();

}
