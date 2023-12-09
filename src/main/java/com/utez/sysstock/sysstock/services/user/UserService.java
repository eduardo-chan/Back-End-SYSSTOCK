package com.utez.sysstock.sysstock.services.user;


import com.utez.sysstock.sysstock.models.user.User;
import com.utez.sysstock.sysstock.models.user.UserRepository;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repository;

    @Lazy
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private PasswordEncoder encoder;


    //obtener todos los usuarios
    @Transactional(readOnly = true)
    public CustomResponse<List<User>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    //obtener un usuario
    @Transactional(readOnly = true)
    public CustomResponse<User> getOne(Long id) {
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



    //obtener usuario por correo
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        Optional<User> user = this.repository.findOneByEmail(email  );
        return user.orElse(null);
    }



    //insertar usuarios con foto y contraseña
    //private String uploadDirectory =  ".//src//main//resources//files//";
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<User> insert(User user) {
        Optional<User> exists = repository.findByName(user.getName());
        if (exists.isPresent()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Usuario ya registrado"
            );
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Usuario inválido o vacío"
            );
        }

//        String fileName = file.getOriginalFilename();
//
//        Path filePath = Paths.get(uploadDirectory, fileName);
//        try {
//            //Files.write(filePath, file.getBytes());
//
//        } catch (IOException e) {
//            return new CustomResponse<>(
//                    null,
//                    true,
//                    500,
//                    "Error al guardar el usuario"
//            );
//        }

        user.setPassword(
                encoder.encode(user.getPassword()));

        //user.setProfilePhoto(fileName.getBytes());
        User savedUser= repository.save(user);
        return new CustomResponse<>(
                savedUser,
                false,
                200,
                "Usuario registrado"
        );
    }


    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<User> update(User user, MultipartFile file) {
        if (!this.repository.existsById(user.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario no existe"
            );
        }

//        if (file != null && !file.isEmpty()) {
//            String fileName = file.getOriginalFilename();
//            Path filePath = Paths.get(uploadDirectory, fileName);
//
//            try {
//                Files.write(filePath, file.getBytes());
//                //user.setProfilePhoto(fileName.getBytes());
//            } catch (IOException e) {
//                return new CustomResponse<>(
//                        null,
//                        true,
//                        500,
//                        "Error al guardar el usuario"
//                );
//            }
//        }

        // Solo actualiza la contraseña si se proporciona una nueva contraseña
        if (!user.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        User savedUser = repository.save(user);
        return new CustomResponse<>(
                savedUser,
                false,
                200,
                "Usuario registrado"
        );
    }
    //UpdatePassword
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<User> updatePassword(User user) {
        if (!this.repository.existsById(user.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario no existe"
            );
        }
        user.setPassword(
                encoder.encode(user.getPassword())
        );
        return new CustomResponse<>(
                this.repository.saveAndFlush(user),
                false,
                200,
                "Usuario Actualizado!"
        );
    }

    //update token para cambio de contraseña
    @Transactional(rollbackFor = {SQLException.class})
    public void updateSecretPass(User user, String secretPass) {
        user.setSecretPass(secretPass);
        this.repository.saveAndFlush(user);
    }

    //Cambio de status - borrado lógico
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> changeStatus(User user) {
        if (!this.repository.existsById(user.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.updateStatusById(
                        user.getStatus(), user.getId()) == 1,
                false,
                200,
                "Status Actualizado"
        );
    }

    //Cambio de status - borrado lógico
    public CustomResponse<Boolean> changeStatus(Long userId, boolean newStatus) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setStatus(newStatus);
        repository.save(user);

        return new CustomResponse<>(true, 200, "User status updated successfully");

    }

}
//Developed by: Jose Eduardo Arroyo Palafox