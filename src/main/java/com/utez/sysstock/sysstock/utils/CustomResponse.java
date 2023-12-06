package com.utez.sysstock.sysstock.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomResponse <T> {

    T data;
    Boolean error;
    int StatusCode;
    String message;

    // Constructor original
    public CustomResponse(T data, boolean error, int StatusCode) {
        this.data = data;
        this.error = error;
        this.StatusCode = StatusCode;
    }

    // Nuevo constructor con mensaje
    public CustomResponse(boolean error, int StatusCode, String message) {
        this.data = null;
        this.error = error;
        this.StatusCode = StatusCode;
        this.message = message;
    }

}
//Developed by: Jose Eduardo Arroyo Palafox