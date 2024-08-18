package com.jesus.sales.controller;

import com.jesus.sales.dto.CategoryDTO;
import com.jesus.sales.exception.ModelNotFoundException;
import com.jesus.sales.model.Category;
import com.jesus.sales.service.ICategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Qualifier("categoryMapper")
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> readAll() throws Exception {
        List<CategoryDTO> list = service.readAll().stream()
                .map(cat -> mapper.map(cat, CategoryDTO.class))
                .collect(Collectors.toList());
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
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) throws Exception {
        Category obj = service.save(mapper.map(dto, Category.class));
        return new ResponseEntity<>(mapper.map(obj, CategoryDTO.class), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto) throws Exception {
        Category obj = service.update(mapper.map(dto, Category.class));
        return new ResponseEntity<>(mapper.map(obj, CategoryDTO.class), HttpStatus.OK);
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

    @GetMapping("/find/name/{name}")
    public ResponseEntity<List<CategoryDTO>> findByName(@PathVariable("name") String name) {
        List<CategoryDTO> categorylist = service.findByName(name).stream().map(cat -> mapper.map(cat, CategoryDTO.class)).toList();
        return new ResponseEntity<>(categorylist, HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Category>> findPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Category> pageResponse = service.findPage(pageRequest);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);

    }

    @GetMapping("/order")
    public ResponseEntity<List<Category>> findAllOrder(
            @RequestParam(name = "param", defaultValue = "ASC") String param
    ) {
        return new ResponseEntity<>(service.findAllOrder(param), HttpStatus.OK);
    }
}
