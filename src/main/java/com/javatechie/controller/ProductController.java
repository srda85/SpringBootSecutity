package com.javatechie.controller;

import com.javatechie.dto.Product;
import com.javatechie.entity.UserInfo;
import com.javatechie.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    //4.2 J'ajoute cette méthode qui devrait normalement être dans un controller à part.
    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);
    }

    //ATTETNION Quand je crée un utilisateur son role doit être comme cela : "role":"ROLE_ADMIN", le role underscore est indispensable.
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    //2.0 Avec preAuthorize je peux définir le champ d'accès des différents roles. faut bien mettre ROLE_...
    //2.1 Il faut préciser à springBoot qu'on utilise cette configuration, c'est pourquoi l'on rajoute une annotation @EnableMethodSecurity dans le config security
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable int id) {
        return service.getProduct(id);
    }
}
