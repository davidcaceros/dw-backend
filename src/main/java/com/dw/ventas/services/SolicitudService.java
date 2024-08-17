package com.dw.ventas.services;

import com.dw.ventas.entities.Solicitud;
import com.dw.ventas.entities.SupervisorTecnico;
import com.dw.ventas.entities.Usuario;
import com.dw.ventas.entities.Visita;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.SolicitudRequest;
import com.dw.ventas.models.SolicitudResponse;
import com.dw.ventas.models.UsuarioResponse;
import com.dw.ventas.models.VisitaResponse;
import com.dw.ventas.repositories.SolicitudRepository;
import com.dw.ventas.repositories.SupervisorTecnicoRepository;
import com.dw.ventas.repositories.UsuarioRepository;
import com.dw.ventas.repositories.VisitaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.dw.ventas.utils.utils.*;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
public class SolicitudService {
    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;

    private final VisitaRepository visitaRepository;
    private final SupervisorTecnicoRepository supervisorTecnicoRepository;

    public SolicitudService(final SolicitudRepository solicitudRepository,
                            final UsuarioRepository usuarioRepository,
                            final VisitaRepository visitaRepository,
                            final SupervisorTecnicoRepository supervisorTecnicoRepository) {
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
        this.visitaRepository = visitaRepository;
        this.supervisorTecnicoRepository = supervisorTecnicoRepository;
    }

    public SolicitudResponse saveSolicitud(final SolicitudRequest solicitudRequest) {
        final Usuario cliente = usuarioRepository.findById(solicitudRequest.getClienteId())
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("The client was not found.")
                        .addAdditionalInformation("clienteId", solicitudRequest.getClienteId())
                        .build());

        final Usuario tecnico;
        final SupervisorTecnico supervisorTecnico;
        if (solicitudRequest.getTecnicoId() != null) {
            tecnico = usuarioRepository.findById(solicitudRequest.getTecnicoId())
                    .orElseThrow(() -> ResourceNotFoundException.builder()
                            .errorMessageKey("The tecnico was not found.")
                            .addAdditionalInformation("tecnicoId", solicitudRequest.getTecnicoId())
                            .build());
            supervisorTecnico = supervisorTecnicoRepository.findByTecnico(tecnico)
                    .orElseThrow(() -> ResourceNotFoundException.builder()
                            .errorMessageKey("The supervisorTecnico was not found.")
                            .addAdditionalInformation("tecnicoId", tecnico.getId())
                            .build());
        } else {
            tecnico = null;
            supervisorTecnico = null;
        }

        final LocalDateTime fechaVisita;
        if (solicitudRequest.getFechaVisita() != null) {
            fechaVisita = frontToBackendLocalDateTime(solicitudRequest.getFechaVisita());
        } else {
            fechaVisita = null;
        }

        final LocalDateTime now = LocalDateTime.now();
        final Solicitud solicitud = Solicitud.builder()
                .cliente(cliente)
                .tipoServicio(solicitudRequest.getTipoServicio())
                .descripcion(solicitudRequest.getDescripcion())
                .direccion(solicitudRequest.getDireccion())
                .latitud(solicitudRequest.getLatitud())
                .longitud(solicitudRequest.getLongitud())
                .tecnico(tecnico)
                .supervisor(supervisorTecnico != null ? supervisorTecnico.getSupervisor() : null)
                .fechaVisita(fechaVisita)
                .estatus("PENDIENTE")
                .fechaCreacion(now)
                .enabled(true)
                .build();

        final Solicitud solicitudSaved = solicitudRepository.save(solicitud);

        final UsuarioResponse clienteResponse = buildUsuarioResponse(solicitudSaved.getCliente());
        final UsuarioResponse tecnicoResponse = getUsuarioResponse(solicitudSaved.getTecnico());
        final UsuarioResponse superVisorResponse = getUsuarioResponse(solicitudSaved.getSupervisor());

        return SolicitudResponse.builder()
                .id(solicitudSaved.getId())
                .cliente(clienteResponse)
                .tipoServicio(solicitudSaved.getTipoServicio())
                .descripcion(solicitudSaved.getDescripcion())
                .direccion(solicitudSaved.getDireccion())
                .latitud(solicitudSaved.getLatitud())
                .longitud(solicitudSaved.getLongitud())
                .tecnico(tecnicoResponse)
                .supervisor(superVisorResponse)
                .fechaVisita(solicitudSaved.getFechaVisita() != null ? solicitudSaved.getFechaVisita().toString() : null)
                .estatus(solicitudSaved.getEstatus())
                .enabled(solicitudSaved.isEnabled())
                .fechaCreacion(solicitudSaved.getFechaCreacion().toString())
                .build();
    }

    public SolicitudResponse updateSolicitud(final SolicitudRequest solicitudRequest) {
        final Solicitud solicitud = solicitudRepository.findById(solicitudRequest.getId())
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("The solicitud was not found.")
                        .addAdditionalInformation("solicitudID", solicitudRequest.getId())
                        .build());

        final Usuario tecnico;
        final SupervisorTecnico supervisorTecnico;
        if (solicitudRequest.getTecnicoId() != null) {
            tecnico = usuarioRepository.findById(solicitudRequest.getTecnicoId())
                    .orElseThrow(() -> ResourceNotFoundException.builder()
                            .errorMessageKey("The tecnico was not found.")
                            .addAdditionalInformation("tecnicoId", solicitudRequest.getTecnicoId())
                            .build());

            supervisorTecnico = supervisorTecnicoRepository.findByTecnico(tecnico)
                    .orElseThrow(() -> ResourceNotFoundException.builder()
                            .errorMessageKey("The supervisorTecnico was not found.")
                            .addAdditionalInformation("tecnicoId", solicitudRequest.getTecnicoId())
                            .build());

        } else {
            tecnico = null;
            supervisorTecnico = null;
        }


        final LocalDateTime fechaVisita;
        if (solicitudRequest.getFechaVisita() != null) {
            fechaVisita = frontToBackendLocalDateTime(solicitudRequest.getFechaVisita());
        } else {
            fechaVisita = null;
        }

        final Visita visita;
        if (solicitudRequest.getVisitaId() != null) {
            visita = visitaRepository.findById(solicitudRequest.getVisitaId())
                    .orElseThrow(() -> ResourceNotFoundException.builder()
                            .errorMessageKey("The visita was not found.")
                            .addAdditionalInformation("visitaId", solicitudRequest.getVisitaId())
                            .build());
        } else {
            visita = null;
        }

        final LocalDateTime now = LocalDateTime.now();
        solicitud.setTecnico(tecnico);
        solicitud.setSupervisor(supervisorTecnico != null ? supervisorTecnico.getSupervisor() : null);
        solicitud.setEstatus(solicitudRequest.getEstatus());
        solicitud.setFechaVisita(fechaVisita);
        solicitud.setFechaActualizacion(now);
        solicitud.setVisita(visita);

        final Solicitud solicitudUpdated = solicitudRepository.save(solicitud);

        final UsuarioResponse clienteResponse = getUsuarioResponse(solicitudUpdated.getCliente());
        final UsuarioResponse tecnicoResponse = getUsuarioResponse(solicitudUpdated.getTecnico());
        final UsuarioResponse supervisorResponse = getUsuarioResponse(solicitudUpdated.getSupervisor());

        final VisitaResponse visitaResponse;
        if (solicitudUpdated.getVisita() != null) {
            visitaResponse = VisitaResponse.builder()
                    .id(solicitudUpdated.getVisita().getId())
                    .idSolicitud(solicitudUpdated.getId())
                    .latitud(solicitudUpdated.getVisita().getLatitud())
                    .longitud(solicitudUpdated.getVisita().getLongitud())
                    .comentario(solicitudUpdated.getVisita().getComentario())
                    .fechaHoraInicio(solicitudUpdated.getVisita().getFechaHoraInicio() != null ? solicitudUpdated.getVisita().getFechaHoraInicio().toString() : null)
                    .fechaHoraFin(solicitudUpdated.getVisita().getFechaHoraFin() != null ? solicitudUpdated.getVisita().getFechaHoraFin().toString() : null)
                    .build();
        } else {
            visitaResponse = null;
        }

        return SolicitudResponse.builder()
                .id(solicitudUpdated.getId())
                .cliente(clienteResponse)
                .tipoServicio(solicitudUpdated.getTipoServicio())
                .descripcion(solicitudUpdated.getDescripcion())
                .direccion(solicitudUpdated.getDireccion())
                .latitud(solicitudUpdated.getLatitud())
                .longitud(solicitudUpdated.getLongitud())
                .tecnico(tecnicoResponse)
                .supervisor(supervisorResponse)
                .fechaVisita(formattedDateTime(solicitudUpdated.getFechaVisita()))
                .visita(visitaResponse)
                .estatus(solicitudUpdated.getEstatus())
                .enabled(solicitudUpdated.isEnabled())
                .fechaCreacion(formattedDateTime(solicitudUpdated.getFechaCreacion()))
                .fechaActualizacion(solicitudUpdated.getFechaActualizacion() != null ? formattedDateTime(solicitudUpdated.getFechaActualizacion()) : null)
                .build();
    }

    public SolicitudResponse getSolicitud(final Long id) {
        final Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("The solicitud was not found.")
                        .addAdditionalInformation("solicitudID", id)
                        .build());

        final UsuarioResponse clienteResponse = getUsuarioResponse(solicitud.getCliente());
        final UsuarioResponse tecnicoResponse = getUsuarioResponse(solicitud.getTecnico());
        final UsuarioResponse supervisorResponse = getUsuarioResponse(solicitud.getSupervisor());

        final VisitaResponse visitaResponse;
        if (solicitud.getVisita() != null) {
            final Visita visita = visitaRepository.findById(solicitud.getVisita().getId())
                    .orElseThrow(() -> ResourceNotFoundException.builder()
                            .errorMessageKey("The visita was not found.")
                            .addAdditionalInformation("visitaId", solicitud.getVisita().getId())
                            .build());
            visitaResponse = VisitaResponse.builder()
                    .id(visita.getId())
                    .idSolicitud(solicitud.getId())
                    .latitud(visita.getLatitud())
                    .longitud(visita.getLongitud())
                    .comentario(visita.getComentario())
                    .fechaHoraInicio(visita.getFechaHoraInicio().toString())
                    .fechaHoraFin(visita.getFechaHoraFin() != null ? visita.getFechaHoraFin().toString() : null)
                    .build();
        } else {
            visitaResponse = null;
        }

        return SolicitudResponse.builder()
                .id(solicitud.getId())
                .cliente(clienteResponse)
                .tipoServicio(solicitud.getTipoServicio())
                .descripcion(solicitud.getDescripcion())
                .direccion(solicitud.getDireccion())
                .latitud(solicitud.getLatitud())
                .longitud(solicitud.getLongitud())
                .tecnico(tecnicoResponse)
                .supervisor(supervisorResponse)
                .fechaVisita(solicitud.getFechaVisita() != null ? solicitud.getFechaVisita().toString() : null)
                .estatus(solicitud.getEstatus())
                .enabled(solicitud.isEnabled())
                .fechaCreacion(solicitud.getFechaCreacion().toString())
                .fechaActualizacion(solicitud.getFechaActualizacion() != null ? solicitud.getFechaActualizacion().toString() : null)
                .visita(visitaResponse)
                .build();
    }

    public List<SolicitudResponse> getSolicitudes(final String status) {
        final List<Solicitud> solicitudes = isNotBlank(status)
                ? solicitudRepository.findByEstatusAndEnabledTrue(status)
                : solicitudRepository.findAllByEnabledTrueOrderByFechaCreacionDesc();


        return solicitudes.stream()
                .map(solicitud -> {
                    final UsuarioResponse clienteResponse = getUsuarioResponse(solicitud.getCliente());
                    final UsuarioResponse tecnicoResponse = getUsuarioResponse(solicitud.getTecnico());
                    final UsuarioResponse supervisorResponse = getUsuarioResponse(solicitud.getSupervisor());

                    final VisitaResponse visitaResponse;
                    if (solicitud.getVisita() != null) {
                        final Visita visita = visitaRepository.findById(solicitud.getVisita().getId())
                                .orElseThrow(() -> ResourceNotFoundException.builder()
                                        .errorMessageKey("The visita was not found.")
                                        .addAdditionalInformation("visitaId", solicitud.getVisita().getId())
                                        .build());
                        visitaResponse = VisitaResponse.builder()
                                .id(visita.getId())
                                .idSolicitud(solicitud.getId())
                                .latitud(visita.getLatitud())
                                .longitud(visita.getLongitud())
                                .comentario(visita.getComentario())
                                .fechaHoraInicio(visita.getFechaHoraInicio().toString())
                                .fechaHoraFin(visita.getFechaHoraFin() != null ? visita.getFechaHoraFin().toString() : null)
                                .build();
                    } else {
                        visitaResponse = null;
                    }

                    return SolicitudResponse.builder()
                            .id(solicitud.getId())
                            .cliente(clienteResponse)
                            .tipoServicio(solicitud.getTipoServicio())
                            .descripcion(solicitud.getDescripcion())
                            .direccion(solicitud.getDireccion())
                            .latitud(solicitud.getLatitud())
                            .longitud(solicitud.getLongitud())
                            .tecnico(tecnicoResponse)
                            .supervisor(supervisorResponse)
                            .fechaVisita(solicitud.getFechaVisita() != null ? solicitud.getFechaVisita().toString() : null)
                            .estatus(solicitud.getEstatus())
                            .enabled(solicitud.isEnabled())
                            .fechaCreacion(formattedDateTime(solicitud.getFechaCreacion()))
                            .fechaActualizacion(solicitud.getFechaActualizacion() != null ? formattedDateTime(solicitud.getFechaActualizacion()) : null)
                            .visita(visitaResponse)
                            .build();

                }).collect(Collectors.toList());
    }

    private UsuarioResponse getUsuarioResponse(final Usuario usuario) {
        final UsuarioResponse usuarioResponse;
        if (usuario != null) {
            usuarioResponse = buildUsuarioResponse(usuario);
        } else {
            usuarioResponse = null;
        }
        return usuarioResponse;
    }

    private UsuarioResponse buildUsuarioResponse(Usuario usuario) {
        final UsuarioResponse usuarioResponse;
        usuarioResponse = UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .dpi(usuario.getDpi())
                .rolId(usuario.getRol().getId())
                .rolSlug(usuario.getRol().getSlug())
                .telefono(usuario.getTelefono())
                .enabled(usuario.isEnabled())
                .build();
        return usuarioResponse;
    }

}
