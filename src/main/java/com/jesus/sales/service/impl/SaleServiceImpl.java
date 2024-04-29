package com.jesus.sales.service.impl;

import com.jesus.sales.model.Sale;
import com.jesus.sales.repo.ISaleRepo;
import com.jesus.sales.repo.IGenericRepo;
import com.jesus.sales.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    @Autowired
    private ISaleRepo repo;
    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return repo;
    }
}
