package com.utez.sysstock.sysstock.controllers.prestamos;


import com.utez.sysstock.sysstock.dto.categoria.CategoriaDto;
import com.utez.sysstock.sysstock.dto.equipos.EquiposDto;
import com.utez.sysstock.sysstock.dto.prestamos.PrestamosDto;
import com.utez.sysstock.sysstock.models.categoria.Categoria;
import com.utez.sysstock.sysstock.models.equipos.Equipos;
import com.utez.sysstock.sysstock.models.prestamos.Prestamos;
import com.utez.sysstock.sysstock.services.prestamos.PrestamosService;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api-sysstock/prestamos/")
@CrossOrigin(origins = {"*"})
public class PrestamosController {

    @Autowired
    PrestamosService service;

    //get all
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Prestamos>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get one
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Prestamos>> getOne(@PathVariable("id")Long id){
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    //insert
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Prestamos>> insert(@Valid @ModelAttribute PrestamosDto prestamosDto){
        return new ResponseEntity<>(
                this.service.insert(prestamosDto.getPrestamos()),
                HttpStatus.CREATED
        );
    }

    //update cantidad dias (será solo para admins hasta 4 días)
    @PutMapping("/")
    public ResponseEntity<CustomResponse<Prestamos>> update(
            @ModelAttribute PrestamosDto prestamosDto, @Valid BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.update(prestamosDto.getPrestamos()),
                HttpStatus.CREATED
        );
    }


    @DeleteMapping("/")
    public ResponseEntity<CustomResponse<Boolean>> deletePrestamosyId(@ModelAttribute PrestamosDto prestamosDto) throws IOException {
        return new ResponseEntity<>(
                this.service.deletePrestamosById(prestamosDto.getId()),
                HttpStatus.OK
        );
    }




}
