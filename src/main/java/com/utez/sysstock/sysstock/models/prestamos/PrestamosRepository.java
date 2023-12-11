package com.utez.sysstock.sysstock.models.prestamos;

import com.utez.sysstock.sysstock.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrestamosRepository extends JpaRepository<Prestamos, Long>  {


    List<Prestamos> findByUsuarioAndStatus(User usuario, Boolean status);

    List<Prestamos> findByUsuario(User usuario);


}
