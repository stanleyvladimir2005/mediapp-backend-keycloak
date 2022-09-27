package com.mitocode.repo;

import com.mitocode.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepo  extends IGenericRepo<Usuario, Integer> {

	public Optional<Usuario> findOneByUsername(String username);
	
	public Boolean existsByUsername(String username);
}
