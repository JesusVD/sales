package com.jesus.sales.repo;

import com.jesus.sales.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepo extends IGenericRepo<Category, Integer> {

    List<Category> findByName(String name);

    @Query("FROM Category c WHERE c.name = :name AND c.description LIKE %:description%")
    List<Category> getByNameAndDescription(@Param("name") String name, @Param("description") String description);

    @Query(value="SELECT * FROM category WHERE name = :name", nativeQuery = true)
    List<Category> getNameBySQL(@Param("name") String name);
}
