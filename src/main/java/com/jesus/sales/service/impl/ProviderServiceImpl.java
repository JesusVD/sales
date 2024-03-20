package com.jesus.sales.service.impl;

import com.jesus.sales.model.Provider;
import com.jesus.sales.repo.IProviderRepo;
import com.jesus.sales.repo.IGenericRepo;
import com.jesus.sales.service.IProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl extends CRUDImpl<Provider, Integer> implements IProviderService {

    @Autowired
    private IProviderRepo repo;
    @Override
    protected IGenericRepo<Provider, Integer> getRepo() {
        return repo;
    }
}
