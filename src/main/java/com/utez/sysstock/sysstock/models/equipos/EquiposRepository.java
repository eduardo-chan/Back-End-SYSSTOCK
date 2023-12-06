package com.utez.sysstock.sysstock.models.equipos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EquiposRepository extends JpaRepository<Equipos, Long> {

    Optional<Equipos> findByName(String name);

    @Modifying
    @Query(value = "UPDATE equipos SET status = :status WHERE id = :id",nativeQuery = true)
    int updateStatusById (
            @Param("status") Boolean status,
            @Param("id")Long id
    );

}
//Developed by: Jose Eduardo Arroyo Palafox