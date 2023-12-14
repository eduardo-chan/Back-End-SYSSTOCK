package com.utez.sysstock.sysstock.dto.prestamos;


import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("id")
    private Long id;

    @JsonProperty("cantidadDias")
    private int cantidadDias = 1; // valor por defecto es 1 día

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("fechaEntregaEsperada")
    private LocalDate fechaEntregaEsperada;

    @JsonProperty("multa")
    private double multa;

    @JsonProperty("equipo")
    private Equipos equipo;

    @JsonProperty("usuario")
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
