package com.dw.ventas.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bitacora_entregas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BitacoraEntrega {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bitacora_entregas")
    @SequenceGenerator(name = "seq_bitacora_entregas", sequenceName = "seq_bitacora_entregas", allocationSize = 1)
    private Integer idBitacora;

    private Integer idVenta;

    private String estado;

    private String latitud;

    private String longitud;

    private Timestamp fechaCreacion;

    private Timestamp fechaActualizacion;

    private String codigoEntrega;
}

