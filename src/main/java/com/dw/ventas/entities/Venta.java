package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "venta")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_venta")
    @SequenceGenerator(name = "seq_venta", sequenceName = "seq_venta", allocationSize = 1)
    @Column(name = "id_venta")
    private Integer idVenta;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Persona cliente;

    @ManyToOne
    @JoinColumn(name = "id_vendedor", nullable = false)
    private Persona vendedor;

    @Column(name = "tipo_venta", length = 25, nullable = false)
    private String tipoVenta;

    @Column(name = "tipo_pago", length = 32)
    private String tipoPago;

    @Column(name = "total_venta", nullable = false)
    private Double totalVenta;

    @Column(name = "estado", length = 25, nullable = false)
    private String estado;

    @Column(name = "devolucion")
    private Boolean devolucion;

    @Column(name = "saldo_favor")
    private Double saldoFavor;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
