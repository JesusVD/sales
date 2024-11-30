package com.jesus.sales.repo;

import com.jesus.sales.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends IGenericRepo<User, Integer> {

    User findOneByUsername(String username);
}
