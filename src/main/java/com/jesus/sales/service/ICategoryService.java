package com.jesus.sales.service;

import com.jesus.sales.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService extends ICRUD<Category, Integer> {

    List<Category> findByName(String name);

    Page<Category> findPage(Pageable pageable);

    List<Category> findAllOrder(String param);
}
