package com.utn.entities;

import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Entity
@Table(name = "pedidos")
public class Pedido extends Base implements Calculable {

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPago formaPago;

    @Builder.Default
    @OneToMany( cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.LAZY, orphanRemoval = true )
    @JoinColumn(name = "pedido_id", nullable = false)
    private Set<DetallePedido> detalles = new HashSet<>();


    @Override
    public void calcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(detalle -> detalle.getSubtotal())
                .sum();
    }

    public void addDetallePedido(int cantidad, Producto producto) {

        DetallePedido detalle = DetallePedido.builder()
                .cantidad(cantidad)
                .producto(producto)
                .build();

        detalle.calcularSubtotal();

        detalles.add(detalle);

        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        return detalles.stream()
                .filter(detalle -> detalle.getProducto().equals(producto))
                .findFirst()
                .orElse(null);
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        detalles.removeIf(detalle -> detalle.getProducto().equals(producto));
        calcularTotal();
    }

    public int calcularCantidadItems() {

        return detalles.stream()
                .mapToInt(detalle -> detalle.getCantidad())
                .sum();
    }
}
