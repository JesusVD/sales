package com.jesus.sales.service.impl;

import com.jesus.sales.dto.ProcedureDTO;
import com.jesus.sales.model.Sale;
import com.jesus.sales.repo.ISaleRepo;
import com.jesus.sales.repo.IGenericRepo;
import com.jesus.sales.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    @Autowired
    private ISaleRepo repo;
    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<ProcedureDTO> callProcedure() {
        List<ProcedureDTO> lst = new ArrayList<>();
        repo.callProcedure().forEach( e -> {
            ProcedureDTO dto = new ProcedureDTO();
            dto.setQuantityfn((Integer) e[0]);
            dto.setDatetimefn((String) e[1]);
            lst.add(dto);
        });
        return  lst;
    }
}
