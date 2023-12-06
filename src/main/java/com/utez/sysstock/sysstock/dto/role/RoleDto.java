package com.utez.sysstock.sysstock.dto.role;


import com.utez.sysstock.sysstock.models.role.Role;
import com.utez.sysstock.sysstock.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {
    private Long id;
    private String name;
    private List<User> users;


    public Role getRole() {
        return new Role(
                getId(),
                getName(),
                getUsers()
        );
    }

}
