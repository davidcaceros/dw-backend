package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SOLICITUD")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Usuario cliente;

    @Column(name = "tipo_servicio")
    private String tipoServicio;

    private String descripcion;
    private String direccion;
    private String latitud;
    private String longitud;

    @ManyToOne
    @JoinColumn(name = "id_tecnico")
    private Usuario tecnico;

    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    private Usuario supervisor;

    @Column(name = "fecha_visita")
    private LocalDateTime fechaVisita;

    @ManyToOne
    @JoinColumn(name = "visita_id")
    private Visita visita;

    private String estatus;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false)
    private boolean enabled;
}
