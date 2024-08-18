package com.jesus.sales.service;

import com.jesus.sales.dto.ProcedureDTO;
import com.jesus.sales.model.Sale;

import java.util.List;

public interface ISaleService extends ICRUD<Sale, Integer>{

    List<ProcedureDTO> callProcedure();
}
