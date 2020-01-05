package com.bookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.models.User;
import com.bookstore.service.UserService;

/**
 * @author Marieta
 *
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public Iterable<User> getAll() {
        return userService.findAll();
    }

}
