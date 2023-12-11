package com.utez.sysstock.sysstock.models.equipos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utez.sysstock.sysstock.models.categoria.Categoria;
import com.utez.sysstock.sysstock.models.prestamos.Prestamos;
import com.utez.sysstock.sysstock.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "equipos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Equipos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false) // Evita que se actualice en ediciones
    private byte[] ProfilePhoto;

    @Column(nullable = false,length = 45)
    private String name;

    @Column(nullable = false,length = 60)
    private String description;

    @Column(nullable = false,length = 45)
    private Date fecha;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private Boolean status;


//    //ubicación
//    @Column(nullable = false,length = 60)
//    private String ubicacion;

    //existencias
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoria_id",nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Prestamos> prestamos;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User usuario;






}

//Developed by: Jose Eduardo Arroyo Palafox