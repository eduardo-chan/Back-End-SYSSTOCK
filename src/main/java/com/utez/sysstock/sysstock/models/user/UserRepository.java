package com.utez.sysstock.sysstock.models.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);
    Optional<User> findByName(String name);

    @Modifying
    @Query(value = "UPDATE users SET status = :status WHERE id = :id",nativeQuery = true)
    int updateStatusById (
            @Param("status") Boolean status,
            @Param("id")Long id
    );

}
//Developed by: Jose Eduardo Arroyo Palafox