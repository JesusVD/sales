package com.jesus.sales.service;

import com.jesus.sales.dto.ProcedureDTO;
import com.jesus.sales.model.Sale;

import java.util.List;
import java.util.Map;

public interface ISaleService extends ICRUD<Sale, Integer>{

    List<ProcedureDTO> callProcedure();

    Sale getSaleMostExpensive();
    String getBestSellerPerson();
    Map<String, Long> getSalesCountBySeller();
    Map<String, Double> getMostSellerProduct();
}
