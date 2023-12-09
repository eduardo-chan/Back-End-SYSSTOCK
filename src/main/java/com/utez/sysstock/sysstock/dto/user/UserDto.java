package com.utez.sysstock.sysstock.dto.user;


import com.utez.sysstock.sysstock.models.role.Role;
import com.utez.sysstock.sysstock.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
//    private MultipartFile ProfilePhoto;
    @NotEmpty(message = "Campo obligatorio")
    @Size(min = 1, max = 100)
    private String name;
    @NotEmpty(message = "Campo obligatorio")
    private String email;
//    @NotEmpty(message = "Campo obligatorio")
//    @Size(min = 1, max = 100)
//    private String surname;
//    @NotEmpty(message = "Campo obligatorio")
//    @Size(min = 1, max = 100)
//    private String lastname;
    @NotEmpty(message = "Campo obligatorio")
    private String password;
    private Boolean status;
    private String token;
    private String secretPass;
    private Role role;

    public User getUser() {
//        byte[] profilePhotoBytes = null;
//
//        if (getProfilePhoto() != null) {
//            profilePhotoBytes = getProfilePhoto().getOriginalFilename().getBytes();
//        }

        return new User(
                getId(),
//                profilePhotoBytes,
                getName(),
//                getSurname(),
//                getLastname(),
                getEmail(),
                getPassword(),
                getStatus(),
                getToken(),
                getSecretPass(),
                getRole()
        );
    }
}
