package com.shaz.rest.svc.restfulwebsvcs.Hello;

import com.shaz.rest.svc.restfulwebsvcs.Bean.User;
import com.shaz.rest.svc.restfulwebsvcs.Bean.UserRepository;
import com.shaz.rest.svc.restfulwebsvcs.DAO.UserDAOSvc;
import com.shaz.rest.svc.restfulwebsvcs.Exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAResource {

    @Autowired
    private UserDAOSvc service;

    @Autowired
    private UserRepository userRepository;
    @GetMapping(path = "/jpa/users")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/jpa/users/{id}")
    public EntityModel<User> getUser(@PathVariable int id){
        Optional<User> oneUser = userRepository.findById(id);
        if(!oneUser.isPresent()){
            throw new UserNotFoundException("id: "+ id);
        }

        EntityModel<User> userModel = EntityModel.of(oneUser.get());
        WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass())
                .getAllUser());
        userModel.add(linkToUsers.withRel("all-users"));

        return userModel;
    }

    @GetMapping(path = "/jpa/users/{id}/posts")
    public User getUserAllPosts(@PathVariable int id){
        User oneUser = service.findOneUser(id);
        if(oneUser == null){
            throw new UserNotFoundException("id: "+ id);
        }

        return oneUser;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }


}
