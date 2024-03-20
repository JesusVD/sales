package com.jesus.sales.service.impl;

import com.jesus.sales.model.Role;
import com.jesus.sales.repo.IGenericRepo;
import com.jesus.sales.repo.IRoleRepo;
import com.jesus.sales.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends CRUDImpl<Role, Integer> implements IRoleService {

    @Autowired
    private IRoleRepo repo;

    @Override
    protected IGenericRepo<Role, Integer> getRepo() {
        return repo;
    }
}
