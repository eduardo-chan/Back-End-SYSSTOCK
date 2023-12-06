package com.utez.sysstock.sysstock.services.role;

import com.utez.sysstock.sysstock.models.role.Role;
import com.utez.sysstock.sysstock.models.role.RoleRepository;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository repository;

    //traer todos los roles
    public CustomResponse<List<Role>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    //traer uno

    public CustomResponse<Role> getOne(Long id) {
        Optional<Role> role = repository.findById(id);
        if (role.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El registro no existe."
            );
        }
        return new CustomResponse<>(
                role.get(),
                false,
                200,
                "Ok"
        );
    }


    //insert role

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Role> insert(Role role) {
        return new CustomResponse<>(
                this.repository.saveAndFlush(role),
                false,
                200,
                "Rol registrado"
        );
    }


    //actualizar role
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Role> update(Role role){
        if (!this.repository.existsById(role.getId())){
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Role inexistente"
            );
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(role),
                false,
                200,
                "Rol actualizado"
        );
    }


}
//Developed by: Jose Eduardo Arroyo Palafox