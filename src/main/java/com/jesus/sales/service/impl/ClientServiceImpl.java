package com.jesus.sales.service.impl;

import com.jesus.sales.model.Client;
import com.jesus.sales.repo.IClientRepo;
import com.jesus.sales.repo.IGenericRepo;
import com.jesus.sales.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends CRUDImpl<Client, Integer> implements IClientService {

    @Autowired
    private IClientRepo repo;
    @Override
    protected IGenericRepo<Client, Integer> getRepo() {
        return repo;
    }
}
