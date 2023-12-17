package com.springboot.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {
    public List<User> findByTech(String tech);
    public <Optional>User findByName(String name);
    public List<User> findByIdGreaterThan(int id);
    // find by tech but all the values should be sorted.
    @Query("from User user where user.tech=?1 order by user.name")
    public List<User> findByTechSorted(String tech);

}
