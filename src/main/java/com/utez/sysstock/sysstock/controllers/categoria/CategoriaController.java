package com.utez.sysstock.sysstock.controllers.categoria;


import com.utez.sysstock.sysstock.dto.categoria.CategoriaDto;
import com.utez.sysstock.sysstock.models.categoria.Categoria;
import com.utez.sysstock.sysstock.services.categoria.CategoriaService;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api-sysstock/categoria/")
@CrossOrigin(origins = {"*"})
public class CategoriaController {

    @Autowired
    private CategoriaService service;


    //get all
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Categoria>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Categoria>> getOne(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    //insert
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Categoria>> insert(@Valid @RequestBody CategoriaDto categoriaDto){
        return new ResponseEntity<>(
                this.service.insert(categoriaDto.getCategoria()),
                HttpStatus.CREATED
        );
    }

    //update
    @PutMapping("/")
    public ResponseEntity<CustomResponse<Categoria>> update(
            @RequestBody CategoriaDto categoriaDto, @Valid BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.update(categoriaDto.getCategoria()),
                HttpStatus.CREATED
        );
    }

}
