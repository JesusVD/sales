package com.jesus.sales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesus.sales.dto.CategoryDTO;
import com.jesus.sales.exception.ModelNotFoundException;
import com.jesus.sales.model.Category;
import com.jesus.sales.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryService service;

    @MockBean(name = "categoryMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    Category CATEGORY_1 = new Category(1, "TVES", "Television", true);
    Category CATEGORY_2 = new Category(2, "Radio", "Radio", true);
    Category CATEGORY_3 = new Category(3, "PCES", "Computadora", true);

    CategoryDTO CATEGORYDTO_1 = new CategoryDTO(1, "TVES", "Television", true);
    CategoryDTO CATEGORYDTO_2 = new CategoryDTO(2, "Radio", "Radio", true);
    CategoryDTO CATEGORYDTO_3 = new CategoryDTO(3, "PCES", "Computadora", true);

    @Test
    public void readAllTest() throws Exception {

        List<Category> categories = Arrays.asList(CATEGORY_1, CATEGORY_2, CATEGORY_3);
        Mockito.when(service.readAll()).thenReturn(categories);

        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORYDTO_1);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORYDTO_2);
        Mockito.when(modelMapper.map(CATEGORY_3, CategoryDTO.class)).thenReturn(CATEGORYDTO_3);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].nameCategory", is("Radio")));
    }

    @Test
    public void readByIdTest() throws Exception {

        int ID = 1;

        Mockito.when(service.readById(any())).thenReturn(CATEGORY_1);
        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORYDTO_1);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/" + ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameCategory", is("TV")));
    }

    @Test
    public void createTest() throws Exception {

        Mockito.when(service.save(any())).thenReturn(CATEGORY_2);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORYDTO_2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nameCategory", is("Radio")))
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    public void updateTest() throws Exception {
        Mockito.when(service.readById(any())).thenReturn(CATEGORY_3);
        Mockito.when(service.update(any())).thenReturn(CATEGORY_2);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORYDTO_2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/categories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameCategory", is("Radio")))
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    public void updateErrorTest() throws Exception {
        Mockito.when(service.readById(any())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/categories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_2));

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }

    @Test
    public void deleteTest() throws Exception {

        int ID_CATEGORY = 1;

        Mockito.when(service.readById(any())).thenReturn(CATEGORY_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/categories/" + ID_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteErrorTest() throws Exception {

        int ID_CATEGORY = 999;

        Mockito.when(service.readById(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/categories/" + ID_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }
}
