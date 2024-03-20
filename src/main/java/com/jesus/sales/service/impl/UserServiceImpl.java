package com.jesus.sales.service.impl;

import com.jesus.sales.model.User;
import com.jesus.sales.repo.IGenericRepo;
import com.jesus.sales.repo.IUserRepo;
import com.jesus.sales.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {

    @Autowired
    private IUserRepo repo;

    @Override
    protected IGenericRepo<User, Integer> getRepo() {
        return repo;
    }
}
