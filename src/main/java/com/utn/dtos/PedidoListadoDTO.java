package com.utn.dtos;

import com.utn.enums.Estado;
import com.utn.enums.FormaPago;

import java.time.LocalDate;

public record PedidoListadoDTO(
        Long id,
        LocalDate fecha,
        Estado estado,
        FormaPago formaPago,
        String nombreUsuario,
        Double total
) {
}