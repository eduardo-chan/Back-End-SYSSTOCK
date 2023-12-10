package com.utez.sysstock.sysstock.services.equipos;

import com.utez.sysstock.sysstock.dto.equipos.EquiposDto;
import com.utez.sysstock.sysstock.models.equipos.Equipos;
import com.utez.sysstock.sysstock.models.equipos.EquiposRepository;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class EquiposService {

    @Autowired
    private EquiposRepository repository;


    //get All
    @Transactional(readOnly = true)
    public CustomResponse<List<Equipos>> getAll(){
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }

    //obtener uno
    @Transactional(readOnly = true)
    public CustomResponse<Equipos> getOne(Long id){
        Optional<Equipos> archive = repository.findById(id);
        if (archive.isEmpty()){
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El registro no existe"
            );
        }
        return new CustomResponse<>(
                archive.get(),
                false,
                200,
                "OK"
        );
    }

    //insert

    private String uploadDirectory =  ".//src//main//resources//files//";
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Equipos> insert(Equipos equipo, MultipartFile file) {
        Optional<Equipos> exists = repository.findByName(equipo.getName());
        if (exists.isPresent()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Equipo ya registrado"
            );
        }

        if (file == null || file.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Equipo inválido o vacío"
            );
        }

        String fileName = file.getOriginalFilename();

        Path filePath = Paths.get(uploadDirectory, fileName);
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error al guardar el equipo"
            );
        }

        equipo.setProfilePhoto(fileName.getBytes());
        Equipos savedEquipo = repository.save(equipo);
        return new CustomResponse<>(
                savedEquipo,
                false,
                200,
                "Equipo registrado"
        );
    }

    //actualizar

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Equipos> update(Equipos equipos, MultipartFile file) {
        if (!this.repository.existsById(equipos.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El equipo no existe"
            );
        }

        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory, fileName);

            try {
                Files.write(filePath, file.getBytes());
                equipos.setProfilePhoto(fileName.getBytes());
            } catch (IOException e) {
                return new CustomResponse<>(
                        null,
                        true,
                        500,
                        "Error al guardar el equipo"
                );
            }
        }

        Equipos savedEquipo = repository.save(equipos);
        return new CustomResponse<>(
                savedEquipo,
                false,
                200,
                "Equipo actualizado"
        );
    }


    //delete by id
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> deleteEquiposById(Long id) {
        if (!this.repository.existsById(id)) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El Equipo no existe"
            );
        }

        this.repository.deleteById(id);

        return new CustomResponse<>(
                true,
                false,
                200,
                "Equipo eliminado exitosamente"
        );
    }



}

