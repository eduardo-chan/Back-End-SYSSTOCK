package com.utez.sysstock.sysstock.models.prestamos;

import com.utez.sysstock.sysstock.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrestamosRepository extends JpaRepository<Prestamos, Long>  {


//    //busca una lista de préstamos asociados a un usuario específico
//    List<Prestamos> findByUsuario(User usuario);
//
//    Optional<Prestamos> findPrestamosById(Long id);



}
