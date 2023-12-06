package com.utez.sysstock.sysstock.models.categoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utez.sysstock.sysstock.models.equipos.Equipos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 45)
    private String name;

    @OneToMany(mappedBy="categoria")
    @JsonIgnore
    private List<Equipos> equipos;

    public Categoria(String id) {
        this.id = Long.parseLong(id);
    }

}
//Developed by: Jose Eduardo Arroyo
