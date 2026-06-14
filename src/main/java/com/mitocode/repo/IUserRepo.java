package com.mitocode.repo;

import com.mitocode.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends IGenericRepo<User, Integer>  {
	User findOneByUsername(String username);
}