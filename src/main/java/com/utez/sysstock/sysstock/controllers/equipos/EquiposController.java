package com.utez.sysstock.sysstock.controllers.equipos;


import com.utez.sysstock.sysstock.dto.equipos.EquiposDto;
import com.utez.sysstock.sysstock.models.equipos.Equipos;
import com.utez.sysstock.sysstock.services.equipos.EquiposService;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-sysstock/equipos/")
@CrossOrigin(origins = {"*"})
public class EquiposController {

    @Autowired
    private EquiposService service;

    //get all
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Equipos>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get one
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Equipos>> getOne(@PathVariable("id")Long id){
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    //insert
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Equipos>> insert(@Valid @ModelAttribute EquiposDto equiposDto){
        MultipartFile file = equiposDto.getProfilePhoto();
        return new ResponseEntity<>(
                this.service.insert(equiposDto.getEquipos(), file),
                HttpStatus.CREATED
        );
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Equipos>> update(
            @PathVariable("id") Long id,
            @ModelAttribute EquiposDto equiposDto) {
        // Obtén el equipo existente desde la base de datos
        Equipos existingEquipos = service.getOne(id).getData();

        // Actualiza los campos necesarios
        Equipos updatedEquipos = equiposDto.getEquipos();

        existingEquipos.setProfilePhoto(updatedEquipos.getProfilePhoto());
        existingEquipos.setName(updatedEquipos.getName());
        existingEquipos.setDescription(updatedEquipos.getDescription());
        existingEquipos.setFecha(updatedEquipos.getFecha());
        existingEquipos.setStatus(updatedEquipos.getStatus());
        existingEquipos.setCategoria(updatedEquipos.getCategoria());
        // No actualizar el campo ProfilePhoto

        CustomResponse<Equipos> response = service.update(existingEquipos, null);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }


    //delete
    @DeleteMapping("/")
    public ResponseEntity<CustomResponse<Boolean>> deleteEquiposById(@ModelAttribute EquiposDto equiposDto) throws IOException {
        return new ResponseEntity<>(
                this.service.deleteEquiposById(equiposDto.getId()),
                HttpStatus.OK
        );
    }



}
