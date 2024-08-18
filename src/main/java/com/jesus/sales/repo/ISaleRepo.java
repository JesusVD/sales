package com.jesus.sales.repo;

import com.jesus.sales.dto.ProcedureDTO;
import com.jesus.sales.model.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface ISaleRepo extends IGenericRepo<Sale, Integer>{

    @Query(value = "select * from fn_sales()", nativeQuery = true)
    List<Object []> callProcedure();

}
