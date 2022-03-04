package com.yashen.jwtx.controller;

import com.yashen.jwtx.entity.AuthRequest;
import com.yashen.jwtx.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;  //bean was defined in SecurityConfig file

    @GetMapping("/")
    public String welcome(){
        return "Welcome to javatechie !!";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{

        try{
            authenticationManager.authenticate(   //authenticate
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword())
            );
        }catch (Exception exception){
            throw new Exception("invalid username/password");
        }



        return jwtUtil.generateToken(authRequest.getUserName());  //if auth passed -> generate token via username
    }

    @GetMapping("/en")
    public String enterPoint(){
        return "<h1>welcome to entry point</h1>" +
                "<p>api call want blocked by spring security</p>";
    }
}
