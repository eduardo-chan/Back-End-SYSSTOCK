package com.utez.sysstock.sysstock.dto.equipos;


import com.utez.sysstock.sysstock.models.categoria.Categoria;
import com.utez.sysstock.sysstock.models.equipos.Equipos;
import com.utez.sysstock.sysstock.models.prestamos.Prestamos;
import com.utez.sysstock.sysstock.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EquiposDto {

    private Long id;
    private MultipartFile ProfilePhoto;
    @NotEmpty(message = "Campo obligatorio")
    @Size(min = 1, max = 100)
    private String name;
    @NotEmpty(message = "Campo obligatorio")
    @Size(min = 1, max = 200)
    private String description;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha;
    private Boolean status;
    private int stock;
    private Categoria categoria;
    private List<Prestamos> prestamos;
    private User usuario;

    public Equipos getEquipos() {
        byte[] profilePhotoBytes = null;

        if (getProfilePhoto() != null) {
            profilePhotoBytes = getProfilePhoto().getOriginalFilename().getBytes();
        }

        return new Equipos(
                getId(),
                profilePhotoBytes,
                getName(),
                getDescription(),
                getFecha(),
                getStatus(),
                getStock(),
                getCategoria(),
                getPrestamos(),
                getUsuario()
        );

    }

}
