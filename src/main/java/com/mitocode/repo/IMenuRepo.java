package com.mitocode.repo;

import com.mitocode.model.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IMenuRepo extends IGenericRepo<Menu, Integer>{

    @Query(value= "select m.* from menu_role mr \n" +
            "inner join usuario_rol ur on ur.id_rol = mr.id_rol \n" +
            "inner join menu m on m.id_menu = mr.id_menu \n" +
            "inner join usuario u on u.id_usuario = ur.id_usuario\n" +
            "where u.user_name = :username", nativeQuery = true)
    List<Object[]> listarMenuPorUsuario(@Param("username") String user_name);
}
