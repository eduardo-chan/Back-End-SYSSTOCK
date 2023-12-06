package com.utez.sysstock.sysstock.security.controller.recoverypass;


import com.utez.sysstock.sysstock.dto.email.EmailDto;
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
@RequestMapping("/api-sysstock/recovery/")
@CrossOrigin(origins = {"*"})
public class RecoveryPasswordController {

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

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Map<String, Object>>> getGeneratedResetPasswordToken(
            @Valid @RequestBody EmailDto emailDto) {
        User user = service.getUserByEmail(emailDto.getEmail());
        if (user != null) {
            String resetPasswordToken = provider.generateResetPasswordToken(emailDto.getEmail());
            String secretPass = generateRandomString();
            service.updateSecretPass(user, secretPass);
            Map<String, Object> data = new HashMap<>();
            data.put("reset_password_token", resetPasswordToken);
            data.put("user", user);
            data.put("secretPass", secretPass);
            emailService.sendMail(new EmailDto(
                    user.getEmail(),
                    user.getName() + " " + user.getSurname() + " " + user.getLastname(),
                    "Cambio de contraseña | Código de verificación",
                    "Tu código de verificación es: " + secretPass
            ));
            return new ResponseEntity<>(
                    new CustomResponse<>(
                            data,
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

    @PutMapping(value = "/updatePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse<User>> updatePassword(
            @RequestBody UpdatePasswordDto updatePasswordDto,
            @Valid BindingResult result) {
        User user = service.getUserByEmail(updatePasswordDto.getEmail());
        if (user != null) {
            if (result.hasErrors()) {
                return new ResponseEntity<>(
                        null,
                        HttpStatus.BAD_REQUEST
                );
            }
            if (updatePasswordDto.getSecretPass().equals(user.getSecretPass())) {
                user.setPassword(updatePasswordDto.getNewPassword());
                service.updateSecretPass(user, null);
                emailService.sendMail(new EmailDto(
                        user.getEmail(),
                        user.getName() + " " + user.getSurname() + " " + user.getLastname(),
                        "Cambio de contraseña",
                        "Se ha actualizado correctamente la contraseña de tu cuenta para el sistema SYSSTOCK"
                ));
                return new ResponseEntity<>(
                        this.service.updatePassword(user),
                        HttpStatus.CREATED
                );
            } else {
                return new ResponseEntity<>(
                        new CustomResponse<>(
                                null,
                                true,
                                400,
                                "El código de verificación no válido."
                        ),
                        HttpStatus.BAD_REQUEST
                );
            }
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

    public static String generateRandomString() {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
//Developed by: Jose Eduardo Arroyo Palafox