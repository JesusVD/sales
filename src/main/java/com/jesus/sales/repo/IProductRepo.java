package com.jesus.sales.repo;

import com.jesus.sales.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends IGenericRepo<Product, Integer> {
}
