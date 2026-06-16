package com.utn.dtos;

import com.utn.enums.Rol;

public record UsuarioAltaDTO(
        String nombre,
        String apellido,
        String mail,
        String celular,
        String contrasena,
        Rol rol
) {
}