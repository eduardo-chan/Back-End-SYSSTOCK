package com.utez.sysstock.sysstock.models.user;


import com.utez.sysstock.sysstock.models.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false) // Evita que se actualice en ediciones
    private byte[] ProfilePhoto;

    @Column(nullable = false,length = 45)
    private String name;

    @Column(nullable = false,length = 45)
    private String surname;

    @Column(nullable = false,length = 45)
    private String lastname;

    @Column(nullable = false,length = 45)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private Boolean status;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String token;

    @Column(columnDefinition = "VARCHAR(8)")
    private String secretPass;

    @ManyToOne
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;


}


//Developed by: Jose Eduardo Arroyo Palafox