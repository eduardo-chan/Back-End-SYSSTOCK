package com.utez.sysstock.sysstock.services.prestamos;


import com.utez.sysstock.sysstock.models.categoria.Categoria;
import com.utez.sysstock.sysstock.models.prestamos.Prestamos;
import com.utez.sysstock.sysstock.models.prestamos.PrestamosRepository;
import com.utez.sysstock.sysstock.models.user.User;
import com.utez.sysstock.sysstock.models.user.UserRepository;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class PrestamosService {


    @Autowired
    private PrestamosRepository repository;


    //nooop->
    // Expresión de autorización para el rol "ROLE_ADMIN"
    private static final String ADMIN_AUTH_EXPRESSION = "hasRole('ROLE_ADMIN')";




    //obtener todos los prestamos
    @Transactional(readOnly = true)
    public CustomResponse<List<Prestamos>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    //obtener un prestamo
    @Transactional(readOnly = true)
    public CustomResponse<Prestamos> getOne(Long id) {
        if (this.repository.findById(id).isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Registro no existe."
            );
        }
        return new CustomResponse<>(
                this.repository.findById(id).get(),
                false,
                200,
                "Ok"
        );
    }

    //insert
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Prestamos> insert(Prestamos prestamos) {

        // Verifica que la cantidad de días no sea mayor a 4
        if (prestamos.getCantidadDias() > 4) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La cantidad de días no puede ser mayor a 4"
            );
        }

        return new CustomResponse<>(
                this.repository.saveAndFlush(prestamos),
                false,
                200,
                "Prestamo solicitado, tienes " + prestamos.getCantidadDias() + " días para devolverlo"
        );
    }


    //update
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Prestamos> update(Prestamos prestamos) {
        if (!this.repository.existsById(prestamos.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El prestamo no existe"
            );
        }

        // Verifica que la cantidad de días no sea mayor a 4
        if (prestamos.getCantidadDias() > 4) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La cantidad de días no puede ser mayor a 4"
            );
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(prestamos),
                false,
                200,
                "Prestamo actualizado!"
        );
    }



    //delete by id
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> deletePrestamosById(Long id) {
        if (!this.repository.existsById(id)) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El prestamo no existe"
            );
        }

        this.repository.deleteById(id);

        return new CustomResponse<>(
                true,
                false,
                200,
                "Prestamo eliminado exitosamente"
        );
    }




}
