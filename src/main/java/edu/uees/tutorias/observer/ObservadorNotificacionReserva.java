package edu.uees.tutorias.observer;

import edu.uees.tutorias.domain.ReservaTutoria;
import edu.uees.tutorias.notification.Notificador;

import java.util.Objects;

public class ObservadorNotificacionReserva implements ObservadorReserva {

    private final Notificador notificador;

    public ObservadorNotificacionReserva(Notificador notificador) {
        this.notificador = Objects.requireNonNull(notificador, "El notificador es obligatorio");
    }

    @Override
    public void actualizar(EventoReserva evento) {
        ReservaTutoria reserva = evento.reserva();
        String mensajeEstudiante = mensajeEstudiante(evento.tipo());
        String mensajeDocente = mensajeDocente(evento.tipo());

        notificador.notificar(reserva.getEstudiante(), mensajeEstudiante);
        notificador.notificar(reserva.getDocente(), mensajeDocente);
    }

    private String mensajeEstudiante(TipoEventoReserva tipo) {
        return switch (tipo) {
            case CREADA -> "Tu tutoría ya quedó reservada.";
            case CONFIRMADA -> "Tu tutoría ya quedó confirmada.";
            case CANCELADA -> "Tu tutoría fue cancelada.";
            case REPROGRAMADA -> "Tu tutoría fue reprogramada.";
        };
    }

    private String mensajeDocente(TipoEventoReserva tipo) {
        return switch (tipo) {
            case CREADA -> "Un estudiante reservó uno de tus horarios.";
            case CONFIRMADA -> "La tutoría ya quedó confirmada.";
            case CANCELADA -> "La tutoría fue cancelada.";
            case REPROGRAMADA -> "La tutoría fue reprogramada.";
        };
    }
}
