package edu.uees.tutorias.service;

import edu.uees.tutorias.builder.ReservaTutoriaBuilder;
import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ReservaTutoria;
import edu.uees.tutorias.notification.Notificador;
import edu.uees.tutorias.repository.RepositorioReservas;

import java.util.UUID;

public class ServicioReservas {

    private final RepositorioReservas repositorio;
    private final Notificador notificador;

    public ServicioReservas(
            RepositorioReservas repositorio,
            Notificador notificador
    ) {
        this.repositorio = repositorio;
        this.notificador = notificador;
    }


    public ReservaTutoria crearReserva(
            Estudiante estudiante,
            Docente docente,
            String horarioId
    ) {
        if (estudiante == null || docente == null) {
            throw new IllegalArgumentException("El estudiante y el docente son obligatorios");
        }

        if (!docente.estaHorarioDisponible(horarioId)) {
            throw new IllegalStateException("Ese horario ya no está disponible");
        }

        HorarioDisponible horario = docente.obtenerHorario(horarioId);

        // En Ae2 la creación ya utiliza Builder, pero el caso de uso del servicio no cambia.
        ReservaTutoria reserva = new ReservaTutoriaBuilder()
                .conId(UUID.randomUUID().toString())
                .paraEstudiante(estudiante)
                .conDocente(docente)
                .enHorario(horario)
                .construir();

        repositorio.guardar(reserva);

        notificador.notificar(estudiante, "Tu tutoría ya quedó reservada.");
        notificador.notificar(docente, "Ojo, un estudiante te acaba de reservar uno de tus horarios.");

        return reserva;
    }

    public void confirmarReserva(String reservaId) {
        ReservaTutoria reserva = obtenerReserva(reservaId);

        reserva.confirmar();
        repositorio.guardar(reserva);

        notificador.notificar(reserva.getEstudiante(), "Tu tutoría ya quedó confirmada.");
        notificador.notificar(reserva.getDocente(), "La tutoría ya quedó confirmada.");
    }

    public void cancelarReserva(String reservaId) {
        ReservaTutoria reserva = obtenerReserva(reservaId);

        reserva.cancelar();
        repositorio.guardar(reserva);

        notificador.notificar(reserva.getEstudiante(), "Tu tutoría fue cancelada.");
        notificador.notificar(reserva.getDocente(), "La tutoría fue cancelada.");
    }

    public void reprogramarReserva(String reservaId, String nuevoHorarioId) {
        ReservaTutoria reserva = obtenerReserva(reservaId);
        Docente docente = reserva.getDocente();

        if (!docente.estaHorarioDisponible(nuevoHorarioId)) {
            throw new IllegalStateException("El nuevo horario no está disponible");
        }

        HorarioDisponible nuevoHorario = docente.obtenerHorario(nuevoHorarioId);

        reserva.reprogramar(nuevoHorario);
        repositorio.guardar(reserva);

        notificador.notificar(reserva.getEstudiante(), "Tu tutoría fue reprogramada.");
        notificador.notificar(reserva.getDocente(), "La tutoría fue reprogramada.");
    }


    private ReservaTutoria obtenerReserva(String reservaId) {
        return repositorio.buscarPorId(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la reserva"));
    }
}
