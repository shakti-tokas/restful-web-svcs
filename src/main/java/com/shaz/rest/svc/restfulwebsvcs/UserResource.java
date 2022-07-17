package com.shaz.rest.svc.restfulwebsvcs;

import com.shaz.rest.svc.restfulwebsvcs.DAO.UserDAOSvc;
import com.shaz.rest.svc.restfulwebsvcs.Exception.UserNotFoundException;
import com.shaz.rest.svc.restfulwebsvcs.Bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDAOSvc service;

    @GetMapping(path = "/users")
    public List<User> getAllUser(){
        return service.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public EntityModel<User> getUser(@PathVariable int id){
        User oneUser = service.findOneUser(id);
        if(oneUser == null){
            throw new UserNotFoundException("id: "+ id);
        }

        EntityModel<User> userModel = EntityModel.of(oneUser);
        WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass())
                .getAllUser());
        userModel.add(linkToUsers.withRel("all-users"));

        return userModel;
    }

    @GetMapping(path = "/users/{id}/posts")
    public User getUserAllPosts(@PathVariable int id){
        User oneUser = service.findOneUser(id);
        if(oneUser == null){
            throw new UserNotFoundException("id: "+ id);
        }

        return oneUser;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = service.saveUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
