package com.utez.sysstock.sysstock.models.role;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utez.sysstock.sysstock.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 45)
    private String name;

    @OneToMany(mappedBy="role")
    @JsonIgnore
    private List<User> users;

    public Role(String id) {
        this.id = Long.parseLong(id);
    }
}
//Developed by: Jose Eduardo Arroyo Palafox