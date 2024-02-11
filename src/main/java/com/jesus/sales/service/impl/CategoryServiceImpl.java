package com.jesus.sales.service.impl;

import com.jesus.sales.model.Category;
import com.jesus.sales.repo.ICategoryRepo;
import com.jesus.sales.repo.IGenericRepo;
import com.jesus.sales.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends CRUDImpl<Category, Integer> implements ICategoryService {

    @Autowired
    private ICategoryRepo repo;

    @Override
    protected IGenericRepo<Category, Integer> getRepo() {
        return repo;
    }
}
