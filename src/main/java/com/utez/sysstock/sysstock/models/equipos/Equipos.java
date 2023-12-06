package com.utez.sysstock.sysstock.models.equipos;


import com.utez.sysstock.sysstock.models.categoria.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "categoria_id",nullable = false)
    private Categoria categoria;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;


}

//Developed by: Jose Eduardo Arroyo Palafox