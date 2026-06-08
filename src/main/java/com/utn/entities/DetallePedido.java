package com.utn.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Entity
@Table( name = "detalle_pedido")
public class DetallePedido extends Base {

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private Double subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;


    public void calcularSubtotal() {
        this.subtotal = cantidad * producto.getPrecio();
    }

}


