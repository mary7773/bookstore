package com.bookstore.controllers;

import java.util.Base64;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.service.UserService;

/**
 * @author Marieta
 *
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8181", "http://localhost:4300"})
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public ResponseEntity token(@RequestBody HashMap<String, String> mapper, HttpSession httpSession, HttpServletResponse response) {
        String username = mapper.get("username");
        String password = mapper.get("password");
       // String encryptedPassword = Utils.passwordEncoder().encode(password);
        System.out.println(username + "}{" + password);
        String isfound = userService.findByUsernameAndPassword(username, password);
        if (isfound == "username not exist") {
            return new ResponseEntity("username not exist", HttpStatus.BAD_REQUEST);
        }
        if (isfound == "password incorrect") {
            return new ResponseEntity("password incorrect", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(httpSession.getId(), HttpStatus.OK);

    }
    
    @RequestMapping("/token")
    public ResponseEntity getToken(HttpServletRequest request, HttpSession httpSession, HttpServletResponse response) {
        String[] basicAuth = request.getHeader("Authorization").split(" ");
        byte[] decodedPass = Base64.getDecoder().decode(basicAuth[1].getBytes());
        String[] auth = new String(decodedPass).split(":");
        String isfound = userService.findByUsernameAndPassword(auth[0], auth[1]);
        if (isfound == "username not exist") {
            return new ResponseEntity("username not exist", HttpStatus.BAD_REQUEST);
        }
        if (isfound == "password incorrect") {
            return new ResponseEntity("password incorrect", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(httpSession.getId(), HttpStatus.OK);

    }

    @RequestMapping("/checkSession")
    public ResponseEntity checkSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
//        if ( ! authentication.isAuthenticated() ){
//            return new ResponseEntity("Inactive Session", HttpStatus.BAD_REQUEST);
//        }
        return new ResponseEntity("Session Active!", HttpStatus.OK);
    }

    @RequestMapping(value = "/userLogout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        // session= request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
            }
        }
        System.out.println("logout" + SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.clearContext();
        System.out.println("logout context" + SecurityContextHolder.getContext());
        return new ResponseEntity("Logout Successful!", HttpStatus.OK);
    }

}
