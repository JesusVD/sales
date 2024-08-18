package com.jesus.sales.service.impl;

import com.jesus.sales.dto.ProcedureDTO;
import com.jesus.sales.model.Sale;
import com.jesus.sales.model.SaleDetail;
import com.jesus.sales.repo.ISaleRepo;
import com.jesus.sales.repo.IGenericRepo;
import com.jesus.sales.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    @Autowired
    private ISaleRepo repo;
    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<ProcedureDTO> callProcedure() {
        List<ProcedureDTO> lst = new ArrayList<>();
        repo.callProcedure().forEach( e -> {
            ProcedureDTO dto = new ProcedureDTO();
            dto.setQuantityfn((Integer) e[0]);
            dto.setDatetimefn((String) e[1]);
            lst.add(dto);
        });
        return  lst;
    }

    @Override
    public Sale getSaleMostExpensive() {
        return repo.findAll()
                .stream()
                .max(Comparator.comparing(Sale::getTotal)) //e -> e.getTotal()
                .orElse(new Sale());
    }

    @Override
    public String getBestSellerPerson() {
        Map<String, Double> byUser = repo.findAll()
                .stream()
                .collect(Collectors.groupingBy(s -> s.getUser().getUsername(), Collectors.summingDouble(Sale::getTotal))); //s -> s.getTotal()

        String userBestSeller = Collections.max(byUser.entrySet(), Comparator.comparingDouble( Map.Entry::getValue)).getKey(); //e -> e.getValue()
        return userBestSeller;
    }

    @Override
    public Map<String, Long> getSalesCountBySeller() {
        return repo.findAll()
                .stream()
                .collect(Collectors.groupingBy(s -> s.getUser().getUsername(), Collectors.counting())); //s -> s.getTotal()
    }

    @Override
    public Map<String, Double> getMostSellerProduct() {
        Stream<List<SaleDetail>> stream = repo.findAll().stream().map(Sale::getDetails); //e -> e.getDetails()
        Stream<SaleDetail> streamSaleDetail = stream.flatMap(Collection::stream); //list -> list.stream() , como la lista en un collection

        Map<String, Double> byProduct =  streamSaleDetail.collect(Collectors.groupingBy(d-> d.getProduct().getName(), Collectors.summingDouble(SaleDetail::getQuantity)));


        return byProduct.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new // () -> new LinkedHashMap<>()
                ));
    }
}
