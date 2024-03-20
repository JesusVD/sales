package com.jesus.sales.service.impl;

import com.jesus.sales.model.Product;
import com.jesus.sales.repo.IProductRepo;
import com.jesus.sales.repo.IGenericRepo;
import com.jesus.sales.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    @Autowired
    private IProductRepo repo;

    @Override
    protected IGenericRepo<Product, Integer> getRepo() {
        return repo;
    }
}
