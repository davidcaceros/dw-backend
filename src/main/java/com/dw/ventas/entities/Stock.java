package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_stock")
    @SequenceGenerator(name = "seq_stock", sequenceName = "seq_stock", allocationSize = 1)
    @Column(name = "id_stock")
    private Integer idStock;

    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @Column(name = "existencia", nullable = false)
    private Integer existencia;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", insertable = false, updatable = false)
    private Producto producto;
}

