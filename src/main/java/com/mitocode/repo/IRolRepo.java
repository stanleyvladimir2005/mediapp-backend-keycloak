package com.mitocode.repo;

import com.mitocode.model.Rol;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepo extends IGenericRepo<Rol, Integer> {
	
	public Optional<Rol> findByName(String name);
}
