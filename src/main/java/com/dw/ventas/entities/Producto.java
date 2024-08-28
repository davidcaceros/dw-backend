package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "producto")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_producto")
    @SequenceGenerator(name = "seq_producto", sequenceName = "seq_producto", allocationSize = 1)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre", unique = true, nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", length = 50)
    private String descripcion;

    @Column(name = "ubicacion_fisica", length = 50)
    private String ubicacionFisica;

    @Column(name = "existencia_minima")
    private Integer existenciaMinima;

    @Column(name = "codigo_proveedor")
    private Integer codigoProveedor;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "codigo_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    private Proveedor proveedor;
}