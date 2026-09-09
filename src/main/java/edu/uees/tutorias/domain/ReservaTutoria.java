package edu.uees.tutorias.domain;

import java.util.Objects;

public class ReservaTutoria {

    private final String id;
    private final Estudiante estudiante;
    private final Docente docente;

    private HorarioDisponible horario;
    private EstadoReserva estado;

    private final String asignatura;
    private final ModalidadTutoria modalidad;
    private final String enlaceReunion;
    private final String ubicacion;
    private final String notas;
    private final int duracionMinutos;
    private final TipoTutoria tipoTutoria;

    // Constructor original de Ae1: se conserva por compatibilidad.
    public ReservaTutoria(
            String id,
            Estudiante estudiante,
            Docente docente,
            HorarioDisponible horario
    ) {
        this(
                id,
                estudiante,
                docente,
                horario,
                null,
                ModalidadTutoria.ONLINE,
                null,
                null,
                null,
                60,
                TipoTutoria.NORMAL
        );
    }

    // Constructor de Ae2: también se conserva.
    public ReservaTutoria(
            String id,
            Estudiante estudiante,
            Docente docente,
            HorarioDisponible horario,
            String asignatura,
            ModalidadTutoria modalidad,
            String enlaceReunion,
            String ubicacion,
            String notas,
            int duracionMinutos
    ) {
        this(
                id,
                estudiante,
                docente,
                horario,
                asignatura,
                modalidad,
                enlaceReunion,
                ubicacion,
                notas,
                duracionMinutos,
                TipoTutoria.NORMAL
        );
    }

    // Constructor utilizado por el Builder en Ae3.
    public ReservaTutoria(
            String id,
            Estudiante estudiante,
            Docente docente,
            HorarioDisponible horario,
            String asignatura,
            ModalidadTutoria modalidad,
            String enlaceReunion,
            String ubicacion,
            String notas,
            int duracionMinutos,
            TipoTutoria tipoTutoria
    ) {
        this.id = Objects.requireNonNull(id, "El identificador de la reserva es obligatorio");
        this.estudiante = Objects.requireNonNull(estudiante, "El estudiante es obligatorio");
        this.docente = Objects.requireNonNull(docente, "El docente es obligatorio");
        this.horario = Objects.requireNonNull(horario, "El horario disponible es obligatorio");
        this.modalidad = Objects.requireNonNull(modalidad, "La modalidad es obligatoria");
        this.tipoTutoria = Objects.requireNonNull(tipoTutoria, "El tipo de tutoría es obligatorio");

        if (id.isBlank()) {
            throw new IllegalArgumentException("El identificador de la reserva es obligatorio");
        }

        if (duracionMinutos <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor que cero");
        }

        this.asignatura = asignatura;
        this.enlaceReunion = enlaceReunion;
        this.ubicacion = ubicacion;
        this.notas = notas;
        this.duracionMinutos = duracionMinutos;

        horario.reservar();
        estado = EstadoReserva.PENDIENTE;
    }

    public void confirmar() {
        if (estado == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("No se puede confirmar una reserva cancelada");
        }

        estado = EstadoReserva.CONFIRMADA;
    }

    public void cancelar() {
        if (estado == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("La reserva ya está cancelada");
        }

        horario.liberar();
        estado = EstadoReserva.CANCELADA;
    }

    public void reprogramar(HorarioDisponible nuevoHorario) {
        Objects.requireNonNull(nuevoHorario, "El nuevo horario disponible es obligatorio");

        if (estado == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("No se puede reprogramar una reserva cancelada");
        }

        if (!nuevoHorario.estaDisponible()) {
            throw new IllegalStateException("El nuevo horario no está disponible");
        }

        horario.liberar();
        nuevoHorario.reservar();
        horario = nuevoHorario;
        estado = EstadoReserva.PENDIENTE;
    }

    public String getId() {
        return id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Docente getDocente() {
        return docente;
    }

    public HorarioDisponible getHorario() {
        return horario;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public ModalidadTutoria getModalidad() {
        return modalidad;
    }

    public String getEnlaceReunion() {
        return enlaceReunion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getNotas() {
        return notas;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public TipoTutoria getTipoTutoria() {
        return tipoTutoria;
    }
}
