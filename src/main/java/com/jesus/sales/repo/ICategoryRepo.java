package com.jesus.sales.repo;

import com.jesus.sales.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepo extends IGenericRepo<Category, Integer> {
}
