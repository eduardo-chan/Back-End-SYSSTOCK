package com.utez.sysstock.sysstock.controllers.user;

import com.utez.sysstock.sysstock.dto.email.EmailDto;
import com.utez.sysstock.sysstock.dto.prestamos.PrestamosDto;
import com.utez.sysstock.sysstock.models.user.User;
import com.utez.sysstock.sysstock.security.dtos.UpdatePasswordDto;
import com.utez.sysstock.sysstock.security.jwt.JwtProvider;
import com.utez.sysstock.sysstock.services.user.UserService;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import com.utez.sysstock.sysstock.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("/api-sysstock/prestamo-email/")
@CrossOrigin(origins = {"*"})
public class UserEmailPrestamoController {

    @Autowired
    private JwtProvider provider;
    @Autowired
    private UserService service;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EmailService emailService;

    //String que se envia al email
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;


    /*
    *
    * El sistema debe enviar un mensaje por correo al usuario, especificando la
información del préstamo de artículos.
    *
    * */

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Map<String, Object>>> emailPrestamos(
            @Valid @RequestBody EmailDto emailDto, PrestamosDto prestamosDto) {
        User user = service.getUserByEmail(emailDto.getEmail());
        if (user != null) {
            emailService.sendMail(new EmailDto(
                    user.getEmail(),
                    user.getName() + " :)",
                    "Confirmación de solicitud de prestamo",
                    "Usted ha solicitado el siguiente artículo: " + prestamosDto.getEquipo() + ".\n"
                            + "Tiene " + prestamosDto.getCantidadDias() + " para devoler el equipo, por" +
                            " el contra contrario se aplicará una multa de $20 por día de retraso.\n"
                            + "Fecha límite de entrega: " + prestamosDto.getFechaEntregaEsperada() + "\n"
                            + "Favor de no responder este correo"

            ));
            return new ResponseEntity<>(
                    new CustomResponse<>(
                            false,
                            200,
                            "Ok"
                    ),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Usuario no existente"
                ),
                HttpStatus.BAD_REQUEST
        );
    }





}
//Developed by: Jose Eduardo Arroyo Palafox

