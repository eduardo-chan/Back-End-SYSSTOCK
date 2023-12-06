package com.utez.sysstock.sysstock.controllers.email;


import com.utez.sysstock.sysstock.dto.email.EmailDto;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import com.utez.sysstock.sysstock.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api-sysstock/contact")
@CrossOrigin(origins = {"*"})
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Object>> sendMail(
            @Valid @RequestBody EmailDto emailDto
    ) {
        if (this.service.sendMail(emailDto)) {
            return new ResponseEntity<>(
                    new CustomResponse<>(
                            emailDto,
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
                        "BAD_REQUEST"
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
//Developed by: Jose Eduardo Arroyo Palafox
