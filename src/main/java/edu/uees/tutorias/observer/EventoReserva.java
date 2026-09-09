package edu.uees.tutorias.observer;

import edu.uees.tutorias.domain.ReservaTutoria;

import java.util.Objects;

public record EventoReserva(ReservaTutoria reserva, TipoEventoReserva tipo) {

    public EventoReserva {
        Objects.requireNonNull(reserva, "La reserva del evento es obligatoria");
        Objects.requireNonNull(tipo, "El tipo de evento es obligatorio");
    }
}
