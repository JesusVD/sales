package com.jesus.sales.service;

import com.jesus.sales.model.Category;
import com.jesus.sales.repo.ICategoryRepo;
import com.jesus.sales.service.impl.CategoryServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl service ;

    @MockBean
    private ICategoryRepo categoryRepo;

    private Category CATEGORY_1 ;
    private Category CATEGORY_2 ;
    private Category CATEGORY_3 ;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        CATEGORY_1 = new Category(1,"TVES","Television", true);
        CATEGORY_2 = new Category(2,"Radio","Radio", true);
        CATEGORY_3 = new Category(3,"PCES","Computadora", true);

        List<Category> categories = Arrays.asList(CATEGORY_1, CATEGORY_2, CATEGORY_3);
        Mockito.when(categoryRepo.findAll()).thenReturn(categories);
        Mockito.when(categoryRepo.findById(any())).thenReturn(Optional.of(CATEGORY_1));
        Mockito.when(categoryRepo.save(any())).thenReturn(CATEGORY_1);
    }

    @Test
    public void readAllTest() throws Exception {
        List<Category> response = service.readAll();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(response.size(), 3);
    }

    @Test
    public void readById() throws Exception {
        Category response = service.readById(1);

        assertNotNull(response);
    }

    @Test
    public void save() throws Exception {
        Category response = service.save(CATEGORY_1);
        assertNotNull(response);
    }

    @Test
    public void update() throws Exception {
        Category response = service.update(CATEGORY_1);
        assertNotNull(response);
    }

    @Test
    public void delete() throws Exception {
        service.delete(1);
        service.delete(1);
        verify(categoryRepo, times(2)).deleteById(1);
    }



}
