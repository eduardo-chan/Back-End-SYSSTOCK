package com.utez.sysstock.sysstock.controllers.role;

import com.utez.sysstock.sysstock.dto.role.RoleDto;
import com.utez.sysstock.sysstock.models.role.Role;
import com.utez.sysstock.sysstock.services.role.RoleService;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api-sysstock/role/")
@CrossOrigin(origins = {"*"})
public class RoleController {

    @Autowired
    private RoleService service;


    //get all
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Role>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get one

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Role>> getOne(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    //insert
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Role>> insert(@Valid @RequestBody RoleDto roleDto){
        return new ResponseEntity<>(
                this.service.insert(roleDto.getRole()),
                HttpStatus.CREATED
        );
    }

    //update
    @PutMapping("/")
    public ResponseEntity<CustomResponse<Role>> update(
            @RequestBody RoleDto roleDto, @Valid BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.update(roleDto.getRole()),
                HttpStatus.CREATED
        );
    }


}
//Developed by: Jose Eduardo Arroyo Palafox