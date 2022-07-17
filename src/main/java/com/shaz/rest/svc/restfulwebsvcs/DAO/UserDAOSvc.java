package com.shaz.rest.svc.restfulwebsvcs.DAO;

import com.shaz.rest.svc.restfulwebsvcs.Bean.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class UserDAOSvc {

    private static int userCount=3;
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Eve", new Date()));
        users.add(new User(3, "Shirley", new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User saveUser(User user){

        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findOneUser(int id){
        for(User user:users){
            if(user.getId()==id) {
                return user;
            }
        }
        return null;
    }

    public User findUserPosts(int id){
        for(User user:users){
            if(user.getId()==id) {
                return user;
            }
        }
        return null;
    }
}
