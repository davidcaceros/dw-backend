package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "SUPERVISOR_TECNICO")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SupervisorTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_supervisor")
    private Long idSupervisor;

    @Column(name = "id_tecnico")
    private Long idTecnico;

    @ManyToOne
    @JoinColumn(name = "id_supervisor", referencedColumnName = "id", insertable = false, updatable = false)
    private Usuario supervisor;

    @ManyToOne
    @JoinColumn(name = "id_tecnico", referencedColumnName = "id", insertable = false, updatable = false)
    private Usuario tecnico;

}
