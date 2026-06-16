package com.utn.dtos;

public record UsuarioModificacionDTO(
        String nombre,
        String apellido,
        String mail,
        String celular,
        String contrasena
) {
}