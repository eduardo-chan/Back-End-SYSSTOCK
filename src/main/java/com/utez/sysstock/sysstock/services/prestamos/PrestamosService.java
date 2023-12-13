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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        // Verifica si el usuario tiene un préstamo activo
        List<Prestamos> prestamosActivos = this.repository.findByUsuarioAndStatus(prestamos.getUsuario(), true);
        if (!prestamosActivos.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario ya tiene un préstamo activo."
            );
        }

        // Verifica si el usuario ha alcanzado el límite de 3 préstamos
        List<Prestamos> prestamosUsuario = this.repository.findByUsuario(prestamos.getUsuario());
        if (prestamosUsuario.size() >= 3) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario ha alcanzado el límite de 3 préstamos."
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

        // Calcula la fecha de entrega esperada y la asigna al préstamo
        LocalDate fechaEntregaEsperada = LocalDate.now().plusDays(prestamos.getCantidadDias());
        prestamos.setFechaEntregaEsperada(fechaEntregaEsperada);

        // Guarda el préstamo en la base de datos
        Prestamos nuevoPrestamo = this.repository.saveAndFlush(prestamos);

        // Verifica si el préstamo está vencido
        if (LocalDate.now().isAfter(fechaEntregaEsperada)) {
            // Calcula el número de días de retraso
            long diasRetraso = ChronoUnit.DAYS.between(fechaEntregaEsperada, LocalDate.now());

            // Calcula la multa total
            double multa = diasRetraso * 20;

            // Establece la multa en el préstamo
            nuevoPrestamo.setMulta(multa);

            // Actualiza el estado del préstamo y almacena la multa en la base de datos
            nuevoPrestamo.setStatus(false);
            this.repository.saveAndFlush(nuevoPrestamo);

            return new CustomResponse<>(
                    nuevoPrestamo,
                    true,
                    200,
                    "Préstamo vencido. Multa acumulada: $" + multa
            );
        }

        return new CustomResponse<>(
                nuevoPrestamo,
                false,
                200,
                "Préstamo solicitado, tienes " + prestamos.getCantidadDias() + " días para devolverlo"
        );
    }


    //update
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Prestamos> update(Prestamos prestamos) {
        //se verifica si existe el prestamo por medio de su id
        if (!this.repository.existsById(prestamos.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El prestamo no existe"
            );
        }

        // Verifica si el usuario tiene un préstamo activo
        List<Prestamos> prestamosActivos = this.repository.findByUsuarioAndStatus(prestamos.getUsuario(), true);
        if (!prestamosActivos.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario ya tiene un préstamo activo."
            );
        }


        // Verifica si el usuario ha alcanzado el límite de 3 préstamos
        List<Prestamos> prestamosUsuario = this.repository.findByUsuario(prestamos.getUsuario());
        if (prestamosUsuario.size() >= 3) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario ha alcanzado el límite de 3 préstamos."
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

        // Calcula la fecha de entrega esperada y la asigna al préstamo
        LocalDate fechaEntregaEsperada = LocalDate.now().plusDays(prestamos.getCantidadDias());
        prestamos.setFechaEntregaEsperada(fechaEntregaEsperada);

        // Guarda el préstamo en la base de datos
        Prestamos nuevoPrestamo = this.repository.saveAndFlush(prestamos);

        // Verifica si el préstamo está vencido
        if (LocalDate.now().isAfter(fechaEntregaEsperada)) {
            // Calcula el número de días de retraso
            long diasRetraso = ChronoUnit.DAYS.between(fechaEntregaEsperada, LocalDate.now());

            // Calcula la multa total
            double multa = diasRetraso * 20;

            // Establece la multa en el préstamo
            nuevoPrestamo.setMulta(multa);

            // Actualiza el estado del préstamo y almacena la multa en la base de datos
            nuevoPrestamo.setStatus(false);
            this.repository.saveAndFlush(nuevoPrestamo);

            return new CustomResponse<>(
                    nuevoPrestamo,
                    true,
                    200,
                    "Préstamo vencido. Multa acumulada: $" + multa
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
