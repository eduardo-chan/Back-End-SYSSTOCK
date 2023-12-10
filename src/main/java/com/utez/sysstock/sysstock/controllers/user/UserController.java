package com.utez.sysstock.sysstock.controllers.user;


import com.utez.sysstock.sysstock.dto.prestamos.PrestamosDto;
import com.utez.sysstock.sysstock.dto.user.UserDto;
import com.utez.sysstock.sysstock.models.user.User;
import com.utez.sysstock.sysstock.services.user.UserService;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-sysstock/user/")
@CrossOrigin(origins = {"*"})
public class UserController {

    @Autowired
    private UserService service;

    //get all
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<User>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get one
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<User>> getOne(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<User>> insert(@Valid @ModelAttribute UserDto userDto){
        //MultipartFile file = userDto.getProfilePhoto();
        return new ResponseEntity<>(
                this.service.insert(userDto.getUser()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<User>> update(
            @PathVariable("id") Long id,
            @RequestBody UserDto userDto) {
        // Obtén el usuario existente desde la base de datos
        User existingUser = service.getOne(id).getData();

        // Actualiza los campos necesarios
        User updatedUser = userDto.getUser();
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setStatus(updatedUser.getStatus());
        existingUser.setRole(updatedUser.getRole());
        //existingUser.setProfilePhoto(updatedUser.getProfilePhoto());

        // No actualizar el campo ProfilePhoto

        CustomResponse<User> response = service.update(existingUser, null);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @DeleteMapping("/")
    public ResponseEntity<CustomResponse<Boolean>> deleteUserById(@ModelAttribute UserDto userDto) throws IOException {
        return new ResponseEntity<>(
                this.service.deleteUserById(userDto.getId()),
                HttpStatus.OK
        );
    }



}
//Developed by: Jose Eduardo Arroyo Palafox