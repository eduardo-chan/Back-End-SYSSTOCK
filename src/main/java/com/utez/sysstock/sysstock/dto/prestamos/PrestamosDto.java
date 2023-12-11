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
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrestamosDto {

    private Long id;


    private int cantidadDias = 1; // valor por defecto es 1 día

    private Boolean status;

    private LocalDate fechaEntregaEsperada;

    private double multa;

    private Equipos equipo;

    private User usuario;




    public Prestamos getPrestamos() {

        return new Prestamos(
          getId(),
          getCantidadDias(),
          getStatus(),
          getFechaEntregaEsperada(),
          getMulta(),
          getEquipo(),
          getUsuario()
        );
    }


}
