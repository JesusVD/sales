package com.jesus.sales.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesus.sales.dto.CategoryDTO;
import com.jesus.sales.security.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test1")
    public ResponseEntity<List<CategoryDTO>> test1() {
        String url = "http://localhost:8080/categories";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<List<CategoryDTO>> typeRef = new ParameterizedTypeReference<>() {};

        return restTemplate.exchange(url, HttpMethod.GET, entity, typeRef);

    }

    @GetMapping("/test2")
    public ResponseEntity<?> test2(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        String url = "http://localhost:8080/categories/pagination?page" + page + "&size" + size;

        ResponseEntity<?> response = restTemplate.getForEntity(url, String.class);

        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }


    @GetMapping("/test3")
    public ResponseEntity<?> test3(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        String url = "http://localhost:8080/categories/pagination?page={page}&size={size}";

        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("page", page);
        uriVariables.put("size", size);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class, uriVariables);
    }

    @PostMapping("/test4")
    public ResponseEntity<CategoryDTO> test4(@RequestBody CategoryDTO categoryDTO) {
        String url = "http://localhost:8080/categories";

        HttpEntity<CategoryDTO> request = new HttpEntity<>(categoryDTO);
        CategoryDTO dto = restTemplate.postForObject(url, request, CategoryDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/test5")
    public ResponseEntity<CategoryDTO> test5(@RequestBody CategoryDTO categoryDTO) {
        String url = "http://localhost:8080/categories";

        HttpEntity<CategoryDTO> request = new HttpEntity<>(categoryDTO);

        return restTemplate.exchange(url, HttpMethod.PUT, request, CategoryDTO.class);
    }

    @DeleteMapping("/test6/{id}")
    public ResponseEntity<Void> test6(@PathVariable("id") Integer id) {
        String url = "http://localhost:8080/categories/" + id;

        restTemplate.delete(url);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/test7/{id}")
    public ResponseEntity<Void> test7(@PathVariable("id") Integer id) {
        String url = "http://localhost:8080/categories/{idCategory}";

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("idCategory", id);

        restTemplate.delete(url, uriVariables);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/test8/{id}")
    public ResponseEntity<Void> test8(@PathVariable("id") Integer id) throws JsonProcessingException {
        //Generando el token
        final String AUTHENTICATION_URL = "http://localhost:8080/login";

        JwtRequest userRequest = new JwtRequest("mito", "123");
        String userRequestJSON = new ObjectMapper().writeValueAsString(userRequest);

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        authHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> authEntity = new HttpEntity<>(userRequestJSON, authHeaders);

        // Enviado el token para pedir datos
        ResponseEntity<String> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL, HttpMethod.POST, authEntity, String.class);
        String token = "Bearer " + authenticationResponse.getBody().split(":")[1].replace("\"", "").replace("}", "");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", token);

        HttpEntity<String> jwtEntity = new HttpEntity<>(headers);

        String url = "http://localhost:8080/categories/{idCategory}";

        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("idCategory", id);

        restTemplate.exchange(url, HttpMethod.DELETE, jwtEntity, String.class, uriVariables);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
