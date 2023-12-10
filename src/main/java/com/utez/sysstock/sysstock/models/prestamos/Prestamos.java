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

    @ManyToOne
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipos equipo;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User usuario;





}
