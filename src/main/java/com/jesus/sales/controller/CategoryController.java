package com.jesus.sales.controller;

import com.jesus.sales.model.Category;
import com.jesus.sales.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService service;

    @GetMapping
    public List<Category> readAll() throws Exception{
        return service.readAll();
    }

    @GetMapping("/{id}")
    public Category readById(@PathVariable("id") Integer id) throws  Exception{
        return service.readById(id);
    }

    @PostMapping
    public Category create(@RequestBody Category category) throws Exception{
        return service.save(category);
    }

    @PutMapping
    public Category update(@RequestBody Category category) throws Exception{
        return service.save(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) throws  Exception{
        service.delete(id);
    }
}
