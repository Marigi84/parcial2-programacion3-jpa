package com.utn.dtos;

public record DetallePedidoDTO(

        Integer cantidad,
        Double subtotal,
        ProductoDTO producto

) {
}