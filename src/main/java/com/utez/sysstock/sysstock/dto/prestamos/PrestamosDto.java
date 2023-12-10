package com.utez.sysstock.sysstock.dto.prestamos;


import com.utez.sysstock.sysstock.models.equipos.Equipos;
import com.utez.sysstock.sysstock.models.prestamos.Prestamos;
import com.utez.sysstock.sysstock.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrestamosDto {

    private Long id;


    private int cantidadDias = 1; // valor por defecto es 1 día

    private Boolean status;

    private Equipos equipo;




    public Prestamos getPrestamos() {

        return new Prestamos(
          getId(),
          getCantidadDias(),
          getStatus(),
          getEquipo()
        );
    }


}
