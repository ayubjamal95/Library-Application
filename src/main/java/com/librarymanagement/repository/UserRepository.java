package com.librarymanagement.repository;

import com.librarymanagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByNameAndLastName(String name, String lastName);
    @Query("SELECT u FROM Users u WHERE u.memberTill > CURRENT_DATE OR u.memberTill IS NULL")
    List<Users> findNonTerminatedUsers();
}
