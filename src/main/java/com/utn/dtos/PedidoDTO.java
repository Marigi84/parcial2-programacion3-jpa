package com.utn.dtos;

import com.utn.enums.Estado;
import com.utn.enums.FormaPago;

import java.time.LocalDate;
import java.util.Set;

public record PedidoDTO(

        LocalDate fecha,
        Estado estado,
        Double total,
        FormaPago formaPago,
        Set<DetallePedidoDTO> detalles

) {
}