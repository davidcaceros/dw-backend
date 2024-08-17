package com.dw.ventas.services;

import com.dw.ventas.entities.Solicitud;
import com.dw.ventas.entities.Visita;
import com.dw.ventas.exception.impl.InternalInconsistencyException;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.SolicitudRequest;
import com.dw.ventas.models.SolicitudResponse;
import com.dw.ventas.models.VisitaRequest;
import com.dw.ventas.models.VisitaResponse;
import com.dw.ventas.repositories.SolicitudRepository;
import com.dw.ventas.repositories.VisitaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.dw.ventas.utils.utils.localDateTimeToString;

@Service
public class VisitaService {
    private final VisitaRepository visitaRepository;
    private final SolicitudRepository solicitudRepository;

    private final SolicitudService solicitudService;

    public VisitaService(final VisitaRepository visitaRepository,
                         final SolicitudRepository solicitudRepository,
                         final SolicitudService solicitudService) {
        this.visitaRepository = visitaRepository;
        this.solicitudRepository = solicitudRepository;
        this.solicitudService = solicitudService;
    }

    @Transactional
    public VisitaResponse saveVisita(final VisitaRequest visitaRequest) {
        final Solicitud solicitud = solicitudRepository.findById(visitaRequest.getIdSolicitud())
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("The solicitud was not found.")
                        .addAdditionalInformation("solicitudID", visitaRequest.getIdSolicitud())
                        .build());

        final LocalDateTime now = LocalDateTime.now();
        final Visita visita = Visita.builder()
                .solicitud(solicitud)
                .latitud(visitaRequest.getLatitud())
                .longitud(visitaRequest.getLongitud())
                .fechaHoraInicio(now)
                .build();

        final Visita visitaSaved = visitaRepository.save(visita);
        if (!atachVisitaToSolicitud(visitaSaved, solicitud)) {
            throw InternalInconsistencyException.builder()
                    .message("No se puedo adjuntar la visita a la solicitud")
                    .addAdditionalInformation("visitaId", visitaSaved.getId())
                    .addAdditionalInformation("solicitud", solicitud.getId())
                    .build();
        }

        return VisitaResponse.builder()
                .id(visitaSaved.getId())
                .idSolicitud(visitaSaved.getSolicitud().getId())
                .latitud(visitaSaved.getLatitud())
                .longitud(visitaSaved.getLongitud())
                .comentario(visitaSaved.getComentario())
                .fechaHoraInicio(visitaSaved.getFechaHoraInicio() != null ? visitaSaved.getFechaHoraInicio().toString() : null)
                .fechaHoraFin(visitaSaved.getFechaHoraFin() != null ? visitaSaved.getFechaHoraFin().toString() : null)
                .build();
    }

    public VisitaResponse updateVisita(final VisitaRequest visitaRequest) {
        final Visita visita = visitaRepository.findById(visitaRequest.getId())
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("The visita was not found.")
                        .addAdditionalInformation("visitaId", visitaRequest.getId())
                        .build());

        final Solicitud solicitud = solicitudRepository.findById(visita.getSolicitud().getId())
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("The solicitud was not found.")
                        .addAdditionalInformation("solicitudID", visitaRequest.getIdSolicitud())
                        .build());


        final LocalDateTime now = LocalDateTime.now();
        visita.setFechaHoraFin(now);
        visita.setLatitud(visitaRequest.getLatitud());
        visita.setLongitud(visitaRequest.getLongitud());
        visita.setComentario(visitaRequest.getComentario());

        final Visita visitaSaved = visitaRepository.save(visita);

        solicitud.setEstatus("COMPLETADA");
        solicitud.setFechaActualizacion(now);
        solicitudRepository.save(solicitud);

        return VisitaResponse.builder()
                .id(visitaSaved.getId())
                .idSolicitud(visitaSaved.getSolicitud().getId())
                .latitud(visitaSaved.getLatitud())
                .longitud(visitaSaved.getLongitud())
                .comentario(visitaSaved.getComentario())
                .fechaHoraInicio(visitaSaved.getFechaHoraInicio() != null ? visitaSaved.getFechaHoraInicio().toString() : null)
                .fechaHoraFin(visitaSaved.getFechaHoraFin() != null ? visitaSaved.getFechaHoraFin().toString() : null)
                .build();
    }

    private boolean atachVisitaToSolicitud(final Visita visita, final Solicitud solicitud) {
        final SolicitudRequest solicitudRequest = SolicitudRequest.builder()
                .id(solicitud.getId())
                .tecnicoId(solicitud.getTecnico().getId())
                .estatus(solicitud.getEstatus())
                .fechaVisita(localDateTimeToString(solicitud.getFechaVisita()))
                .visitaId(visita.getId())
                .build();
        SolicitudResponse solicitudResponse = solicitudService.updateSolicitud(solicitudRequest);

        return solicitudResponse != null;
    }
}
