package com.utn.dtos;

import com.utn.enums.Rol;

public record UsuarioListadoDTO(
        Long id,
        String nombreCompleto,
        String mail,
        Rol rol
) {
}