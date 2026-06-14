package com.mitocode.controller;

import com.mitocode.model.Menu;
import com.mitocode.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private IMenuService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Menu>> findAll() {
        List<Menu> menus =service.findAll();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Menu>> find(@RequestBody String user_name) {
        List<Menu> menus = service.getMenusByUserName(user_name);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
}