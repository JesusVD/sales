package com.jesus.sales.service;

import com.jesus.sales.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    Category save(Category category) throws Exception;
    Category update(Category category) throws Exception;
    List<Category> readAll() throws Exception;
    Category readById(Integer id) throws Exception;
    void delete(Integer id) throws Exception;

}
