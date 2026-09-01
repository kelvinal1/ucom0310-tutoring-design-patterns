package edu.uees.tutorias.domain;

import java.util.Objects;

public class ReservaTutoria {

    private final String id;
    private final Estudiante estudiante;
    private final Docente docente;

    private HorarioDisponible horario;
    private EstadoReserva estado;

    // Estos datos aparecen en Ae2 porque la reserva ahora tiene más configuración.
    private final String asignatura;
    private final ModalidadTutoria modalidad;
    private final String enlaceReunion;
    private final String ubicacion;
    private final String notas;
    private final int duracionMinutos;


    // Se conserva el constructor original de Ae1 para mantener compatibilidad.
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
                60
        );
    }


    // El Builder utiliza este constructor después de validar toda la configuración.
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
        this.id = Objects.requireNonNull(id, "El identificador de la reserva es obligatorio");
        this.estudiante = Objects.requireNonNull(estudiante, "El estudiante es obligatorio");
        this.docente = Objects.requireNonNull(docente, "El docente es obligatorio");
        this.horario = Objects.requireNonNull(horario, "El horario disponible es obligatorio");
        this.modalidad = Objects.requireNonNull(modalidad, "La modalidad es obligatoria");

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

        // Cuando se reprograma, toca confirmarla otra vez.
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
}
