package com.mitocode.repo;

import com.mitocode.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepo extends IGenericRepo<Role, Integer> {
}