package com.utn.entities;

import com.utn.enums.Rol;
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
@Table( name = "usuarios")
public class Usuario extends Base {

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String mail;

    private String celular;

    @Column(nullable = false)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Set<Pedido> pedidos = new HashSet<>();


    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

}