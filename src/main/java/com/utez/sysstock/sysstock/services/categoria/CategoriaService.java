package com.utez.sysstock.sysstock.services.categoria;


import com.utez.sysstock.sysstock.models.categoria.Categoria;
import com.utez.sysstock.sysstock.models.categoria.CategoriaRepository;
import com.utez.sysstock.sysstock.models.equipos.Equipos;
import com.utez.sysstock.sysstock.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    //getall
    @Transactional(readOnly = true)
    public CustomResponse<List<Categoria>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }

//get one

    public CustomResponse<Categoria> getOne(Long id) {
        Optional<Categoria> categoria = repository.findById(id);
        if (categoria.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La Categoria no existe."
            );
        }
        return new CustomResponse<>(
                categoria.get(),
                false,
                200,
                "Ok"
        );
    }


    //insert

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Categoria> insert(Categoria categoria) {
        return new CustomResponse<>(
                this.repository.saveAndFlush(categoria),
                false,
                200,
                "Categoría registrada"
        );
    }

    //update

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Categoria> update(Categoria categoria) {
        if (!this.repository.existsById(categoria.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La Categoría no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(categoria),
                false,
                200,
                "Categoria Actualizada!"
        );
    }


}
