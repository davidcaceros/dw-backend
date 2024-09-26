package com.dw.ventas.services;

import com.dw.ventas.entities.NotaCredito;
import com.dw.ventas.entities.Venta;
import com.dw.ventas.models.NotaCreditoRequest;
import com.dw.ventas.models.NotaCreditoResponse;
import com.dw.ventas.repositories.NotaCreditoRepository;
import org.springframework.stereotype.Service;

@Service
public class NotaCreditoService {


    private final NotaCreditoRepository notacreditoRepository;


    public NotaCreditoService(final NotaCreditoRepository notacreditoRepository) {
        this.notacreditoRepository = notacreditoRepository;
    }


    public NotaCredito createNotaCredito(final NotaCreditoRequest notaCreditoRequest) {
        Venta venta = new Venta();
        venta.setIdVenta(notaCreditoRequest.getIdventa());

        final NotaCredito notacredit = NotaCredito.builder()
                .id_venta(venta)
                .descripcion(notaCreditoRequest.getDescripcion())
                .build();

        final NotaCredito notacreditsave = notacreditoRepository.save(notacredit);
        return notacreditsave;
    }

}
