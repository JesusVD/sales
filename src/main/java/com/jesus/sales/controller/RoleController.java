package com.jesus.sales.controller;

import com.jesus.sales.dto.RoleDTO;
import com.jesus.sales.exception.ModelNotFoundException;
import com.jesus.sales.model.Role;
import com.jesus.sales.service.IRoleService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private IRoleService service;

    @Autowired
    @Qualifier("roleMapper")
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> readAll() throws Exception {
        List<RoleDTO> list = service.readAll().stream()
                .map(rol -> mapper.map(rol, RoleDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> readById(@PathVariable("id") Integer id) throws Exception {
        RoleDTO obj = mapper.map(service.readById(id), RoleDTO.class);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND" + id);
        }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleDTO dto) throws Exception {
        Role obj = service.save(mapper.map(dto, Role.class));
        return new ResponseEntity<>(mapper.map(obj, RoleDTO.class), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RoleDTO> update(@Valid @RequestBody RoleDTO dto) throws Exception {
        Role obj = service.update(mapper.map(dto, Role.class));
        return new ResponseEntity<>(mapper.map(obj, RoleDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        Role obj = service.readById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND" + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
