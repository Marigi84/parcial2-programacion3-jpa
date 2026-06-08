package com.utn.dtos;

public record ProductoDTO(

        String nombre,
        Double precio,
        String descripcion,
        Boolean disponible,
        CategoriaDTO categoria

) {
}