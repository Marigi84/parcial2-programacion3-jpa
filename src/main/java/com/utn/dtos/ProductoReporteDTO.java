package com.utn.dtos;

public record ProductoReporteDTO(
        Long id,
        String nombre,
        Double precio,
        int stock
) {
}