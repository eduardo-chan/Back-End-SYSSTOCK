package com.utez.sysstock.sysstock.dto.email;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailDto {
    private String email;
    private String fullName;
    private String subject;
    private String body;
}

//Developed by: Jose Eduardo Arroyo Palafox
