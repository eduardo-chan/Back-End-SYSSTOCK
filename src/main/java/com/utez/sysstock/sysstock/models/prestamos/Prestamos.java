package com.utez.sysstock.sysstock.models.prestamos;


import com.utez.sysstock.sysstock.models.equipos.Equipos;
import com.utez.sysstock.sysstock.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Prestamos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cantidadDias ;  // valor por defecto es 1 día

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private Boolean status;


    //almacenar la fecha de entrega esperada (la fecha en la que el user debe devolver el equipo).
    @Column(nullable = false)
    private LocalDate fechaEntregaEsperada;

//multaaaaa
    @Column(nullable = false)
    private double multa;


    @ManyToOne
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipos equipo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User usuario;




}
