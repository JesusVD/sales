package com.jesus.sales.controller;

import com.jesus.sales.dto.ProcedureDTO;
import com.jesus.sales.dto.SaleDTO;
import com.jesus.sales.exception.ModelNotFoundException;
import com.jesus.sales.model.Sale;
import com.jesus.sales.service.ISaleService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private ISaleService service;

    @Autowired
    @Qualifier("saleMapper")
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> readAll() throws Exception {
        List<SaleDTO> list = service.readAll().stream()
                .map(sale -> mapper.map(sale, SaleDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> readById(@PathVariable("id") Integer id) throws Exception {
        SaleDTO obj = mapper.map(service.readById(id), SaleDTO.class);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND" + id);
        }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SaleDTO> create(@Valid @RequestBody SaleDTO dto) throws Exception {
        Sale obj = service.save(mapper.map(dto, Sale.class));
        return new ResponseEntity<>(mapper.map(obj, SaleDTO.class), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SaleDTO> update(@Valid @RequestBody SaleDTO dto) throws Exception {
        Sale obj = service.update(mapper.map(dto, Sale.class));
        return new ResponseEntity<>(mapper.map(obj, SaleDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        Sale obj = service.readById(id);
        if (obj == null) {
            throw new ModelNotFoundException("ID NOT FOUND" + id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/resume")
    public ResponseEntity<List<ProcedureDTO>> getSaleResume(){
        return new ResponseEntity<>(service.callProcedure(),HttpStatus.OK);
    }

    @GetMapping("/mostexpensive")
    public ResponseEntity<SaleDTO> getSaleMostExpensive(){
        SaleDTO dto = mapper.map(service.getSaleMostExpensive(), SaleDTO.class);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/bestseller")
    public ResponseEntity<String> getBestSellerPerson(){
        String user = service.getBestSellerPerson();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/sellercount")
    public ResponseEntity<Map<String, Long>> getSalesCountBySeller(){
        Map<String, Long> map = service.getSalesCountBySeller();
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/bestproduct")
    public ResponseEntity<Map<String, Double>> getMostSellerProduct(){
        Map<String, Double> map = service.getMostSellerProduct();
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


}
