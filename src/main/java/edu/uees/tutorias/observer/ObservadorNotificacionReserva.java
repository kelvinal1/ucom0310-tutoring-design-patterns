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
        notificarSegunEvento(evento.reserva(), evento.tipo());
    }

    private void notificarSegunEvento(
            ReservaTutoria reserva,
            TipoEventoReserva tipo
    ) {
        switch (tipo) {
            case CREADA -> notificarUsuarios(
                    reserva,
                    "Tu tutoría ya quedó reservada.",
                    "Un estudiante reservó uno de tus horarios."
            );
            case CONFIRMADA -> notificarUsuarios(
                    reserva,
                    "Tu tutoría ya quedó confirmada.",
                    "La tutoría ya quedó confirmada."
            );
            case CANCELADA -> notificarUsuarios(
                    reserva,
                    "Tu tutoría fue cancelada.",
                    "La tutoría fue cancelada."
            );
            case REPROGRAMADA -> notificarUsuarios(
                    reserva,
                    "Tu tutoría fue reprogramada.",
                    "La tutoría fue reprogramada."
            );
        }
    }

    private void notificarUsuarios(
            ReservaTutoria reserva,
            String mensajeEstudiante,
            String mensajeDocente
    ) {
        notificador.notificar(reserva.getEstudiante(), mensajeEstudiante);
        notificador.notificar(reserva.getDocente(), mensajeDocente);
    }
}
