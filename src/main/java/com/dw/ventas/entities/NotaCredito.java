package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "nota_credito")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotaCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota")
    @SequenceGenerator(name = "seq_nota", sequenceName = "seq_nota", allocationSize = 1)
    @Column(name = "id_nota")
    private Integer id_nota;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta id_venta;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

}
