package com.utez.sysstock.sysstock.dto.categoria;

import com.utez.sysstock.sysstock.models.categoria.Categoria;
import com.utez.sysstock.sysstock.models.equipos.Equipos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaDto {
    private Long id;
    private String name;
    private List<Equipos> equipos;

    public Categoria getCategoria() {
        return new Categoria(
                getId(),
                getName(),
                getEquipos()
        );
    }
}
