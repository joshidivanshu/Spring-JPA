package com.springboot.JPA;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {
    public List<User> findByTech(String tech);
    public <Optional>User findByName(String name);
}
