package com.jesus.sales.controller;

import com.jesus.sales.dto.CategoryDTO;
import com.jesus.sales.exception.ModelNotFoundException;
import com.jesus.sales.model.Category;
import com.jesus.sales.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> readAll() throws Exception {
        List<CategoryDTO> list = service.readAll().stream().map(cat -> mapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> readById(@PathVariable("id") Integer id) throws Exception {
        CategoryDTO obj = mapper.map(service.readById(id), CategoryDTO.class);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND" + id);
        }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) throws Exception {
        Category obj = service.save(category);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Category> update(@RequestBody Category category) throws Exception {
        Category obj = service.update(category);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        Category obj = service.readById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND" + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
