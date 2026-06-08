package com.utn.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Entity
@Table(name = "categorias")
public class Categoria extends Base {

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    @Builder.Default
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private Set<Producto> productos = new HashSet<>();

    public void addProducto(Producto producto) {
        productos.add(producto);
        producto.setCategoria(this);
    }

    public void removeProducto(Producto producto) {
        productos.remove(producto);
        producto.setCategoria(null);
    }
}