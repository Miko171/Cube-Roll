package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;


public interface UserService {


    void register(User user);


    User login(String username, String password);


    User findByUsername(String username);


    void updateUser(User user);



}