package com.mitocode.serviceImpl;

import com.mitocode.model.Menu;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IMenuRepo;
import com.mitocode.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl extends CRUDImpl<Menu, Integer> implements IMenuService {

    @Autowired
    private IMenuRepo repo;

    @Override
    protected IGenericRepo<Menu, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Menu> listarMenuPorUsuario(String user_name) {
        List<Menu> menus =  new ArrayList<>();
        repo.listarMenuPorUsuario(user_name).forEach( x -> {
            Menu m = new Menu();
            m.setIdMenu((Integer.parseInt(String.valueOf(x[0]))));
            m.setIcon(String.valueOf(x[1]));
            m.setName(String.valueOf(x[2]));
            m.setUrl(String.valueOf(x[3]));

            menus.add(m);
        });
        return menus;
    }
}